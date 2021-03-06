package com.zy.zyrasc.vo;

import com.zy.zyrasc.enums.FuseState;
import java.util.Date;

/**
 * 实体：客户端
 * @author wuhailong
 */
public class Client {
    
    /*
     * 服务名（组名，一个组包含多个实例） 
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
    
    /**
     * 熔断状态
     * 0：正常状态
     * 1：半开状态
     * 2：熔断状态
     * 3：掉线状态（该状态实际不会留存，当连续5次半开状态连接失败后，就删除该客户端。当所有客户端都掉线后，列表为空，则删除服务）
     */
    private FuseState fused;
    
    /**
     * 连续熔断次数
     */
    private int fuseTimes;
    
    /**
     * 熔断时间
     */
    private Date fuseTime;
    
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

    public FuseState getFused() {
        return fused;
    }

    public void setFused(FuseState fused) {
        this.fused = fused;
    }

    public int getFuseTimes() {
        return fuseTimes;
    }

    public void setFuseTimes(int fuseTimes) {
        this.fuseTimes = fuseTimes;
    }

    public Date getFuseTime() {
        return fuseTime;
    }

    public void setFuseTime(Date fuseTime) {
        this.fuseTime = fuseTime;
    }

}
