package com.zy.zyrasc.server;

import java.util.Date;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时器：连接注册中心
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
 */
@Configuration
@EnableScheduling
public class ServerWorkScheduler {
    
    /**
     * 刷新服务
     * 当方法，用于从注册中心获取新的服务客户端，当新客户端加入后，需要等待该定时来获取（新服务除外，熔断器不拦截，会在第一次调用时获取）。
     */
    @Scheduled(cron = "0 5 * * * ?")
    private void update(){
        
    }
    
    
    
}
