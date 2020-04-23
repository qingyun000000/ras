package com.zy.zyras.client.domain.vo;

/**
 * 报文封装：注册
 * @author wuhailong
 */
public class RegistRequest {
    
    /*
    * 客户端port
    */
    private int port;
    
    /*
    * 客户端类型(service, customer, gateway)
    */
    private String clientType;
    
    /*
    * 服务类型（service类型下有效，all, limited)
    */
    private String serviceType;
    
    /*
     * 服务名（组名，一个组包含多个实例） 
     */
    private String name;
    
    /*
    * 唯一名（实例名，单一客户端，空则使用url）
    */
    private String uniName;
    
    /*
    * 地址（客户端地址, 客户端无需传入，服务器端从报文中获取）
    */
    private String url;
    
    //接口列表（LimitedServiceClient类型时）
    private String interList;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

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

    public String getUniName() {
        return uniName;
    }

    public void setUniName(String uniName) {
        this.uniName = uniName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInterList() {
        return interList;
    }

    public void setInterList(String interList) {
        this.interList = interList;
    }
    
}
