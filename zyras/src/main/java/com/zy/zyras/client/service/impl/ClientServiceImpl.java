package com.zy.zyras.client.service.impl;

import cn.whl.commonutils.exception.ExistException;
import cn.whl.commonutils.exception.InputWrongException;
import cn.whl.commonutils.exception.NotExistException;
import cn.whl.commonutils.exception.ServiceRunException;
import cn.whl.commonutils.exception.TokenWrongException;
import cn.whl.commonutils.log.LoggerTools;
import cn.whl.commonutils.token.TokenTool;
import cn.whl.commonutils.verificate.VerificateTool;
import com.alibaba.fastjson.JSON;
import com.zy.zyras.client.domain.Client;
import com.zy.zyras.client.domain.CustomerClient;
import com.zy.zyras.client.domain.GatewayClient;
import com.zy.zyras.client.domain.LimitedServiceClient;
import com.zy.zyras.client.domain.ServiceClient;
import com.zy.zyras.client.domain.enums.ClientType;
import com.zy.zyras.client.domain.enums.ServiceType;
import com.zy.zyras.client.domain.vo.FindServiceRequest;
import com.zy.zyras.client.domain.vo.LimitedServiceClientResponse;
import com.zy.zyras.client.domain.vo.HeartbeatResponse;
import com.zy.zyras.client.domain.vo.RegistRequest;
import com.zy.zyras.client.domain.vo.RegistResponse;
import com.zy.zyras.client.domain.vo.RequestTokenRequest;
import com.zy.zyras.client.domain.vo.RequestTokenResponse;
import com.zy.zyras.client.domain.vo.ServiceClientResponse;
import com.zy.zyras.client.domain.vo.ServiceResponse;
import com.zy.zyras.client.pool.ClientPool;
import com.zy.zyras.client.service.ClientService;
import com.zy.zyras.set.RasSet;
import com.zy.zyras.utils.HttpUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.catalina.Executor;
import org.springframework.stereotype.Service;

/**
 * 业务层实现：客户端
 *
 * @author wuhailong
 */
@Service
public class ClientServiceImpl implements ClientService {

    //服务提供方修改锁
    private final Lock updateServiceLock = new ReentrantLock();

    //服务调用方修改锁
    private final Lock updateCustomerLock = new ReentrantLock();

    //网关修改锁
    private final Lock updateGatewayLock = new ReentrantLock();

    @Override
    public RegistResponse regist(RegistRequest request) throws ServiceRunException, ExistException, InputWrongException {
        if ("service".equals(request.getClientType())) {
            //服务提供方注册
            return this.serviceRegist(request);
        } else if ("customer".equals(request.getClientType())) {
            //服务调用方注册
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

        try {
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
                if (contains == 0 || contains == 2) {
                    serviceClient = this.limitedServiceRegist(contains, pool, request);
                }
                response.setServiceType(ServiceType.limited);
                try {
                    serviceClient.setToken(TokenTool.createToken(new Date().getTime() + ""));
                } catch (Exception ex) {
                    throw new ServiceRunException("token生成失败");
                }
            } else {
                //默认，service类型注册
                if (contains == 2) {
                    //在limitedService中已经存在
                    throw new ExistException("服务名在限定服务提供方中");

                }
                if (contains == 0 || contains == 1) {
                    serviceClient = this.serviceRegist(contains, pool, request);
                }
                response.setServiceType(ServiceType.all);
                try {
                    serviceClient.setToken(TokenTool.createToken(new Date().getTime() + ""));
                } catch (Exception ex) {
                    throw new ServiceRunException("token生成失败");
                }
            }
        } finally {
            updateServiceLock.unlock();
        }

        response.setClientType(ClientType.service);
        response.setName(serviceClient.getName());
        response.setUniName(serviceClient.getUniName());
        response.setUrl(serviceClient.getUrl());
        response.setRas(RasSet.getGroupName());
        response.setToken(serviceClient.getToken());
        response.setBalanceMethod(RasSet.getBalanceMethod());
        

        return response;
    }

