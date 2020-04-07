package com.zy.zyrasc.balance;

import com.zy.zyrasc.server.FindServiceService;
import com.zy.zyrasc.status.ClientStatus;
import com.zy.zyrasc.vo.ServiceClient;
import java.util.List;
import java.util.Random;

/**
 * 负载均衡器
 * @author wuhailong
 */
public class LoadBalanceService {
    
    private static Random random = new Random();
    
    /**
     * 获取服务客户端
     * @param serviceName
     * @return 
     */
    public static ServiceClient getServiceClient(String serviceName){
        
        //获取服务客户端列表
        List<ServiceClient> clients = FindServiceService.getServiceClients(serviceName);
        
        //负载均衡策略
        ServiceClient client = getServiceClient(clients, ClientStatus.getBalanceMethod());
        
        return client;
    }
    
    private static ServiceClient getServiceClient( List<ServiceClient> clients, LoadBalanceMethod balanceMethod){
        return balanceMethod.balanceMethod(clients);
    }

    
}
