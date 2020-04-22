package com.zy.zyrasc.gateway;

/**
 * 真实服务
 * @author wuhailong
 */
public class RealService {
    
    /**
     * 注册中心集群名
     */
    private String ras;
    
    /**
     * 服务名
     */
    private String service;

    public String getRas() {
        return ras;
    }

    public void setRas(String ras) {
        this.ras = ras;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
    
}
