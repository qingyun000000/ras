package com.zy.zyras.enums;

/**
 * 注册中心集群工作模式
 * @author wuhailong
 */
public enum GroupMode {
    /*
    * EQUALITY : 平等工作模式，相互注册实现（类Eureka）, requestToken由集群名和时间戳生成，服务端通过解析token直接获取服务请求来自于那个集群。
    * MASTER_SLAVE : 主从工作模式，设置一台设备为主（模式为master),其余设备为从（slave）。
    *                从机都向主机注册，主机不用注册。
    *                主注册中心负责客户端同步，同步requestToken给从机，服务端从注册中心获取requestToken校验。
    *                主注册中心宕机则无法同步客户端和更新token。
    * COORD : coord协调，配置coord服务器地址。
    * ZOOKEEPER : zookeeper协调，配置zookeeper服务器地址。
    */
    EQUALITY, MASTER_SLAVE, COORD, ZOOKEEPER
}
