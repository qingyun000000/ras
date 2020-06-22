package com.zy.zyras.set;

import com.zy.zyras.enums.BalanceMethod;
import com.zy.zyras.enums.GroupMode;
import com.zy.zyras.enums.WorkMode;
import java.util.List;

/**
 * 注册中心配置
 * @author wuhailong
 */
public class RasSet {
    
    /**
     * port
     */
    private static volatile int port;
    
    /*
    * 注册中心工作模式
    */
    private static volatile WorkMode workMode;
    
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
    private static volatile GroupMode groupMode;
    
    /**
     * master-slave模式master标志
     */
    private static volatile boolean master;
    
    /**
     * coord模式coord服务器地址
     */
    private static volatile String coordUrl;
    
    /**
     * zookeeper模式zookeeper服务器地址
     */
    private static volatile String zookeeperUrl;
    
    /*
    * 平等模式注册地址
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
        RasSet.port = port;
    }
    
    /**
     * 获取注册中心名
     * @return 
     */
    public static String getName(){
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
        RasSet.name = name;
    }
    
    /**
     * 修改注册中心token
     * @param requestToken
     */
    public static void setRequestToken(String requestToken){
        RasSet.requestToken = requestToken;
    }

    public static WorkMode getWorkMode() {
        return workMode;
    }

    public static void setWorkMode(WorkMode workMode) {
        RasSet.workMode = workMode;
    }

    public static String getGroupName() {
        return groupName;
    }

    public static void setGroupName(String groupName) {
        RasSet.groupName = groupName;
    }

    public static GroupMode getGroupMode() {
        return groupMode;
    }

    public static void setGroupMode(GroupMode groupMode) {
        RasSet.groupMode = groupMode;
    }

    public static boolean isMaster() {
        return master;
    }

    public static void setMaster(boolean master) {
        RasSet.master = master;
    }

    public static String getCoordUrl() {
        return coordUrl;
    }

    public static void setCoordUrl(String coordUrl) {
        RasSet.coordUrl = coordUrl;
    }

    public static String getZookeeperUrl() {
        return zookeeperUrl;
    }

    public static void setZookeeperUrl(String zookeeperUrl) {
        RasSet.zookeeperUrl = zookeeperUrl;
    }

    public static List<String> getRegistUrls() {
        return registUrls;
    }

    public static void setRegistUrls(List<String> registUrls) {
        RasSet.registUrls = registUrls;
    }

    public static int getGroupSynTime() {
        return groupSynTime;
    }

    public static void setGroupSynTime(int groupSynTime) {
        RasSet.groupSynTime = groupSynTime;
    }

    public static int getHearbeatTime() {
        return hearbeatTime;
    }

    public static void setHearbeatTime(int hearbeatTime) {
        RasSet.hearbeatTime = hearbeatTime;
    }

    public static int getHearbeatLeaveTimes() {
        return hearbeatLeaveTimes;
    }

    public static void setHearbeatLeaveTimes(int hearbeatLeaveTimes) {
        RasSet.hearbeatLeaveTimes = hearbeatLeaveTimes;
    }

    public static BalanceMethod getBalanceMethod() {
        return balanceMethod;
    }

    public static void setBalanceMethod(BalanceMethod balanceMethod) {
        RasSet.balanceMethod = balanceMethod;
    }

}
