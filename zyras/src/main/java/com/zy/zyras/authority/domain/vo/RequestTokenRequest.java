package com.zy.zyras.authority.domain.vo;

/**
 * 报文封装：服务调用token获取调用
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
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