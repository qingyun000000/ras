package com.zy.zyrasc.balance;

import com.zy.zyrasc.vo.ServiceClient;
import java.util.List;

/**
 * 负载均衡策略
 * @author wuhailong
 */
public interface LoadBalanceMethod {
    
    /**
     * 负载均衡方法
     * @param clients
     * @return 
     */
    public ServiceClient balanceMethod(List<ServiceClient> clients);
}
