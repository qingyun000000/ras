package com.zy.zyras.authority.domain.vo;

/**
 * 报文封装：服务请求token获取请求
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
 */
public class ServiceRequestTokenResponse {
    
    /*
    * 注册中心名
    */
    private String rasName;
    
    /*
    * 服务请求token
    */
    private String serviceRequestToken;

    public String getRasName() {
        return rasName;
    }

    public void setRasName(String rasName) {
        this.rasName = rasName;
    }

    public String getServiceRequestToken() {
        return serviceRequestToken;
    }

    public void setServiceRequestToken(String serviceRequestToken) {
        this.serviceRequestToken = serviceRequestToken;
    }
    
}
