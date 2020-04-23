package com.zy.zyrasc.server;

import com.alibaba.fastjson.JSON;
import com.zy.zyrasc.client.ClientStatus;
import com.zy.zyrasc.client.Clients;
import com.zy.zyrasc.utils.HttpUtil;
import com.zy.zyrasc.vo.LimitedServiceClient;
import com.zy.zyrasc.vo.ServiceClient;
import com.zy.zyrasc.vo.ServiceResponse;
import com.zy.zyrasc.vo.ServiceResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务发现
 * @author wuhailong
 */
public class FindServiceService {

    /**
     * 发现服务
     *
     * @param ras
     * @param serviceName
     * @return
     */
    public static List<ServiceClient> findService(String ras, String serviceName) {
        ClientStatus clientStatus = Clients.getClientStatusMap().get(ras);
        String rasUrl = clientStatus.getRasUrl();
        String token = clientStatus.getToken();
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("name", serviceName);
        String result = HttpUtil.doPost(rasUrl + "/client/find/service", params);
        ServiceResponse response = (ServiceResponse) (JSON.parseObject(result, ServiceResult.class).getData());
        if ("limited".equals(response.getServiceType())) {
            Clients.getServicePoolMap().get(ras).addLimitedService(response.getName(), response.getLimitedServiceClients());
            List<ServiceClient> clients = new ArrayList<>();
            for (LimitedServiceClient client : response.getLimitedServiceClients()) {
                clients.add(client);
            }
            return clients;
        } else {
            Clients.getServicePoolMap().get(ras).addService(response.getName(), response.getServiceClients());
            return response.getServiceClients();
        }
    }

    /**
     * 获取所有服务
     */
    public static void getAllService() {
        Map<String, ClientStatus> clientStatusMap = Clients.getClientStatusMap();
        for (String ras : clientStatusMap.keySet()) {
            ClientStatus clientStatus = clientStatusMap.get(ras);
            String rasUrl = clientStatus.getRasUrl();
            String token = clientStatus.getToken();
            Map<String, String> params = new HashMap<>();
            params.put("token", token);
            String result = HttpUtil.doPost(rasUrl + "/client/find/allService", params);
            System.out.println(result);
            List<ServiceResponse> responses = (List<ServiceResponse>) (JSON.parseObject(result, ServiceResult.class).getData());
            for (ServiceResponse response : responses) {
                if ("limited".equals(response.getServiceType())) {
                    Clients.getServicePoolMap().get(ras).addLimitedService(response.getName(), response.getLimitedServiceClients());
                } else {
                    Clients.getServicePoolMap().get(ras).addService(response.getName(), response.getServiceClients());
                }
            }
        }

    }
}

//    /**
//     * 获取客户端
//     * @param ras
//     * @param serviceName
//     * @return
//     */
//    public static List<ServiceClient> getServiceClients(String ras, String serviceName) {
//        Map<String, List<ServiceClient>> serviceClients = Clients.getServicePoolMap().get(ras).getServiceClients();
//        List<ServiceClient> clients;
//        if (serviceClients.containsKey(serviceName)) {
//            clients = serviceClients.get(serviceName);
//        } else {
//            Map<String, List<LimitedServiceClient>> limitedServiceClients = Clients.getServicePoolMap().get(ras).getLimitedServiceClients();
//            if (limitedServiceClients.containsKey(serviceName)) {
//                List<LimitedServiceClient> limitedClients = limitedServiceClients.get(serviceName);
//                clients = new ArrayList<>();
//                for (LimitedServiceClient client : limitedClients) {
//                    clients.add(client);
//                }
//            } else {
//                //无法找到服务，从注册中心获取服务，完成后，重新获取
//                findService(ras, serviceName);
//                clients = serviceClients.get(serviceName);
//            }
//        }
//        return clients;
//    }
