package com.zy.zyrasc.vo;

import com.zy.zyrasc.enums.ServiceType;

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
    private ServiceType clientType;
    
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

    public String getRas() {
        return ras;
    }

    public void setRas(String ras) {
        this.ras = ras;
    }

    public ServiceType getClientType() {
        return clientType;
    }

    public void setClientType(ServiceType clientType) {
        this.clientType = clientType;
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
    
}
