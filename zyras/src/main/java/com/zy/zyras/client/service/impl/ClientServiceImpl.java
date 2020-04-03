package com.zy.zyras.client.service.impl;

import cn.whl.commonutils.exception.ExistException;
import cn.whl.commonutils.exception.InputWrongException;
import cn.whl.commonutils.exception.NotExistException;
import cn.whl.commonutils.exception.ServiceRunException;
import cn.whl.commonutils.exception.TokenWrongException;
import cn.whl.commonutils.log.LoggerTools;
import cn.whl.commonutils.service.result.ServiceResult;
import cn.whl.commonutils.token.TokenTool;
import cn.whl.commonutils.verificate.VerificateTool;
import com.zy.zyras.client.domain.CustomerClient;
import com.zy.zyras.client.domain.GatewayClient;
import com.zy.zyras.client.domain.LimitedServiceClient;
import com.zy.zyras.client.domain.ServiceClient;
import com.zy.zyras.client.domain.vo.CustomerResponse;
import com.zy.zyras.client.domain.vo.FindServiceRequest;
import com.zy.zyras.client.domain.vo.LimitedServiceClientResponse;
import com.zy.zyras.client.domain.vo.RegistRequest;
import com.zy.zyras.client.domain.vo.RegistResponse;
import com.zy.zyras.client.domain.vo.ServiceClientResponse;
import com.zy.zyras.client.domain.vo.ServiceResponse;
import com.zy.zyras.client.pool.ClientPool;
import com.zy.zyras.client.service.ClientService;
import com.zy.zyras.ras.utils.RasUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.print.attribute.standard.Severity;
import org.springframework.stereotype.Service;

/**
 * 业务层实现：客户端
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
 */
@Service
public class ClientServiceImpl implements ClientService {

    //服务提供方修改锁
    private Lock updateServiceLock = new ReentrantLock();

    //服务请求方修改锁
    private Lock updateCustomerLock = new ReentrantLock();

    //网关修改锁
    private Lock updateGatewayLock = new ReentrantLock();

    @Override
    public RegistResponse regist(RegistRequest request) throws ServiceRunException, ExistException, InputWrongException {
        if ("service".equals(request.getClientType())) {
            //服务提供方注册
            return this.serviceRegist(request);
        } else if ("customer".equals(request.getClientType())) {
            //服务请求方注册
            return this.customerRegist(request);
        } else if ("gateway".equals(request.getClientType())) {
            //网关注册
            return this.gatewayRegist(request);
        } else {
            //默认，进行serviceAndCunstomer注册
            return this.serviceAndCunstomerRegist(request);
        }
    }

    @Override
    public RegistResponse serviceRegist(RegistRequest request) throws ServiceRunException, ExistException, InputWrongException {
        String serviceType = request.getServiceType();
        String name = request.getName();

        ClientPool pool = ClientPool.getInstance();
        RegistResponse response = new RegistResponse();
        ServiceClient serviceClient = null;
        
        updateServiceLock.lock();
        
        try{
            int contains = pool.serviceContainsName(name);
            if (contains == -1) {
                //服务在两个列表中重复出现， 已经异常
                throw new ServiceRunException("服务注册");
            }
            if ("limited".equals(serviceType)) {
                //limitedService类型注册
                if (contains == 1) {
                    //在service中已经存在
                    throw new ExistException("服务名在非限定服务提供方中");
                }
                if (contains == 2) {
                    //在limitedService中已经存在
                    Map<String, LimitedServiceClient> limitService = pool.getLimitService(name);
                    serviceClient = this.limitedServiceRegist(limitService, request);
                }
                if (contains == 0) {
                    serviceClient = this.limitedServiceRegist(pool, request);
                }
                response.setClientType("limitedService");
            }else{
                //默认，service类型注册
                if (contains == 1) {
                    //在service中已经存在
                    Map<String, ServiceClient> service = pool.getService(name);
                    serviceClient = this.serviceRegist(service, request);
                }
                if (contains == 2) {
                    //在limitedService中已经存在
                    throw new ExistException("服务名在限定服务提供方中");

                }
                if (contains == 0) {
                    serviceClient =  this.serviceRegist(pool, request);
                }
                response.setClientType("service");
            }
        }finally{
            updateServiceLock.unlock();
        }
        
        response.setName(serviceClient.getName());
        response.setUniName(serviceClient.getUniName());
        response.setUrl(serviceClient.getUrl());
        response.setRasName(RasUtils.getRasName());
        
        return response;
    }

