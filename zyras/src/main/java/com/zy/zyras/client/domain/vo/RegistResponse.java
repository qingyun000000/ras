package com.zy.zyras.client.domain.vo;

import com.zy.zyras.client.domain.enums.ClientType;
import com.zy.zyras.client.domain.enums.ServiceType;
import com.zy.zyras.ras.enums.BalanceMethod;

/**
 * 返回报文：注册
 * @author wuhailong
 */
public class RegistResponse {
    
    /*
    * 注册中心名
    */
    private String ras;
    
    /*
    * 客户端类型
    */
    private ClientType clientType;
    
    /*
    * 客户端类型
    */
    private ServiceType serviceType;
    
    /*
     * 服务名（组名，一个组包含多个实例） 
     */
    private String name;
    
    /*
    * 唯一名（实例名，单一客户端）
    */
    private String uniName;
    
    /*
    * 地址（客户端地址）
    */
    private String url;
    
    /*
    * token 身份识别标识
    */
    private String token;
    
    /*
    * 负载均衡策略
    */
    private BalanceMethod balanceMethod;

    public String getRas() {
        return ras;
    }

    public void setRas(String ras) {
        this.ras = ras;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public BalanceMethod getBalanceMethod() {
        return balanceMethod;
    }

    public void setBalanceMethod(BalanceMethod balanceMethod) {
        this.balanceMethod = balanceMethod;
    }
    
}
