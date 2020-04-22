package com.zy.zyras.group.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import com.zy.zyras.group.service.GroupService;

/**
 * 定时器：集群工作
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
 */
@Configuration
@EnableScheduling
public class GroupScheduler {
    
    /*
     * 客户端业务组件
     */
    @Autowired
    private GroupService rasService; 
    
    @Scheduled(cron = "0/5 * * * * ?")
    private void groupSyn(){
        rasService.groupSyn();
    }
    
}
