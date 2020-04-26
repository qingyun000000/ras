package com.zy.zyrasc.client;

import com.zy.zyrasc.enums.ClientType;
import java.util.HashMap;
import java.util.List;
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
     * 网关功能路径映射
     */
    private static List<GatewayMap> gatewayMaps;
    
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

    public static List<GatewayMap> getGatewayMaps() {
        return gatewayMaps;
    }

    public static void setGatewayMaps(List<GatewayMap> gatewayMaps) {
        Clients.gatewayMaps = gatewayMaps;
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
