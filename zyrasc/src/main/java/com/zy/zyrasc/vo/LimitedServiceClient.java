package com.zy.zyrasc.vo;

import java.util.List;
import java.util.Set;

/**
 * 实体：客户端_服务提供方_仅提供指定接口供调用
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
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
