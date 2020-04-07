package com.zy.zyrasc.pool;

import com.zy.zyrasc.vo.LimitedServiceClient;
import com.zy.zyrasc.vo.ServiceClient;
import java.util.List;
import java.util.Map;

/**
 *
 * @author wuhailong
 */
public class ServicePool {
    /*
    * 服务提供方客户端列表
     */
    private static volatile Map<String,List<ServiceClient>> serviceClients;

    /*
    * 有限服务提供方客户端列表
     */
    private static volatile Map<String, List<LimitedServiceClient>> limitedServiceClients;

    public static Map<String, List<ServiceClient>> getServiceClients() {
        return serviceClients;
    }

    public static Map<String, List<LimitedServiceClient>> getLimitedServiceClients() {
        return limitedServiceClients;
    }

    public static void addService(String name, List<ServiceClient> scs) {
        serviceClients.put(name, scs);
    }

    public static void addLimitedService(String name, List<LimitedServiceClient> lscs) {
        limitedServiceClients.put(name, lscs);
    }
    
    
}
