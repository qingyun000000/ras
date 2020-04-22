package com.zy.zyrasc.vo;

/**
 * 报文封装：服务调用token获取调用
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
 */
public class RequestTokenResponse {
    
    /*
    * 注册中心名
    */
    private String rasName;
    
    /*
    * 服务调用token
    */
    private String requestToken;

    public String getRasName() {
        return rasName;
    }

    public void setRasName(String rasName) {
        this.rasName = rasName;
    }

    public String getRequestToken() {
        return requestToken;
    }

    public void setRequestToken(String requestToken) {
        this.requestToken = requestToken;
    }
    
}
