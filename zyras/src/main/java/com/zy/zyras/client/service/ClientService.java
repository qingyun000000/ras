package com.zy.zyras.client.service;

import cn.whl.commonutils.exception.ExistException;
import cn.whl.commonutils.exception.InputWrongException;
import cn.whl.commonutils.exception.NotExistException;
import cn.whl.commonutils.exception.ServiceRunException;
import cn.whl.commonutils.exception.TokenWrongException;
import com.zy.zyras.client.domain.Client;
import com.zy.zyras.client.domain.vo.CustomerResponse;
import com.zy.zyras.client.domain.vo.FindServiceRequest;
import com.zy.zyras.client.domain.vo.RegistRequest;
import com.zy.zyras.client.domain.vo.RegistResponse;
import com.zy.zyras.client.domain.vo.ServiceResponse;
import java.util.List;

/**
 * 业务层接口：客户端
 * @author wuhailong
 */
public interface ClientService {

    /**
     * 统一注册
     * @param request
     * @return
     * @throws cn.whl.commonutils.exception.ServiceRunException
     * @throws cn.whl.commonutils.exception.ExistException
     * @throws cn.whl.commonutils.exception.InputWrongException
     */
    public RegistResponse regist(RegistRequest request) throws ServiceRunException, ExistException, InputWrongException, ServiceRunException;
    
    /**
     * 服务提供方注册
     * @param request
     * @return
     * @throws cn.whl.commonutils.exception.ServiceRunException
     * @throws cn.whl.commonutils.exception.ExistException
     * @throws cn.whl.commonutils.exception.InputWrongException
     */
    public RegistResponse serviceRegist(RegistRequest request) throws ServiceRunException, ExistException, InputWrongException, ServiceRunException;

    /**
     * 服务调用方注册
     * @param request
     * @return 
     * @throws cn.whl.commonutils.exception.ExistException 
     * @throws cn.whl.commonutils.exception.ServiceRunException 
     */
    public RegistResponse customerRegist(RegistRequest request) throws ExistException, ServiceRunException;

    /**
     * 服务提供和调用方注册
     * @param request
     * @return 
     * @throws cn.whl.commonutils.exception.ServiceRunException 
     * @throws cn.whl.commonutils.exception.ExistException 
     * @throws cn.whl.commonutils.exception.InputWrongException 
     */
    public RegistResponse serviceAndCunstomerRegist(RegistRequest request) throws ServiceRunException, ExistException, InputWrongException, ServiceRunException;

    /**
     * 网关注册接口
     * @param request
     * @return 
     * @throws cn.whl.commonutils.exception.ExistException 
     * @throws cn.whl.commonutils.exception.ServiceRunException 
     */
    public RegistResponse gatewayRegist(RegistRequest request) throws ExistException, ServiceRunException;
    
    /**
     * 所有服务发现
     * @param token
     * @return 
     */
    public List<ServiceResponse> findAllService(String token) throws TokenWrongException;

    /**
     * 服务发现
     * @param findServiceRequest
     * @return
     * @throws cn.whl.commonutils.exception.TokenWrongException
     * @throws NotExistException 
     * @throws cn.whl.commonutils.exception.ServiceRunException 
     */
    public ServiceResponse findService(FindServiceRequest findServiceRequest) throws TokenWrongException, NotExistException, ServiceRunException;

    /**
     * 获取服务调用客户端
     * @return
     */
    public List<CustomerResponse> getCustomerClients();

    /**
     * 移除客户端
     * @param client
     * @return 
     */
    public boolean removeClient(Client client);

    /**
     * 心跳
     */
    public void heartbeat();

    
}