    @Override
    public RegistResponse customerRegist(RegistRequest request) throws ExistException, ServiceRunException {
        String name = request.getName();
        ClientPool pool = ClientPool.getInstance();
        RegistResponse response = new RegistResponse();
        CustomerClient customerClient = null;

        updateCustomerLock.lock();

        try {
            boolean contains = pool.customerContainsName(name);
            if (contains) {
                //在customer中已经存在
                throw new ExistException("服务名在服务消费方中");
            } else {
                customerClient = new CustomerClient();
                customerClient.setName(request.getName());
                String uniName;
                if (VerificateTool.notEmpty(request.getUniName())) {
                    uniName = request.getUniName();
                } else {
                    uniName = request.getUrl();
                }
                customerClient.setUniName(uniName);
                customerClient.setUrl(request.getUrl());
                try {
                    customerClient.setToken(TokenTool.createToken(new Date().getTime() + ""));
                } catch (Exception ex) {
                    throw new ServiceRunException("token生成失败");
                }

                pool.addCustomer(customerClient);
            }
        } finally {
            updateCustomerLock.unlock();
        }

        response.setClientType(ClientType.customer);
        response.setName(customerClient.getName());
        response.setUniName(customerClient.getUniName());
        response.setUrl(customerClient.getUrl());
        response.setToken(customerClient.getToken());
        response.setRas(RasSet.getGroupName());
        response.setBalanceMethod(RasSet.getBalanceMethod());

        return response;
    }

    @Override
    public RegistResponse serviceAndCunstomerRegist(RegistRequest request) throws ServiceRunException, ExistException, InputWrongException {
        //serviceRegist
        RegistResponse serviceRegist = this.serviceRegist(request);

        String uniName = serviceRegist.getUniName();
        String token = serviceRegist.getToken();
        ServiceType serviceType = serviceRegist.getServiceType();

        //以下流程基本和customerRegist一致
        String name = request.getName();
        ClientPool pool = ClientPool.getInstance();
        RegistResponse response = new RegistResponse();
        CustomerClient customerClient = null;

        updateCustomerLock.lock();

        try {
            boolean contains = pool.customerContainsName(name);
            if (contains) {
                //在customer中已经存在
                throw new ExistException("服务名在服务消费方中");
            } else {
                customerClient = new CustomerClient();
                customerClient.setName(request.getName());
                customerClient.setUniName(uniName);
                customerClient.setUrl(request.getUrl());
                customerClient.setToken(token);

                pool.addCustomer(customerClient);
            }
        } finally {
            updateCustomerLock.unlock();
        }

        response.setClientType(ClientType.serviceAndCustomer);
        response.setServiceType(serviceType);
        response.setName(customerClient.getName());
        response.setUniName(customerClient.getUniName());
        response.setUrl(customerClient.getUrl());
        response.setToken(customerClient.getToken());
        response.setRas(RasSet.getGroupName());
        response.setBalanceMethod(RasSet.getBalanceMethod());

        return response;
    }

    @Override
    public RegistResponse gatewayRegist(RegistRequest request) throws ExistException, ServiceRunException {
        String name = request.getName();
        ClientPool pool = ClientPool.getInstance();
        RegistResponse response = new RegistResponse();
        GatewayClient gatewayClient = null;

        updateGatewayLock.lock();

        try {
            boolean contains = pool.gatewayContainsName(name);
            if (contains) {
                //在customer中已经存在
                throw new ExistException("服务名在网关中");
            } else {
                gatewayClient = new GatewayClient();
                gatewayClient.setName(request.getName());
                String uniName;
                if (VerificateTool.notEmpty(request.getUniName())) {
                    uniName = request.getUniName();
                } else {
                    uniName = request.getUrl();
                }
                gatewayClient.setUniName(uniName);
                gatewayClient.setUrl(request.getUrl());
                try {
                    gatewayClient.setToken(TokenTool.createToken(new Date().getTime() + ""));
                } catch (Exception ex) {
                    throw new ServiceRunException("token生成失败");
                }

                pool.addGateway(gatewayClient);
            }
        } finally {
            updateGatewayLock.unlock();
        }

        response.setClientType(ClientType.gateway);
        response.setName(gatewayClient.getName());
        response.setUniName(gatewayClient.getUniName());
        response.setUrl(gatewayClient.getUrl());
        response.setToken(gatewayClient.getToken());
        response.setRas(RasSet.getGroupName());
        response.setBalanceMethod(RasSet.getBalanceMethod());

        return response;
    }

//    /**
//     * limitedService服务增加实例
//     *
//     * @param limitService
//     * @param request
//     */
//    @Deprecated
//    private LimitedServiceClient verificationInterList(Map<String, LimitedServiceClient> limitService, RegistRequest request) throws ServiceRunException, InputWrongException {
//        //用map来校验之前的interList是否都一致
//        Map<String, Integer> interNumMap = new HashMap<>();
//        boolean empty = true;   //第一条还没有放入，空
//        for (LimitedServiceClient client : limitService.values()) {
//            Set<String> interList = client.getInterList();
//            if (!empty && interList.size() != interNumMap.size()) {
//                //服务中的interList与之前的不一致
//                throw new ServiceRunException("服务注册");
//            }
//            for (String interUrl : interList) {
//                if (interNumMap.containsKey(interUrl)) {
//                    interNumMap.put(interUrl, interNumMap.get(interUrl) + 1);
//                } else {
//                    interNumMap.put(interUrl, 1);
//                }
//            }
//            if (!empty) {
//                empty = false;
//            }
//        }
//        for (int num : interNumMap.values()) {
//            if (num != limitService.size()) {
//                //inter的数量与limitService数量不一致
//                throw new ServiceRunException("服务注册");
//            }
//        }
//        //新的interList校验和Set封装
//        Set<String> newInterList = new HashSet<>();
//        String[] inters = request.getInterList().split(",");
//        if (inters.length != interNumMap.size()) {
//            //服务中的interList与之前的不一致
//            throw new ServiceRunException("服务注册");
//        }
//        for (String interUrl : inters) {
//            if (interNumMap.containsKey(interUrl)) {
//                interNumMap.put(interUrl, interNumMap.get(interUrl) + 1);
//            } else {
//                interNumMap.put(interUrl, 1);
//            }
//            newInterList.add(interUrl);
//        }
//        for (int num : interNumMap.values()) {
//            if (num != limitService.size()) {
//                //inter的数量与limitService数量不一致
//                throw new InputWrongException("可访问接口（与注册中心现有同名服务不一致）");
//            }
//        }
//
//        //加入服务
//        String uniName;
//        if (VerificateTool.notEmpty(request.getUniName())) {
//            uniName = request.getUniName();
//        } else {
//            uniName = request.getUrl();
//        }
//        LimitedServiceClient limitedServiceClient = new LimitedServiceClient();
//        limitedServiceClient.setName(request.getName());
//        limitedServiceClient.setUniName(uniName);
//        limitedServiceClient.setUrl(request.getUrl());
//        try {
//            limitedServiceClient.setToken(TokenTool.createToken(new Date().getTime() + ""));
//        } catch (Exception ex) {
//            throw new ServiceRunException("token生成失败");
//        }
//        limitedServiceClient.setInterList(newInterList);
//
//        limitService.put(uniName, limitedServiceClient);
//
//        return limitedServiceClient;
//    }

