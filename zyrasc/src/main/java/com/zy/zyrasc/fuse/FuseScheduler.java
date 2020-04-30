package com.zy.zyrasc.fuse;

import com.zy.zyrasc.client.ClientStatus;
import com.zy.zyrasc.client.Clients;
import com.zy.zyrasc.enums.ClientType;
import java.util.Map;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时器：熔断回半开位
 * @author wuhailong
 */
@Configuration
@EnableScheduling
public class FuseScheduler {
    
    /**
     * 熔断回复半开
     */
    @Scheduled(cron = "0/1 * * * * ?")
    private void openFused(){
        if(Clients.getType() != ClientType.SERVICE){
            Map<String, ClientStatus> clientStatusMap = Clients.getClientStatusMap();
            for(String ras : clientStatusMap.keySet()){
                FuseService.openFused(ras);
            }
        }
    }
    
}
