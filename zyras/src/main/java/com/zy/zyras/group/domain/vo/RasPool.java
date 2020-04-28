package com.zy.zyras.group.domain.vo;

import com.zy.zyras.group.domain.Ras;
import java.util.Map;

/*
* 单例模式，保存注册中心列表
 */
/**
 * 池：注册中心
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
 */
public class RasPool {

    /*
    * 服务消费方客户端列表
     */
    private  Map<String, Ras> allRass;

    public Map<String, Ras> getAllRass() {
        return allRass;
    }

    public void setAllRass(Map<String, Ras> allRass) {
        this.allRass = allRass;
    }

}