    /**
     * limitedService服务新增服务
     *
     * @param request
     */
    private LimitedServiceClient limitedServiceRegist(int contains, ClientPool pool, RegistRequest request) throws ServiceRunException {
        //interList封装
        String[] inters = request.getInterList().split(",");
        Set<String> newInterList = new HashSet<>();
        for (String interUrl : inters) {
            newInterList.add(interUrl);
        }
        //加入服务
        String uniName;
        if (VerificateTool.notEmpty(request.getUniName())) {
            uniName = request.getUniName();
        } else {
            uniName = request.getUrl();
        }
        LimitedServiceClient limitedServiceClient = new LimitedServiceClient();
        limitedServiceClient.setName(request.getName());
        limitedServiceClient.setUniName(uniName);
        limitedServiceClient.setUrl(request.getUrl());
        try {
            limitedServiceClient.setToken(TokenTool.createToken(new Date().getTime() + ""));
        } catch (Exception ex) {
            throw new ServiceRunException("token生成失败");
        }
        limitedServiceClient.setInterList(newInterList);
        Map<String, LimitedServiceClient> newLimitedSevice = new HashMap<>();
        newLimitedSevice.put(uniName, limitedServiceClient);

        if(contains == 0){
            pool.addLimitService(request.getName(), newLimitedSevice);
        }else{
            pool.synLimitService(request.getName(), newLimitedSevice);
        }

        return limitedServiceClient;
    }

    /**
     * service服务新增
     *
     * @param pool
     * @param request
     * @return
     */
    private ServiceClient serviceRegist(int contains, ClientPool pool, RegistRequest request) throws ServiceRunException {
        //组装服务
        String uniName;
        if (VerificateTool.notEmpty(request.getUniName())) {
            uniName = request.getUniName();
        } else {
            uniName = request.getUrl();
        }
        ServiceClient serviceClient = new ServiceClient();
        serviceClient.setName(request.getName());
        serviceClient.setUniName(uniName);
        serviceClient.setUrl(request.getUrl());
        try {
            serviceClient.setToken(TokenTool.createToken(new Date().getTime() + ""));
        } catch (Exception ex) {
            throw new ServiceRunException("token生成失败");
        }
        
        Map<String, ServiceClient> newSevice = new HashMap<>();
        newSevice.put(uniName, serviceClient);
        if(contains == 0){
            pool.addService(request.getName(), newSevice);
        }else{
            pool.synService(request.getName(), newSevice);
        }

        return serviceClient;
    }

