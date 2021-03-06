package com.zy.zyras.authority.service;

import cn.whl.commonutils.exception.TokenWrongException;
import com.zy.zyras.authority.domain.vo.RequestTokenRequest;
import com.zy.zyras.authority.domain.vo.RequestTokenResponse;

/**
 * 业务层接口：权限
 * @author wuhailong
 */
public interface AuthorityService {

    /**
     * 获取服务调用token(
     * @param request
     * @return 
     * @throws cn.whl.commonutils.exception.TokenWrongException 
     */
    public RequestTokenResponse getServiceRequestToken(RequestTokenRequest request) throws TokenWrongException;
    
}
