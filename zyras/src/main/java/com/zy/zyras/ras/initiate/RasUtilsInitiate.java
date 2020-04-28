package com.zy.zyras.ras.initiate;

import cn.whl.commonutils.log.LoggerTools;
import com.zy.zyras.ras.enums.BalanceMethod;
import com.zy.zyras.ras.enums.GroupState;
import com.zy.zyras.ras.enums.WorkState;
import com.zy.zyras.ras.utils.RasUtils;
import com.zy.zyras.ras.utils.RequestTokenUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import com.zy.zyras.group.service.GroupService;

/**
 * 注册中心配置初始化
 * @author wuhailong
 */
@Component
public class RasUtilsInitiate implements ApplicationListener<ContextRefreshedEvent>{
    
    @Value("${server.port:8080}")
    private int port;
    
    @Value("${zyras.ras.name:emp}")
    private String name;
    
    @Value("${zyras.ras.state:single}")
    private String workState;
    
    @Value("${zyras.ras.ras:emp}")
    private String groupName1;
    
    @Value("${zyras.ras.group.name:emp}")
    private String groupName;
    
    @Value("${zyras.ras.group.state:equality}")
    private String groupState;
    
    @Value("${zyras.ras.group.regist:emp}")
    private String registUrls;
    
    @Value("${zyras.ras.group.syn_time:600}")
    private int groupSynTime;
    
    @Value("${zyras.client.heartbeat_time:600}")
    private int hearbeatTime;
    
    @Value("${zyras.client.hearbeat_leave_times:3}")
    private int hearbeatLeaveTimes;
    
    @Value("${zyras.banlance.method:simple}")
    private String balanceMethod;
    
    @Autowired
    private GroupService groupService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent e) {
        initRasUtils();
    }

    /**
     * 初始化注册中心配置
     */
    private void initRasUtils() {
        RasUtils.setPort(port);
        if("emp".equals(name)){
            name = "zyras" + new Date();
        }
        RasUtils.setName(name);
        
        if("emp".equals(groupName)){
            groupName = groupName1;
        }
        if("equality".equals(groupState)){
            groupName = name;
        }
        if("emp".equals(groupName)){
            throw new RuntimeException("gropuName不能为空");
        }
        RasUtils.setGroupName(groupName);
        
        if("group".equals(workState)){
            RasUtils.setWorkState(WorkState.group);
            //集群工作模式
            if("equality".equals(groupState)){
                //平等模式
                RasUtils.setGroupState(GroupState.equality);
                List<String> registUrls2 = getRegistUrls();
                RasUtils.setRegistUrls(registUrls2);
                RasUtils.setGroupSynTime(groupSynTime * 1000);
                RequestTokenUtils.setTokenByEquality();
                //集群注册
                groupService.registTo(registUrls2);
            }else{
                LoggerTools.log4j_write.info("不支持的集群工作模式");
            }
        }else{
            //单机工作模式
            RasUtils.setWorkState(WorkState.single);
            RequestTokenUtils.setTokenBySingle();
        }
        
        RasUtils.setHearbeatTime(hearbeatTime * 1000);
        RasUtils.setHearbeatLeaveTimes(hearbeatLeaveTimes);
        if("simple".equals(balanceMethod)){
            RasUtils.setBalanceMethod(BalanceMethod.simple);
        }else{
            LoggerTools.log4j_write.info("不支持的负载均衡策略");
        }
    }

    private List<String> getRegistUrls() {
        String[] urls = registUrls.split(",");
        List<String> registUrls2 = new ArrayList<>();
        for(String url : urls){
            registUrls2.add(url);
        }
        return registUrls2;
    }

    
    
    
}
