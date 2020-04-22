package com.zy.zyras.client.domain;

import java.util.Set;

/**
 * 实体：客户端_服务提供方_仅提供指定接口供调用
 * @author wuhailong
 */
public class LimitedServiceClient extends ServiceClient{
    
    //接口列表
    private Set<String> interList;

    public Set<String> getInterList() {
        return interList;
    }

    public void setInterList(Set<String> interList) {
        this.interList = interList;
    }
    
    
}
