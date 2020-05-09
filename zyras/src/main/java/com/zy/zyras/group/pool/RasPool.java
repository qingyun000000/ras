package com.zy.zyras.group.pool;

import cn.whl.commonutils.log.LoggerTools;
import com.alibaba.fastjson.JSON;
import com.zy.zyras.group.equality.domain.Ras;
import java.util.HashMap;
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
    private static volatile Map<String, Ras> rass;

    /*
    * 实例对象
     */
    private static volatile RasPool instance;

    /**
     * 私有构造方法
     */
    private RasPool() {
        rass = new HashMap<>();
    }

    /**
     * 获取实例对象
     *
     * @return
     */
    public static RasPool getInstance() {
        if (instance == null) {
            LoggerTools.log4j_write.info("初始化rasPool");
            synchronized (RasPool.class) {
                if (instance == null) {
                    instance = new RasPool();
                }
            }
            LoggerTools.log4j_write.info("初始化rasPool结束");
        }
        return instance;
    }

    /**
     * 获取所有注册中心
     * @return 
     */
    public Map<String, Ras> getAllRass() {
        return rass;
    }

    /**
     * 注册或更新
     * @param ras 
     */
    public void addRas(Ras ras) {
        rass.put(ras.getName(), ras);
    }

    /**
     * 增量同步服务
     * @param newRass 
     */
    public void synRass(Map<String, Ras> newRass){
        System.out.println(JSON.toJSON(rass));
        System.out.println(JSON.toJSON(newRass));
        for(String str : newRass.keySet()){
            if(!rass.containsKey(str)){
                rass.put(str, newRass.get(str));
            }else{
                System.out.println("注册中心已存在，不同步");
            }
        }
    }
   

}
