package com.zy.zyrasc.vo;

import java.util.List;

/**
 * 返回报文：服务
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
 */
public class ServiceResponse {
    
    /*
    * 服务类型（all, limited)
    */
    private String serviceType;
    
    /*
     * 服务名（组名，一个组包含多个实例） 
     */
    private String name;
    
    /*
    * 非限定服务端列表
    */
    private List<ServiceClient> serviceClients;
    
    /*
    * 限定服务端列表
    */
    private List<LimitedServiceClient> limitedServiceClients;

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ServiceClient> getServiceClients() {
        return serviceClients;
    }

    public void setServiceClients(List<ServiceClient> serviceClients) {
        this.serviceClients = serviceClients;
    }

    public List<LimitedServiceClient> getLimitedServiceClients() {
        return limitedServiceClients;
    }

    public void setLimitedServiceClients(List<LimitedServiceClient> limitedServiceClients) {
        this.limitedServiceClients = limitedServiceClients;
    }

}