    @Override
    public RegistResponse customerRegist(RegistRequest request) throws ExistException {
        String name = request.getName();
        ClientPool pool = ClientPool.getInstance();
        RegistResponse response = new RegistResponse();
        CustomerClient customerClient = null;
        
        updateCustomerLock.lock();
        
        try{
            boolean contains = pool.customerContainsName(name);
            if (contains) {
                //在customer中已经存在
                throw new ExistException("服务名在服务消费方中");
            }else{
                customerClient = new CustomerClient();
                customerClient.setName(request.getName());
                String uniName;
                if(VerificateTool.notEmpty(request.getUniName())){
                    uniName = request.getUniName();
                }else{
                    uniName = request.getUrl();
                }
                customerClient.setUniName(uniName);
                customerClient.setUrl(request.getUrl());
                customerClient.setToken(TokenTool.createToken(new Date().getTime() + ""));
                
                pool.addCustomer(customerClient);
            }
        }finally{
            updateCustomerLock.unlock();
        }
        
        response.setClientType("customer");
        response.setName(customerClient.getName());
        response.setUniName(customerClient.getUniName());
        response.setUrl(customerClient.getUrl());
        response.setToken(customerClient.getToken());
        response.setRasName(RasUtils.getRasName());
        
        return response;
    }

    @Override
    public RegistResponse serviceAndCunstomerRegist(RegistRequest request) throws ServiceRunException, ExistException, InputWrongException {
        //serviceRegist
        RegistResponse serviceRegist = this.serviceRegist(request);
        
        String uniName = serviceRegist.getUniName();
        String token = serviceRegist.getToken();
        
        //以下流程基本和customerRegist一致
        String name = request.getName();
        ClientPool pool = ClientPool.getInstance();
        RegistResponse response = new RegistResponse();
        CustomerClient customerClient = null;
        
        updateCustomerLock.lock();
        
        try{
            boolean contains = pool.customerContainsName(name);
            if (contains) {
                //在customer中已经存在
                throw new ExistException("服务名在服务消费方中");
            }else{
                customerClient = new CustomerClient();
                customerClient.setName(request.getName());
                customerClient.setUniName(uniName);
                customerClient.setUrl(request.getUrl());
                customerClient.setToken(token);
                
                pool.addCustomer(customerClient);
            }
        }finally{
            updateCustomerLock.unlock();
        }
        
        response.setClientType("serviceAndCustomer");
        response.setName(customerClient.getName());
        response.setUniName(customerClient.getUniName());
        response.setUrl(customerClient.getUrl());
        response.setToken(customerClient.getToken());
        response.setRasName(RasUtils.getRasName());
        
        return response;
    }

    @Override
    public RegistResponse gatewayRegist(RegistRequest request) throws ExistException {
        String name = request.getName();
        ClientPool pool = ClientPool.getInstance();
        RegistResponse response = new RegistResponse();
        GatewayClient gatewayClient = null;
        
        updateGatewayLock.lock();
        
        try{
            boolean contains = pool.customerContainsName(name);
            if (contains) {
                //在customer中已经存在
                throw new ExistException("服务名在网关中");
            }else{
                gatewayClient = new GatewayClient();
                gatewayClient.setName(request.getName());
                String uniName;
                if(VerificateTool.notEmpty(request.getUniName())){
                    uniName = request.getUniName();
                }else{
                    uniName = request.getUrl();
                }
                gatewayClient.setUniName(uniName);
                gatewayClient.setUrl(request.getUrl());
                gatewayClient.setToken(TokenTool.createToken(new Date().getTime() + ""));
                
                pool.addGateway(gatewayClient);
            }
        }finally{
            updateGatewayLock.unlock();
        }
        
        response.setClientType("gateway");
        response.setName(gatewayClient.getName());
        response.setUniName(gatewayClient.getUniName());
        response.setUrl(gatewayClient.getUrl());
        response.setToken(gatewayClient.getToken());
        response.setRasName(RasUtils.getRasName());
        
        return response;
    }

