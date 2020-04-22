package com.zy.zyrasc.server;

import com.alibaba.fastjson.JSON;
import com.zy.zyrasc.client.ClientStatus;
import com.zy.zyrasc.client.Clients;
import com.zy.zyrasc.enums.ClientType;
import com.zy.zyrasc.enums.ServiceType;
import com.zy.zyrasc.utils.HttpUtil;
import com.zy.zyrasc.vo.RegistResponse;
import com.zy.zyrasc.vo.RequestTokenResponse;
import com.zy.zyrasc.vo.ServiceResponse;
import com.zy.zyrasc.vo.ServiceResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 获取requestToken
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
 */
public class GetRequestTokenService {

    public static String getRequestToken(String ras) {
        ClientStatus clientStatus = Clients.getClientStatusMap().get(ras);
        String rasUrl = clientStatus.getRasUrl();
        String token = clientStatus.getToken();
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        String result = HttpUtil.doPost(rasUrl + "/authority/serviceRequest/token", params);
        ServiceResult response = JSON.parseObject(result, ServiceResult.class);
        RequestTokenResponse requestTokenResponse = (RequestTokenResponse)(response.getData());
        clientStatus.setRequestToken2(clientStatus.getRequestToken1());
        clientStatus.setRequestToken1(requestTokenResponse.getRequestToken());
        return requestTokenResponse.getRequestToken();
    }
    
    
}
