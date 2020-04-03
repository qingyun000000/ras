package com.zy.zyras.client.domain.vo;

/**
 * 返回报文：注册
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
 */
public class RegistResponse {
    
    /*
    * 注册中心名
    */
    private String rasName;
    
    /*
    * 客户端类型
    */
    private String clientType;
    
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

    public String getRasName() {
        return rasName;
    }

    public void setRasName(String rasName) {
        this.rasName = rasName;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
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