    @Override
    public List<ServiceResponse> findAllService(String token) throws TokenWrongException {
        ClientPool pool = ClientPool.getInstance();

        /*
         * 客户端校验
         */
        boolean auth = pool.containsCustomerOrGatewayByToken(token);
        if (!auth) {
            throw new TokenWrongException();
        }

        List<ServiceResponse> responses = new ArrayList<>();
        Map<String, Map<String, ServiceClient>> allService = pool.getAllServices();
        for (String serviceName : allService.keySet()) {
            ServiceResponse response = new ServiceResponse();
            Map<String, ServiceClient> service = allService.get(serviceName);
            response.setName(serviceName);
            response.setServiceType(ServiceType.all);
            List<ServiceClientResponse> serviceClients = new ArrayList<>();
            for (ServiceClient client : service.values()) {
                ServiceClientResponse serviceClient = new ServiceClientResponse();
                serviceClient.setUniName(client.getUniName());
                serviceClient.setUrl(client.getUrl());
                serviceClients.add(serviceClient);
            }
            response.setServiceClients(serviceClients);
            responses.add(response);
        }
        Map<String, Map<String, LimitedServiceClient>> allLimitedService = pool.getAllLimitedServices();
        for (String serviceName : allLimitedService.keySet()) {
            ServiceResponse response = new ServiceResponse();
            Map<String, LimitedServiceClient> service = allLimitedService.get(serviceName);
            response.setName(serviceName);
            response.setServiceType(ServiceType.limited);
            List<LimitedServiceClientResponse> serviceClients = new ArrayList<>();
            for (LimitedServiceClient client : service.values()) {
                LimitedServiceClientResponse serviceClient = new LimitedServiceClientResponse();
                serviceClient.setUniName(client.getUniName());
                serviceClient.setUrl(client.getUrl());
                serviceClient.setInterList(client.getInterList());
                serviceClients.add(serviceClient);
            }
            response.setLimitedServiceClients(serviceClients);
            responses.add(response);
        }

        return responses;
    }

    @Override
    public ServiceResponse findService(FindServiceRequest findServiceRequest) throws TokenWrongException, NotExistException {
        ClientPool pool = ClientPool.getInstance();

        /*
         * 客户端校验
         */
        boolean auth = pool.containsCustomerOrGatewayByToken(findServiceRequest.getToken());
        if (!auth) {
            throw new TokenWrongException();
        }

        /*
        * 服务端列表
         */
        String name = findServiceRequest.getName();
        ServiceResponse response = new ServiceResponse();
        Map<String, ServiceClient> service = pool.getService(name);
        if (service != null) {
            response.setName(name);
            response.setServiceType(ServiceType.all);
            List<ServiceClientResponse> serviceClients = new ArrayList<>();
            for (ServiceClient client : service.values()) {
                ServiceClientResponse serviceClient = new ServiceClientResponse();
                serviceClient.setUniName(client.getUniName());
                serviceClient.setUrl(client.getUrl());
                serviceClients.add(serviceClient);
            }
            response.setServiceClients(serviceClients);
        } else {
            Map<String, LimitedServiceClient> limitService = pool.getLimitService(name);
            if (limitService != null) {
                response.setName(name);
                response.setServiceType(ServiceType.limited);
                List<LimitedServiceClientResponse> serviceClients = new ArrayList<>();
                for (LimitedServiceClient client : limitService.values()) {
                    LimitedServiceClientResponse serviceClient = new LimitedServiceClientResponse();
                    serviceClient.setUniName(client.getUniName());
                    serviceClient.setUrl(client.getUrl());
                    serviceClient.setInterList(client.getInterList());
                    serviceClients.add(serviceClient);
                }
                response.setLimitedServiceClients(serviceClients);
            } else {
                throw new NotExistException("服务提供方");
            }
        }

        return response;
    }

    private boolean removeClient(Client client) {
        ClientPool pool = ClientPool.getInstance();
        if (client instanceof LimitedServiceClient) {
            updateServiceLock.lock();
            pool.removeLimitedServiceClient(client.getName(), client.getUniName());
            updateServiceLock.unlock();
        } else if (client instanceof ServiceClient) {
            updateServiceLock.lock();
            pool.removeServiceClient(client.getName(), client.getUniName());
            updateServiceLock.unlock();
        } else if (client instanceof GatewayClient) {
            updateGatewayLock.lock();
            pool.removeGatewayClient(client.getName());
            updateGatewayLock.unlock();
        } else if (client instanceof CustomerClient) {
            updateCustomerLock.lock();
            pool.removeCustomerClient(client.getName());
            updateCustomerLock.unlock();
        }

        return true;

    }

