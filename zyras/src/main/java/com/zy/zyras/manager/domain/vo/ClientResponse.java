package com.zy.zyras.manager.domain.vo;

/**
 * 返回报文：调用方
 * @author wuhailong
 */
public class ClientResponse {
    
    /*
    * 唯一名（实例名，单一客户端）
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
    * 心跳检测连续失败的次数
    */
    private int failNum;

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

    public int getFailNum() {
        return failNum;
    }

    public void setFailNum(int failNum) {
        this.failNum = failNum;
    }

}
