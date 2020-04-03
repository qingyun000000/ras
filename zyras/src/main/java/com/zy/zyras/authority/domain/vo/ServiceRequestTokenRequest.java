package com.zy.zyras.authority.domain.vo;

/**
 * 报文封装：服务请求token获取请求
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
 */
public class ServiceRequestTokenRequest {
    
    /*
    * token 身份识别标识
    */
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    
}
