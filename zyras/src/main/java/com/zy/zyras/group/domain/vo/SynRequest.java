package com.zy.zyras.group.domain.vo;

/**
 * 同步请求参数
 * @author wuhailong
 */
public class SynRequest {
 
    /**
     * 注册中心名
     */
    private String name;
    
    /**
     * 注册中心url
     */
    private String url;
    
    /**
     * 注册中心port
     */
    private int port;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
    
    
    
}
