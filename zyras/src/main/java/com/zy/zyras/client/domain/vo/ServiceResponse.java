package com.zy.zyras.client.domain.vo;

import com.zy.zyras.client.domain.LimitedServiceClient;
import com.zy.zyras.client.domain.ServiceClient;
import com.zy.zyras.client.domain.enums.ServiceType;
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
    private ServiceType serviceType;
    
    /*
     * 服务名（组名，一个组包含多个实例） 
     */
    private String name;
    
    /*
    * 非限定服务端列表
    */
    private List<ServiceClientResponse> serviceClients;
    
    /*
    * 限定服务端列表
    */
    private List<LimitedServiceClientResponse> limitedServiceClients;

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ServiceClientResponse> getServiceClients() {
        return serviceClients;
    }

    public void setServiceClients(List<ServiceClientResponse> serviceClients) {
        this.serviceClients = serviceClients;
    }

    public List<LimitedServiceClientResponse> getLimitedServiceClients() {
        return limitedServiceClients;
    }

    public void setLimitedServiceClients(List<LimitedServiceClientResponse> limitedServiceClients) {
        this.limitedServiceClients = limitedServiceClients;
    }

}
