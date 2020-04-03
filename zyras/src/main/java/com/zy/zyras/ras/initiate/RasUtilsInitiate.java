package com.zy.zyras.ras.initiate;

import cn.whl.commonutils.token.Token;
import cn.whl.commonutils.token.TokenTool;
import com.zy.zyras.ras.utils.RasUtils;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 注册中心配置初始化
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
 */
@Component
public class RasUtilsInitiate implements ApplicationListener<ContextRefreshedEvent>{
    
    @Value("${zyras.ras.name:emp}")
    private String name;
    
    @Value("${zyras.ras.group.name:emp}")
    private String groupName;
    
    @Value("${zyras.ras.group.regist:emp}")
    private String registUrls;
    
    @Value("${zyras.client.heartbeat_time:600}")
    private String hearbeatTime;
    
    @Value("${zyras.client.hearbeat_leave_times:3}")
    private String hearbeatLeaveTimes;
    
    @Value("${zyras.banlance.method:simple}")
    private String balanceMethod;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent e) {
        String token = TokenTool.createToken(groupName, new Token(){
            @Override
            public String createToken(String str) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public String decodeToken(String code) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        RasUtils.setRequestToken(token);
        RasUtils.setName(name);
    }
    
    
}
