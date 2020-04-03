package com.zy.zyras.client.service;

import cn.whl.commonutils.exception.ExistException;
import cn.whl.commonutils.exception.InputWrongException;
import cn.whl.commonutils.exception.NotExistException;
import cn.whl.commonutils.exception.ServiceRunException;
import cn.whl.commonutils.exception.TokenWrongException;
import cn.whl.commonutils.service.result.ServiceResult;
import com.zy.zyras.client.domain.vo.CustomerResponse;
import com.zy.zyras.client.domain.vo.FindServiceRequest;
import com.zy.zyras.client.domain.vo.RegistRequest;
import com.zy.zyras.client.domain.vo.RegistResponse;
import com.zy.zyras.client.domain.vo.ServiceClientResponse;
import com.zy.zyras.client.domain.vo.ServiceResponse;
import java.util.List;

/**
 * 业务层接口：客户端
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
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
    public RegistResponse regist(RegistRequest request) throws ServiceRunException, ExistException, InputWrongException;
    
    /**
     * 服务提供方注册
     * @param request
     * @return
     * @throws cn.whl.commonutils.exception.ServiceRunException
     * @throws cn.whl.commonutils.exception.ExistException
     * @throws cn.whl.commonutils.exception.InputWrongException
     */
    public RegistResponse serviceRegist(RegistRequest request) throws ServiceRunException, ExistException, InputWrongException;

    /**
     * 服务请求方注册
     * @param request
     * @return 
     * @throws cn.whl.commonutils.exception.ExistException 
     */
    public RegistResponse customerRegist(RegistRequest request) throws ExistException;

    /**
     * 服务提供和请求方注册
     * @param request
     * @return 
     * @throws cn.whl.commonutils.exception.ServiceRunException 
     * @throws cn.whl.commonutils.exception.ExistException 
     * @throws cn.whl.commonutils.exception.InputWrongException 
     */
    public RegistResponse serviceAndCunstomerRegist(RegistRequest request) throws ServiceRunException, ExistException, InputWrongException;

    /**
     * 网关注册接口
     * @param request
     * @return 
     * @throws cn.whl.commonutils.exception.ExistException 
     */
    public RegistResponse gatewayRegist(RegistRequest request) throws ExistException;

    /**
     * 服务发现
     * @param findServiceRequest
     * @return
     * @throws cn.whl.commonutils.exception.TokenWrongException
     * @throws NotExistException 
     */
    public ServiceResponse findService(FindServiceRequest findServiceRequest) throws TokenWrongException, NotExistException;

    public List<CustomerResponse> getCustomerClients();
}
