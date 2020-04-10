package com.zy.zyrasc.server;

import com.alibaba.fastjson.JSON;
import com.zy.zyrasc.pool.ServicePool;
import com.zy.zyrasc.status.ClientStatus;
import com.zy.zyrasc.utils.HttpUtil;
import com.zy.zyrasc.vo.LimitedServiceClient;
import com.zy.zyrasc.vo.ServiceClient;
import com.zy.zyrasc.vo.ServiceResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * 服务发现
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
 */
@Component
public class FindServiceService {

    /**
     * 获取客户端
     * @param serviceName
     * @return 
     */
    public static List<ServiceClient> getServiceClients(String serviceName) {
        Map<String, List<ServiceClient>> serviceClients = ServicePool.getServiceClients();
        List<ServiceClient> clients = null;
        if(serviceClients.containsKey(serviceName)){
            clients = serviceClients.get(serviceName);
        }else{
            Map<String, List<LimitedServiceClient>> limitedServiceClients = ServicePool.getLimitedServiceClients();
            if(limitedServiceClients.containsKey(serviceName)){
                List<LimitedServiceClient> get = limitedServiceClients.get(serviceName);
                
            }else{
                //无法找到服务，从注册中心获取服务，完成后，重新获取
                findService(serviceName);
                clients = serviceClients.get(serviceName);
            }
        }
        return clients;
    }

    /**
     * 发现服务
     * @param serviceName 
     */
    private static void findService(String serviceName) {
        String rasUrl = ClientStatus.getRasUrl();
        String token = ClientStatus.getToken();
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("name", serviceName);
        String result = HttpUtil.doPost(rasUrl + "/client/find/service", params);
        ServiceResponse response = JSON.parseObject(result, ServiceResponse.class);
        if("limited".equals(response.getServiceType())){
            ServicePool.addLimitedService(response.getName(), response.getLimitedServiceClients());
        }else{
            ServicePool.addService(response.getName(), response.getServiceClients());
        }
    }
}
