package com.zy.zyrasc.client;

import com.zy.zyrasc.enums.ClientType;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * @author wuhailong
 */
public class Clients {
    
    /*
    * 向单注册中心集群注册
    */
    private static boolean singleton;
    
    /*
     * 客户端类型
     */
    private static ClientType type;
    
    /*
     * 客户端类型
     */
    private static String rpcInterfaceBase;
    
    /*
    * 客户端注册信息表（Key为 注册中心集群名）
    */
    private static Map<String, ClientStatus> clientStatusMap = new HashMap<>();
    
    /*
    * 服务池表（Key为 注册中心集群名）
    */
    private static Map<String, ServicePool> servicePoolMap = new HashMap<>();

    public static boolean isSingleton() {
        return singleton;
    }

    public static void setSingleton(boolean singleton) {
        Clients.singleton = singleton;
    }

    public static ClientType getType() {
        return type;
    }

    public static void setType(ClientType type) {
        Clients.type = type;
    }

    public static String getRpcInterfaceBase() {
        return rpcInterfaceBase;
    }

    public static void setRpcInterfaceBase(String rpcInterfaceBase) {
        Clients.rpcInterfaceBase = rpcInterfaceBase;
    }

    public static Map<String, ClientStatus> getClientStatusMap() {
        return clientStatusMap;
    }

    public static void setClientStatusMap(Map<String, ClientStatus> clientStatusMap) {
        Clients.clientStatusMap = clientStatusMap;
    }

    public static Map<String, ServicePool> getServicePoolMap() {
        return servicePoolMap;
    }

    public static void setServicePoolMap(Map<String, ServicePool> servicePoolMap) {
        Clients.servicePoolMap = servicePoolMap;
    }
    
}
