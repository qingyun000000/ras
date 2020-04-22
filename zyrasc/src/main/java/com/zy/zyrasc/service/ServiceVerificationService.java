package com.zy.zyrasc.service;

import com.zy.zyrasc.balance.*;
import com.zy.zyrasc.client.ClientStatus;
import com.zy.zyrasc.server.FindServiceService;
import com.zy.zyrasc.client.Clients;
import com.zy.zyrasc.enums.ServiceType;
import com.zy.zyrasc.exception.RasWrongException;
import com.zy.zyrasc.exception.RequestTokenWrongException;
import com.zy.zyrasc.exception.ServiceBeLimitedException;
import com.zy.zyrasc.server.GetRequestTokenService;
import com.zy.zyrasc.vo.ServiceClient;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;

/**
 * 服务校验服务
 *
 * @author wuhailong
 */
public class ServiceVerificationService {

    /**
     * 校验请求
     * @param request
     * @return
     * @throws com.zy.zyrasc.exception.RequestTokenWrongException
     * @throws com.zy.zyrasc.exception.ServiceBeLimitedException
     * @throws com.zy.zyrasc.exception.RasWrongException
     */
    public static boolean verificateRequest(HttpServletRequest request) throws RequestTokenWrongException, ServiceBeLimitedException, RasWrongException {
        String ras = (String) request.getAttribute("zyras");
        Map<String, ClientStatus> clientStatusMap = Clients.getClientStatusMap();
        if (clientStatusMap.containsKey(ras)) {
            ClientStatus clientStatus = clientStatusMap.get(ras);
            String requestToken = (String) request.getAttribute("zyrasRequestToken");

            //请求方身份判断
            if (!requestToken.equals(clientStatus.getRequestToken1())
                    && !requestToken.equals(clientStatus.getRequestToken2())) {
                //有可能是请求方先更新，再从注册中心刷新一次，并刷新clientStatus
                String newRequestToken = GetRequestTokenService.getRequestToken(ras);
                if(!requestToken.equals(newRequestToken)){
                    throw new RequestTokenWrongException("token:" + requestToken + "不正确");
                }
            }

            if (clientStatus.getServiceType() == ServiceType.limited) {
                //判断请求
                Set<String> interList = clientStatus.getInterList();
                for (String str : interList) {
                    if (str.equals(request.getRequestURI())) {
                        return true;
                    }
                }
                throw new ServiceBeLimitedException("服务:" + request.getRequestURI() + "被限制访问");
            } else {
                return true;
            }
        } else {
            throw new RasWrongException(ras + "为错误的ras");
        }

    }

}
