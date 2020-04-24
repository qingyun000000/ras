package com.zy.zyrasc.server;

import com.zy.zyrasc.client.ClientStatus;
import com.zy.zyrasc.client.Clients;
import com.zy.zyrasc.enums.ClientType;
import java.util.Map;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时器：连接注册中心
 * @author wuhailong
 */
@Configuration
@EnableScheduling
public class ServerWorkScheduler {
    
    /**
     * 发现服务
     * 用于从注册中心获取新的服务客户端，当新客户端加入后，需要等待该定时来获取（新服务除外，熔断器不拦截，会在第一次调用时获取）。
     */
    @Scheduled(cron = "0/20 * * * * ?")
    private void findService(){
        if(Clients.getType() != ClientType.service){
            FindServiceService.getAllService();
        }
    }
    
    /**
     * 更新requestToken
     */
    @Scheduled(cron = "0/10 * * * * ?")
    private void updateRequestToken(){
        Map<String, ClientStatus> clientStatusMap = Clients.getClientStatusMap();
        for(String ras : clientStatusMap.keySet()){
            GetRequestTokenService.getRequestToken(ras);
        }
    }
    
}
