package com.zy.zyrasc.balance;

import com.zy.zyrasc.vo.ServiceClient;
import java.util.List;
import java.util.Random;

/**
 * 简单负载均衡策略（随机）
 * @author wuhailong
 */
public class SimpleLoadBalanceMethod implements LoadBalanceMethod{

    private static Random random = new Random();
    
    @Override
    public ServiceClient balanceMethod(List<ServiceClient> clients){
        int rand = random.nextInt(clients.size());
        return clients.get(rand);
    }
}
