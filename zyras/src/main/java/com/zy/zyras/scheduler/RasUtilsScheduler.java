package com.zy.zyras.scheduler;

import com.zy.zyras.group.service.GroupService;
import com.zy.zyras.enums.WorkMode;
import com.zy.zyras.set.RasSet;
import com.zy.zyras.utils.RequestTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时器：RasUtils维护
 * @author wuhailong
 */
@Configuration
@EnableScheduling
public class RasUtilsScheduler {
    
    @Autowired
    private GroupService groupService;
    
    /**
     * requestToken刷新
     */
    @Scheduled(cron = "0 30 * * * ?")
    private void updateServiceRequestToken(){
        updateRequestToken();
    }
    
    private void updateRequestToken() {
        WorkMode workState = RasSet.getWorkMode();
        if(workState == WorkMode.GROUP){
            groupService.updateTokenByGroup();
        }else{
            //单机工作模式
            RequestTokenUtils.setTokenBySingle();
        }
    }
    
}
