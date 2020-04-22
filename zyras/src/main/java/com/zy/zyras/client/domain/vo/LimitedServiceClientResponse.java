package com.zy.zyras.client.domain.vo;

import com.zy.zyras.client.domain.LimitedServiceClient;
import com.zy.zyras.client.domain.ServiceClient;
import java.util.List;
import java.util.Set;

/**
 * 返回报文：限定服务客户端
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
 */
public class LimitedServiceClientResponse {
    
    /*
    * 唯一名（实例名，单一客户端）
    */
    private String uniName;
    
    /*
    * 地址（客户端地址）
    */
    private String url; 
    
    //接口列表
    private Set<String> interList;

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

    public Set<String> getInterList() {
        return interList;
    }

    public void setInterList(Set<String> interList) {
        this.interList = interList;
    }
    
}
