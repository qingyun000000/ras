package com.zy.zyras.ras.scheduler;

import cn.whl.commonutils.log.LoggerTools;
import com.zy.zyras.ras.enums.GroupMode;
import com.zy.zyras.ras.enums.WorkMode;
import com.zy.zyras.ras.utils.RasUtils;
import com.zy.zyras.ras.utils.RequestTokenUtils;
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
    
    /**
     * requestToken刷新
     */
    @Scheduled(cron = "0 30 * * * ?")
    private void updateServiceRequestToken(){
        updateRequestToken();
    }
    
    private void updateRequestToken() {
        WorkMode workState = RasUtils.getWorkMode();
        GroupMode groupState = RasUtils.getGroupMode();
        
        if(workState == WorkMode.GROUP){
            //集群工作模式
            if(groupState == GroupMode.EQUALITY){
                //平等模式
                RequestTokenUtils.setTokenByEquality();
            }else{
                LoggerTools.log4j_write.info("不支持的集群工作模式");
            }
        }else{
            //单机工作模式
            RequestTokenUtils.setTokenBySingle();
        }
    }
    
}
