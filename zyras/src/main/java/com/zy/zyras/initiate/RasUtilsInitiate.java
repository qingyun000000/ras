package com.zy.zyras.initiate;

import cn.whl.commonutils.log.LoggerTools;
import com.zy.zyras.enums.BalanceMethod;
import com.zy.zyras.enums.GroupMode;
import com.zy.zyras.enums.WorkMode;
import com.zy.zyras.utils.RequestTokenUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import com.zy.zyras.group.service.GroupService;
import com.zy.zyras.set.RasSet;

/**
 * 注册中心配置初始化
 * @author wuhailong
 */
@Component
public class RasUtilsInitiate implements ApplicationListener<ContextRefreshedEvent>{
    
    /*
    * 端口号，和Spring Boot项目使用相同配置，这里保存用于向其他注册中心和客户端传递
    */
    @Value("${server.port:8080}")
    private int port;
    
    /*
    * 注册中心名
    */
    @Value("${zyras.ras.name:emp}")
    private String name;
    
    /*
    * 注册中心工作模式
    */
    @Value("${zyras.ras.work_mode:singleton}")
    private String workMode;
    
    /*
    * 注册中心集群名（备）
    */
    @Value("${zyras.ras.ras:emp}")
    private String groupName1;
    
    /*
    * 注册中心集群名（主）
    */
    @Value("${zyras.ras.group.name:emp}")
    private String groupName;
    
    /*
    * 集群工作模式
    */
    @Value("${zyras.ras.group.mode:equality}")
    private String groupMode;
    
    /*
    * 注册列表
    */
    @Value("${zyras.ras.group.regist:emp}")
    private String registUrls;
    
    /*
    * 集群同步时间
    */
    @Value("${zyras.ras.group.syn_time:600}")
    private int groupSynTime;
    
    /*
    * 客户端心跳连接检测间隔
    */
    @Value("${zyras.client.heartbeat_time:600}")
    private int hearbeatTime;
    
    /*
    * 客户端心跳连接检测连续失败被视为掉线的次数
    */
    @Value("${zyras.client.hearbeat_leave_times:3}")
    private int hearbeatLeaveTimes;
    
    /*
    * 负载均衡模式
    */
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
        RasSet.setPort(port);
        if("emp".equals(name)){
            name = "zyras" + new Date();
        }
        RasSet.setName(name);
        
        if("emp".equals(groupName)){
            groupName = groupName1;
        }
        if(!"group".equals(workMode)){
            groupName = name;
        }
        if("emp".equals(groupName)){
            throw new RuntimeException("gropuName不能为空");
        }
        RasSet.setGroupName(groupName);
        
        if("group".equals(workMode)){
            RasSet.setWorkMode(WorkMode.GROUP);
            //集群工作模式
            if("equality".equals(groupMode)){
                //平等模式
                RasSet.setGroupMode(GroupMode.EQUALITY);
                List<String> registUrls2 = getRegistUrls();
                RasSet.setRegistUrls(registUrls2);
                RasSet.setGroupSynTime(groupSynTime * 1000);
                groupService.updateTokenByGroup();
                //集群注册
                groupService.registTo();
            }else if("coord".equals(groupMode)){
                //coord模式
                RasSet.setGroupMode(GroupMode.COORD);
                
                //集群注册
                groupService.registTo();
            }else{
                LoggerTools.log4j_write.info("不支持的集群工作模式");
            }
        }else{
            //单机工作模式
            RasSet.setWorkMode(WorkMode.SINGLETON);
            RequestTokenUtils.setTokenBySingle();
        }
        
        RasSet.setHearbeatTime(hearbeatTime * 1000);
        RasSet.setHearbeatLeaveTimes(hearbeatLeaveTimes);
        if("simple".equals(balanceMethod)){
            RasSet.setBalanceMethod(BalanceMethod.SIMPLE);
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
