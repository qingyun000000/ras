package com.zy.zyras.client.domain.vo;

/**
 * 报文封装：发现服务
 * @author wuhailong
 */
public class FindServiceRequest {
    
    /*
     * 服务名（组名，一个组包含多个实例） 
     */
    private String name;
    
    /*
    * token 身份识别标识
    */
    private String token;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
    
    
}
