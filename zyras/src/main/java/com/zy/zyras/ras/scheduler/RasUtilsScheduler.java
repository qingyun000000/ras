package com.zy.zyras.ras.scheduler;

import cn.whl.commonutils.token.TokenTool;
import com.zy.zyras.ras.utils.RasUtils;
import java.util.Date;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时器：RasUtils维护
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
 */
@Configuration
@EnableScheduling
public class RasUtilsScheduler {
    
    /**
     * requestToken刷新
     */
    @Scheduled(cron = "0 30 * * * ?")
    private void updateServiceRequestToken(){
        String token = TokenTool.createToken(new Date().getTime() + "");
        RasUtils.setRequestToken(token);
    }
    
}
