package com.zy.zyrasc.status;

import com.zy.zyrasc.balance.LoadBalanceMethod;


/**
 *
 * @author wuhailong
 */
public class ClientStatus {
    /*
     * 客户端类型
     */
    private static String type; 
    
    /*
     * 注册中心地址
     */
    private static String rasUrl; 
    
    /*
    * 客户端token
    */
    private static String token;
    
    /*
    * 请求token
    */
    private static String requestToken;
    
    /*
    * 负载均衡方法
    */
    private static LoadBalanceMethod balanceMethod;

    public static String getType() {
        return type;
    }

    public static void setType(String type) {
        ClientStatus.type = type;
    }

    public static String getRasUrl() {
        return rasUrl;
    }

    public static void setRasUrl(String rasUrl) {
        ClientStatus.rasUrl = rasUrl;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        ClientStatus.token = token;
    }

    public static String getRequestToken() {
        return requestToken;
    }

    public static void setRequestToken(String requestToken) {
        ClientStatus.requestToken = requestToken;
    }

    public static LoadBalanceMethod getBalanceMethod() {
        return balanceMethod;
    }

    public static void setBalanceMethod(LoadBalanceMethod balanceMethod) {
        ClientStatus.balanceMethod = balanceMethod;
    }
    
}
