package com.zy.zyras.authority.service.impl;

import cn.whl.commonutils.exception.TokenWrongException;
import cn.whl.commonutils.log.LoggerTools;
import com.zy.zyras.authority.domain.vo.RequestTokenRequest;
import com.zy.zyras.authority.domain.vo.RequestTokenResponse;
import com.zy.zyras.authority.service.AuthorityService;
import com.zy.zyras.client.pool.ClientPool;
import com.zy.zyras.ras.utils.RasUtils;
import org.springframework.stereotype.Service;

/**
 * 业务层实现：权限
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
 */
@Service
public class AuthorityServiceImpl implements AuthorityService{

    @Override
    public RequestTokenResponse getServiceRequestToken(RequestTokenRequest request) throws TokenWrongException {
        LoggerTools.log4j_write.info("获取服务调用token");
        ClientPool pool = ClientPool.getInstance();
        /*
         * 客户端校验
         */
        boolean auth = pool.containsCustomerOrServiceOrGatewayByToken(request.getToken());
        if(!auth){
            LoggerTools.log4j_write.info("客户校验失败");
            throw new TokenWrongException();
        }
        
        RequestTokenResponse response = new RequestTokenResponse();
        response.setRasName(RasUtils.getRasName());
        response.setRequestToken(RasUtils.getRequestToken());
        return response;
    }
    
}