    @Override
    public void heartbeat() {
        LoggerTools.log4j_write.info("开始心跳连接检测");
        //获取心跳连接配置
        int hearbeatLeaveTimes = RasSet.getHearbeatLeaveTimes();
        int hearbeatTime = RasSet.getHearbeatTime();
        Long date = new Date().getTime();
        //获取客户端
        ClientPool pool = ClientPool.getInstance();
        Set<Client> Clients = pool.getAllClients();
        
        //创建线程池
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (Client client : Clients) {
            //时间校验
            if (client.getTms() == null || date - client.getTms() > hearbeatTime) {
                threadPool.submit(()->{
                    LoggerTools.log4j_write.info("连接" + client.getUniName());
                    Map<String, Object> params = new HashMap<>();
                    params.put("ras", RasSet.getGroupName());
                    String result = HttpUtil.doPost(client.getUrl() + "/zyras/heartbeat", params);
                    if (result != null) {
                        HeartbeatResponse response = JSON.parseObject(result, HeartbeatResponse.class);
                        if (response.isSuccess()) {
                            client.setFailNum(0);
                            client.setTms(date);
                        } else {
                            if (client.getFailNum() >= hearbeatLeaveTimes - 1) {
                                //心跳连接连续失败次数超过设置值，移除
                                this.removeClient(client);
                            } else {
                                client.setFailNum(client.getFailNum() + 1);
                                client.setTms(date);
                            }
                        }
                    } else {
                        if (client.getFailNum() >= hearbeatLeaveTimes - 1) {
                            //心跳连接连续失败次数超过设置值，移除
                            this.removeClient(client);
                        } else {
                            client.setFailNum(client.getFailNum() + 1);
                            client.setTms(date);
                        }
                    }
                });
            }
        }
        LoggerTools.log4j_write.info("心跳连接检测完成");
    }
    
    @Override
    public void synClients(com.zy.zyras.group.equality.domain.vo.ClientPool clientPool){
        //同步客户端列表,同步全部为增加，删除客户端均由心跳连接处理
        ClientPool clientPoolLocal = ClientPool.getInstance();
        
        //1.限制服务提供方
        updateServiceLock.lock();
        Map<String, Map<String, LimitedServiceClient>> allLimitedServices = clientPool.getAllLimitedServices();
        for (String str : allLimitedServices.keySet()) {
            if (clientPoolLocal.serviceContainsName(str) != 2) {
                clientPoolLocal.addLimitService(str, allLimitedServices.get(str));
            } else {
                clientPoolLocal.synLimitService(str, allLimitedServices.get(str));
            }
        }
        //2.非限制服务提供方
        Map<String, Map<String, ServiceClient>> allServices = clientPool.getAllServices();
        for (String str : allServices.keySet()) {
            if (clientPoolLocal.serviceContainsName(str) != 1) {
                clientPoolLocal.addService(str, allServices.get(str));
            } else {
                clientPoolLocal.synService(str, allServices.get(str));
            }
        }
        updateServiceLock.unlock();
        //3.服务调用方
        updateCustomerLock.lock();
        Map<String, CustomerClient> allCustomers = clientPool.getAllCustomers();
        for (String str : allCustomers.keySet()) {
            if (!clientPoolLocal.customerContainsName(str)) {
                clientPoolLocal.addCustomer(allCustomers.get(str));
            } else {
                System.out.println("已存在，不同步");
            }
        }
        updateCustomerLock.unlock();
        //4.网关
        updateGatewayLock.lock();
        Map<String, GatewayClient> allGateways = clientPool.getAllGateways();
        for (String str : allGateways.keySet()) {
            if (!clientPoolLocal.gatewayContainsName(str)) {
                clientPoolLocal.addGateway(allGateways.get(str));
            } else {
                System.out.println("已存在，不同步");
            }
        }
        updateGatewayLock.unlock();
    }

    @Override
    public RequestTokenResponse getServiceRequestToken(RequestTokenRequest request) throws TokenWrongException {
        LoggerTools.log4j_write.info("获取服务调用token");
        ClientPool pool = ClientPool.getInstance();
        
        //客户端校验
        boolean auth = pool.containsCustomerOrServiceOrGatewayByToken(request.getToken());
        if(!auth){
            LoggerTools.log4j_write.info("客户校验失败");
            throw new TokenWrongException();
        }
        
        RequestTokenResponse response = new RequestTokenResponse();
        response.setRasName(RasSet.getGroupName());
        response.setRequestToken(RasSet.getRequestToken());
        return response;
    }
}