    /**
     * limitedService服务增加实例
     * @param limitService
     * @param request
     */
    private LimitedServiceClient limitedServiceRegist(Map<String, LimitedServiceClient> limitService, RegistRequest request) throws ServiceRunException, InputWrongException {
        //用map来校验之前的interList是否都一致
        Map<String, Integer> interNumMap = new HashMap<>();
        boolean empty = true;   //第一条还没有放入，空
        for (LimitedServiceClient client : limitService.values()) {
            Set<String> interList = client.getInterList();
            if (!empty && interList.size() != interNumMap.size()) {
                //服务中的interList与之前的不一致
                throw new ServiceRunException("服务注册");
            }
            for (String interUrl : interList) {
                if (interNumMap.containsKey(interUrl)) {
                    interNumMap.put(interUrl, interNumMap.get(interUrl) + 1);
                } else {
                    interNumMap.put(interUrl, 1);
                }
            }
            if (!empty) {
                empty = false;
            }
        }
        for (int num : interNumMap.values()) {
            if (num != limitService.size()) {
                //inter的数量与limitService数量不一致
                throw new ServiceRunException("服务注册");
            }
        }
        //新的interList校验和Set封装
        Set<String> newInterList = new HashSet<>();
        String[] inters = request.getInterList().split(",");
        if (inters.length != interNumMap.size()) {
            //服务中的interList与之前的不一致
            throw new ServiceRunException("服务注册");
        }
        for (String interUrl : inters) {
            if (interNumMap.containsKey(interUrl)) {
                interNumMap.put(interUrl, interNumMap.get(interUrl) + 1);
            } else {
                interNumMap.put(interUrl, 1);
            }
            newInterList.add(interUrl);
        }
        for (int num : interNumMap.values()) {
            if (num != limitService.size()) {
                //inter的数量与limitService数量不一致
                throw new InputWrongException("可访问接口（与注册中心现有同名服务不一致）");
            }
        }

        //加入服务
        String uniName;
        if(VerificateTool.notEmpty(request.getUniName())){
            uniName = request.getUniName();
        }else{
            uniName = request.getUrl();
        }
        LimitedServiceClient limitedServiceClient = new LimitedServiceClient();
        limitedServiceClient.setName(request.getName());
        limitedServiceClient.setUniName(uniName);
        limitedServiceClient.setUrl(request.getUrl());
        limitedServiceClient.setToken(TokenTool.createToken(new Date().getTime() + ""));
        limitedServiceClient.setInterList(newInterList);
        
        limitService.put(uniName, limitedServiceClient);

        return limitedServiceClient;
    }

    /**
     * limitedService服务新增服务
     * @param request 
     */
    private LimitedServiceClient limitedServiceRegist(ClientPool pool, RegistRequest request) {
        //interList封装
        String[] inters = request.getInterList().split(",");
        Set<String> newInterList = new HashSet<>();
        for (String interUrl : inters) {
            newInterList.add(interUrl);
        }
        //加入服务
        String uniName;
        if(VerificateTool.notEmpty(request.getUniName())){
            uniName = request.getUniName();
        }else{
            uniName = request.getUrl();
        }
        LimitedServiceClient limitedServiceClient = new LimitedServiceClient();
        limitedServiceClient.setName(request.getName());
        limitedServiceClient.setUniName(uniName);
        limitedServiceClient.setUrl(request.getUrl());
        limitedServiceClient.setToken(TokenTool.createToken(new Date().getTime() + ""));
        limitedServiceClient.setInterList(newInterList);
        Map<String, LimitedServiceClient> newLimitedSevice = new HashMap<>();
        newLimitedSevice.put(uniName, limitedServiceClient);
        
        pool.addLimitService(request.getName(), newLimitedSevice);

        return limitedServiceClient;
    }

