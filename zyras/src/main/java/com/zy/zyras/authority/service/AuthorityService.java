package com.zy.zyras.authority.service;

import cn.whl.commonutils.exception.TokenWrongException;
import cn.whl.commonutils.service.result.ServiceResult;
import com.zy.zyras.authority.domain.vo.ServiceRequestTokenRequest;
import com.zy.zyras.authority.domain.vo.ServiceRequestTokenResponse;

/**
 * 业务层接口：权限
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
 */
public interface AuthorityService {

    /**
     * 获取服务请求token(
     * @param request
     * @return 
     * @throws cn.whl.commonutils.exception.TokenWrongException 
     */
    public ServiceRequestTokenResponse getServiceRequestToken(ServiceRequestTokenRequest request) throws TokenWrongException;
    
}
