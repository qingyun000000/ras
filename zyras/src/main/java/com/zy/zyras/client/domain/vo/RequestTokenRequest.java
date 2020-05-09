package com.zy.zyras.client.domain.vo;

/**
 * 报文封装：服务调用token获取调用
 * @author wuhailong
 */
public class RequestTokenRequest {
    
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
