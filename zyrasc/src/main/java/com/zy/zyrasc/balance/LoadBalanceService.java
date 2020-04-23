package com.zy.zyrasc.balance;

import com.zy.zyrasc.client.Clients;
import com.zy.zyrasc.server.FindServiceService;
import com.zy.zyrasc.vo.ServiceClient;
import java.util.List;

/**
 * 负载均衡器
 * @author wuhailong
 */
public class LoadBalanceService {
    
    /**
     * 获取服务客户端
     * @param ras
     * @param serviceName
     * @param url
     * @return 
     */
    public static ServiceClient getServiceClient(String ras, String serviceName, String url){
        
        //先从池中获取客户端列表
        List<ServiceClient> clients = Clients.getServicePoolMap().get(ras).getServiceForName(serviceName, url);
        
        //重新获取服务。目前rpc和网关熔断后不会进入这里，前面直接降级了。
        if(clients == null){
            clients = FindServiceService.findService(ras, serviceName);
        }
        
        if(clients.isEmpty()){
            return null;
        }
        
        
        //负载均衡策略
        ServiceClient client = getServiceClient(clients, Clients.getClientStatusMap().get(ras).getBalanceMethod());
        
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
