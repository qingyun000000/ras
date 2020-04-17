package com.zy.zyrasc.balance;

import com.zy.zyrasc.pool.ServicePool;
import com.zy.zyrasc.server.FindServiceService;
import com.zy.zyrasc.status.ClientStatus;
import com.zy.zyrasc.vo.ServiceClient;
import java.util.List;

/**
 * 负载均衡器
 * @author wuhailong
 */
public class LoadBalanceService {
    
    /**
     * 获取服务客户端
     * @param serviceName
     * @return 
     */
    public static ServiceClient getServiceClient(String serviceName){
        
        //先从池中获取客户端列表
        List<ServiceClient> clients = ServicePool.getServiceForName(serviceName);
        
        //重新获取服务。目前rpc和网关熔断后不会进入这里，前面直接降级了。
        if(clients == null){
            clients = FindServiceService.getServiceClients(serviceName);
        }
        
        if(clients.isEmpty()){
            return null;
        }
        
        //负载均衡策略
        ServiceClient client = getServiceClient(clients, ClientStatus.getBalanceMethod());
        
        return client;
    }
    
    /**
     * 获取服务客户端
     * @param clients
     * @param balanceMethod
     * @return 
     */
    private static ServiceClient getServiceClient( List<ServiceClient> clients, LoadBalanceMethod balanceMethod){
        return balanceMethod.balanceMethod(clients);
    }

    
}
