package com.zy.zyras.group.equality.domain.vo;

import cn.whl.commonutils.log.LoggerTools;
import com.zy.zyras.client.domain.Client;
import com.zy.zyras.client.domain.CustomerClient;
import com.zy.zyras.client.domain.GatewayClient;
import com.zy.zyras.client.domain.LimitedServiceClient;
import com.zy.zyras.client.domain.ServiceClient;
import com.zy.zyras.client.domain.vo.FindServiceRequest;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
* 单例模式，保存客户端列表
 */
/**
 * 池：客户端
 *
 * @author wuhailong
 */
public class ClientPool {
    
    private Set<Client> allClients;

    /*
    * 服务提供方客户端列表
     */
    private Map<String, Map<String, ServiceClient>> allServices;

    /*
    * 有限服务提供方客户端列表
     */
    private Map<String, Map<String, LimitedServiceClient>> allLimitedServices;

    /*
    * 服务消费方客户端列表
     */
    private Map<String, CustomerClient> allCustomers;

    /*
    * 网关客户端列表
     */
    private Map<String, GatewayClient> allGateways;

    public Set<Client> getAllClients() {
        return allClients;
    }

    public void setAllClients(Set<Client> allClients) {
        this.allClients = allClients;
    }

    public Map<String, Map<String, ServiceClient>> getAllServices() {
        return allServices;
    }

    public void setAllServices(Map<String, Map<String, ServiceClient>> allServices) {
        this.allServices = allServices;
    }

    public Map<String, Map<String, LimitedServiceClient>> getAllLimitedServices() {
        return allLimitedServices;
    }

    public void setAllLimitedServices(Map<String, Map<String, LimitedServiceClient>> allLimitedServices) {
        this.allLimitedServices = allLimitedServices;
    }

    public Map<String, CustomerClient> getAllCustomers() {
        return allCustomers;
    }

    public void setAllCustomers(Map<String, CustomerClient> allCustomers) {
        this.allCustomers = allCustomers;
    }

    public Map<String, GatewayClient> getAllGateways() {
        return allGateways;
    }

    public void setAllGateways(Map<String, GatewayClient> allGateways) {
        this.allGateways = allGateways;
    }

}
