package com.zy.zyras.client.scheduler;

import com.zy.zyras.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/*
* 心跳连接检测，用于检查客户端连接状态
*/

/**
 * 定时器：心跳检测
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
 */
@Configuration
@EnableScheduling
public class HeartbeatScheduler {
    
    /*
     * 客户端业务组件
     */
    @Autowired
    private ClientService clientService; 
    
    @Scheduled(cron = "0/5 * * * * ?")
    private void heartbeatConnect(){
        clientService.heartbeat();
    }
    
}