    /**
     * service服务增加实例
     * @param service
     * @param request
     * @return 
     */
    private ServiceClient serviceRegist(Map<String, ServiceClient> service, RegistRequest request) {
        //加入服务
        String uniName;
        if(VerificateTool.notEmpty(request.getUniName())){
            uniName = request.getUniName();
        }else{
            uniName = request.getUrl();
        }
        ServiceClient serviceClient = new ServiceClient();
        serviceClient.setName(request.getName());
        serviceClient.setUniName(uniName);
        serviceClient.setUrl(request.getUrl());
        serviceClient.setToken(TokenTool.createToken(new Date().getTime() + ""));
        service.put(uniName, serviceClient);
        
        return serviceClient;
    }

    /**
     * service服务新增服务
     * @param pool
     * @param request
     * @return 
     */
    private ServiceClient serviceRegist(ClientPool pool, RegistRequest request) {
        //加入服务
        String uniName;
        if(VerificateTool.notEmpty(request.getUniName())){
            uniName = request.getUniName();
        }else{
            uniName = request.getUrl();
        }
        ServiceClient serviceClient = new ServiceClient();
        serviceClient.setName(request.getName());
        serviceClient.setUniName(uniName);
        serviceClient.setUrl(request.getUrl());
        serviceClient.setToken(TokenTool.createToken(new Date().getTime() + ""));
        Map<String, ServiceClient> newSevice = new HashMap<>();
        newSevice.put(uniName, serviceClient);
        
        pool.addService(request.getName(), newSevice);

        return serviceClient;
    }

    @Override
    public ServiceResponse findService(FindServiceRequest findServiceRequest) throws TokenWrongException, NotExistException {
        ClientPool pool = ClientPool.getInstance();
        
        /*
         * 客户端校验
         */
        boolean auth = pool.containsCustomerOrGatewayByToken(findServiceRequest.getToken());
        if(!auth){
            throw new TokenWrongException();
        }
        
        /*
        * 服务端列表
        */
        String name = findServiceRequest.getName();
        ServiceResponse response = new ServiceResponse();
        Map<String, ServiceClient> service = pool.getService(name);
        if(service != null){
            response.setName(name);
            response.setServiceType("all");
            List<ServiceClientResponse> serviceClients = new ArrayList<>();
            for(ServiceClient client : service.values()){
                ServiceClientResponse serviceClient = new ServiceClientResponse();
                serviceClient.setName(client.getName());
                serviceClient.setUniName(client.getUniName());
                serviceClient.setUrl(client.getUrl());
                serviceClients.add(serviceClient);
            }
            response.setServiceClients(serviceClients);
        }else{
            Map<String, LimitedServiceClient> limitService = pool.getLimitService(name);
            if(limitService != null){
                response.setName(name);
                response.setServiceType("limited");
                List<LimitedServiceClientResponse> serviceClients = new ArrayList<>();
                for(LimitedServiceClient client : limitService.values()){
                    LimitedServiceClientResponse serviceClient = new LimitedServiceClientResponse();
                    serviceClient.setName(client.getName());
                    serviceClient.setUniName(client.getUniName());
                    serviceClient.setUrl(client.getUrl());
                    serviceClients.add(serviceClient);
                }
                response.setLimitedServiceClients(serviceClients);
            }else{
                throw new NotExistException("服务提供方");
            }
        }
        
        return response;
    }

    @Override
    public List<CustomerResponse> getCustomerClients() {
        ClientPool pool = ClientPool.getInstance();
        Map<String, CustomerClient> allCustomer = pool.getAllCustomer();
        List<CustomerResponse> responses = new ArrayList<>();
        for(CustomerClient client : allCustomer.values()){
            CustomerResponse response = new CustomerResponse();
            response.setName(client.getName());
            response.setUniName(client.getUniName());
            response.setUrl(client.getUrl());
            response.setFailNum(client.getFailNum());
            responses.add(response);
        }
        
        return responses;
    }

}
