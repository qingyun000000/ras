package com.zy.zyras.ras.utils;

/**
 * 工具：注册中心
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
 */
public class RasUtils {
    
    /*
    * 注册中心名
    */
    private static volatile String name;
    
    /*
    * 服务请求token
    */
    /*
    * 注册中心定时更新服务请求token
    * 服务调用方需要定时从注册中心获取服务请求token
    * 在调用服务时需要传递服务请求token
    * 服务提供方拦截请求，进行权限校验（1，时校校验， 2，身份识别，开放给注册中心的接口权限校验）
    * 服务提供方可定时获取服务请求token（高安全模式），也可解码获得时间和注册中心名来校验（低安全模式） 
    * 服务提供方应当在时校上适当延迟（高安全模式需要保存至少前一个token），为网络传输提供时间窗口
    */
    private static volatile String requestToken;
    
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
}
