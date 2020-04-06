package com.zy.zyras.ras.utils;

import com.zy.zyras.ras.enums.BalanceMethod;
import com.zy.zyras.ras.enums.GroupState;
import com.zy.zyras.ras.enums.WorkState;
import java.util.List;

/**
 * 工具：注册中心
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
 */
public class RasUtils {
    
    /**
     * port
     */
    private static volatile int port;
    
    /*
    * 注册中心工作模式
    */
    private static volatile WorkState workState;
    
    /*
    * 注册中心名
    */
    private static volatile String name;
    
    /*
    * 注册中心集群名
    */
    private static volatile String groupName;
    
    /*
    * 注册中心集群工作模式
    */
    private static volatile GroupState groupState;
    
    /*
    * 注册地址
    */
    private static volatile List<String> registUrls;
    
    /**
     * 集群同步间隔时间
     */
    private static volatile int groupSynTime;
    
    /*
    * 心跳连接间隔时间
    */
    private static volatile int hearbeatTime;
    
    /*
    * 心跳连接失败阈值次数
    */
    private static volatile int hearbeatLeaveTimes;
    
    /*
    * 负载均衡策略方法
    */
    private static volatile BalanceMethod balanceMethod;
    
    /*
    * 服务调用token
    */
    /*
    * 注册中心定时更新服务调用token
    * 服务调用方需要定时从注册中心获取服务调用token
    * 在调用服务时需要传递服务调用token
    * 服务提供方拦截请求，进行权限校验（1，时校校验， 2，身份识别，开放给注册中心的接口权限校验）
    * 服务提供方可定时获取服务调用token（高安全模式），也可解码获得时间和注册中心名来校验（低安全模式） 
    * 服务提供方应当在时校上适当延迟（高安全模式需要保存至少前一个token），为网络传输提供时间窗口
    */
    private static volatile String requestToken;

    public static int getPort() {
        return port;
    }

    public static void setPort(int port) {
        RasUtils.port = port;
    }
    
    /**
     * 获取注册中心名
     * @return 
     */
    public static String getRasName(){
        return name;
    }
    
    /**
     * 获取注册中心token
     * @return 
     */
    public static String getRequestToken(){
        return requestToken;
    }
    
    /**
     * 修改注册中心name
     * @param name
     */
    public static void setName(String name){
        RasUtils.name = name;
    }
    
    /**
     * 修改注册中心token
     * @param requestToken
     */
    public static void setRequestToken(String requestToken){
        RasUtils.requestToken = requestToken;
    }

    public static WorkState getWorkState() {
        return workState;
    }

    public static void setWorkState(WorkState workState) {
        RasUtils.workState = workState;
    }

    public static String getGroupName() {
        return groupName;
    }

    public static void setGroupName(String groupName) {
        RasUtils.groupName = groupName;
    }

    public static GroupState getGroupState() {
        return groupState;
    }

    public static void setGroupState(GroupState groupState) {
        RasUtils.groupState = groupState;
    }

    public static List<String> getRegistUrls() {
        return registUrls;
    }

    public static void setRegistUrls(List<String> registUrls) {
        RasUtils.registUrls = registUrls;
    }

    public static int getGroupSynTime() {
        return groupSynTime;
    }

    public static void setGroupSynTime(int groupSynTime) {
        RasUtils.groupSynTime = groupSynTime;
    }

    public static int getHearbeatTime() {
        return hearbeatTime;
    }

    public static void setHearbeatTime(int hearbeatTime) {
        RasUtils.hearbeatTime = hearbeatTime;
    }

    public static int getHearbeatLeaveTimes() {
        return hearbeatLeaveTimes;
    }

    public static void setHearbeatLeaveTimes(int hearbeatLeaveTimes) {
        RasUtils.hearbeatLeaveTimes = hearbeatLeaveTimes;
    }

    public static BalanceMethod getBalanceMethod() {
        return balanceMethod;
    }

    public static void setBalanceMethod(BalanceMethod balanceMethod) {
        RasUtils.balanceMethod = balanceMethod;
    }

}
