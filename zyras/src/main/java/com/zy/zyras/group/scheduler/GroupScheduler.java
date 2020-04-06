package com.zy.zyras.group.scheduler;

import cn.whl.commonutils.log.LoggerTools;
import com.zy.zyras.client.domain.Client;
import com.zy.zyras.client.pool.ClientPool;
import com.zy.zyras.client.service.ClientService;
import com.zy.zyras.group.domain.Ras;
import com.zy.zyras.group.pool.RasPool;
import com.zy.zyras.ras.utils.RasUtils;
import com.zy.zyras.utils.HttpUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
