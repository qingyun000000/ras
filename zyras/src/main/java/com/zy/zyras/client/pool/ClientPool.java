package com.zy.zyras.client.pool;

import cn.whl.commonutils.log.LoggerTools;
import com.zy.zyras.client.domain.CustomerClient;
import com.zy.zyras.client.domain.GatewayClient;
import com.zy.zyras.client.domain.LimitedServiceClient;
import com.zy.zyras.client.domain.ServiceClient;
import com.zy.zyras.client.domain.vo.FindServiceRequest;
import java.util.HashMap;
import java.util.Map;

/*
* 单例模式，保存客户端列表
 */
/**
 * 池：客户端
 *
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
 */
public class ClientPool {

    /*
    * 服务提供方客户端列表
     */
    private static volatile Map<String, Map<String, ServiceClient>> serviceClients;

    /*
    * 有限服务提供方客户端列表
     */
    private static volatile Map<String, Map<String, LimitedServiceClient>> limitedServiceClients;

    /*
    * 服务消费方客户端列表
     */
    private static volatile Map<String, CustomerClient> customerClients;

    /*
    * 网关客户端列表
     */
    private static volatile Map<String, GatewayClient> gatewayClients;

    /*
    * 实例对象
     */
    private static volatile ClientPool instance;

    /**
     * 私有构造方法
     */
    private ClientPool() {
        serviceClients = new HashMap<>();
        limitedServiceClients = new HashMap<>();
        customerClients = new HashMap<>();
        gatewayClients = new HashMap<>();
    }

    /**
     * 获取实例对象
     *
     * @return
     */
    public static ClientPool getInstance() {
        if (instance == null) {
            LoggerTools.log4j_write.info("初始化clientPool");
            synchronized (ClientPool.class) {
                if (instance == null) {
                    instance = new ClientPool();
                }
            }
            LoggerTools.log4j_write.info("初始化clientPool结束");
        }
        return instance;
    }

    /**
     * 服务提供方客户端包含服务名
     *
     * @param name
     * @return
     */
    public int serviceContainsName(String name) {
        boolean contains1 = serviceClients.containsKey(name);
        boolean contains2 = limitedServiceClients.containsKey(name);
        if (contains1 && contains2) {
            return -1;               //服务在两个列表中重复出现
        } else if (contains1) {
            return 1;                //服务在service中出现
        } else if (contains2) {
            return 2;                //服务在limitedService中出现
        } else {
            return 0;                //服务未出现
        }
    }

    /**
     * 服务请求方客户端包含服务名
     *
     * @param name
     * @return
     */
    public boolean customerContainsName(String name) {
        return customerClients.containsKey(name);
    }

    /**
     * 获取service
     *
     * @param name
     * @return
     */
    public Map<String, ServiceClient> getService(String name) {
        return serviceClients.get(name);
    }

    /**
     * 获取LimitedService
     *
     * @param name
     * @return
     */
    public Map<String, LimitedServiceClient> getLimitService(String name) {
        return limitedServiceClients.get(name);
    }

    /**
     * 获取customer
     *
     * @param name
     * @return
     */
    public CustomerClient getCustomer(String name) {
        return customerClients.get(name);
    }

    /**
     * 加入LimitedService
     *
     * @param name
     * @param service
     * @return
     */
    public boolean addLimitService(String name, Map<String, LimitedServiceClient> service) {
        limitedServiceClients.put(name, service);
        return true;
    }

    /**
     * 加入Service
     *
     * @param name
     * @param service
     * @return
     */
    public boolean addService(String name, Map<String, ServiceClient> service) {
        serviceClients.put(name, service);
        return true;
    }

    /**
     * 加入customer
     *
     * @param customerClient
     * @return
     */
    public boolean addCustomer(CustomerClient customerClient) {
        customerClients.put(customerClient.getName(), customerClient);
        return true;
    }

    /**
     * 加入gateway
     *
     * @param gatewayClient
     * @return
     */
    public boolean addGateway(GatewayClient gatewayClient) {
        gatewayClients.put(gatewayClient.getName(), gatewayClient);
        return true;
    }

    /**
     * 根据token判断对方是否时服务请求方（含网关）
     *
     * @param token
     * @return
     */
    public boolean containsCustomerOrGatewayByToken(String token) {
        LoggerTools.log4j_write.info("请求方身份校验");
        for (GatewayClient client : gatewayClients.values()) {
            if (client.getToken().equals(token)) {
                return true;
            }
        }
        for (CustomerClient client : customerClients.values()) {
            if (client.getToken().equals(token)) {
                return true;
            }
        }
        LoggerTools.log4j_write.info("请求方身份校验不通过");
        return false;
    }

    /**
     * 根据token判断对方是否是正常客户端
     *
     * @param token
     * @return
     */
    public boolean containsCustomerOrServiceOrGatewayByToken(String token) {
        LoggerTools.log4j_write.info("用户身份校验");
        boolean result = this.containsCustomerOrGatewayByToken(token);
        if (result) {
            LoggerTools.log4j_write.info("用户身份校验通过");
            return true;
        }
        LoggerTools.log4j_write.info("服务方身份校验通过");
        for (Map<String, ServiceClient> service : serviceClients.values()) {
            for(ServiceClient client : service.values()){
                if (client.getToken().equals(token)) {
                    return true;
                }
            }
        }
        LoggerTools.log4j_write.info("非限定服务方身份校验不通过");
        LoggerTools.log4j_write.info("限定服务方身份校验");
        for (Map<String, LimitedServiceClient> service : limitedServiceClients.values()) {
            LoggerTools.log4j_write.info("限定服务方身份校验");
            for(LimitedServiceClient client : service.values()){
                if (client.getToken().equals(token)) {
                    LoggerTools.log4j_write.info("限定服务方身份校验");
                    return true;
                }
            }
        }
        LoggerTools.log4j_write.info("用户身份校验不通过");
        return false;
    }

    public Map<String, CustomerClient> getAllCustomer() {
        return customerClients;
    }

}
