package com.zy.zyrasc.client;

import com.zy.zyrasc.vo.LimitedServiceClient;
import com.zy.zyrasc.vo.ServiceClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author wuhailong
 */
public class ServicePool {
    /*
    * 服务提供方客户端列表
     */
    private volatile Map<String,List<ServiceClient>> serviceClients;

    /*
    * 有限服务提供方客户端列表
     */
    private volatile Map<String, List<LimitedServiceClient>> limitedServiceClients;

    /**
     * 获取所有非限制服务
     * @return
     */
    public Map<String, List<ServiceClient>> getServiceClients() {
        return serviceClients;
    }

    /**
     * 获取所有限制服务
     * @return
     */
    public Map<String, List<LimitedServiceClient>> getLimitedServiceClients() {
        return limitedServiceClients;
    }

    /**
     * 增加非限制服务
     * @param name
     * @param scs
     */
    public void addService(String name, List<ServiceClient> scs) {
        serviceClients.put(name, scs);
    }

    /**
     * 增加限制服务
     * @param name
     * @param lscs 
     */
    public void addLimitedService(String name, List<LimitedServiceClient> lscs) {
        limitedServiceClients.put(name, lscs);
    }
    
    /**
     * 获取指定服务（的client列表）
     * @param name
     * @param url
     * @return 
     */
    public List<ServiceClient> getServiceForName(String name, String url){
        List<ServiceClient> response = new ArrayList<>();
        List<ServiceClient> service = serviceClients.get(name);
        if(service == null || service.isEmpty()){
            List<LimitedServiceClient> service2 = limitedServiceClients.get(name);
            for(LimitedServiceClient client : service2){
                if(client.getFused() == 0 || client.getInterList().contains(url)){
                    response.add(client);
                }
            }
        }else{
            for(ServiceClient client : service){
                if(client.getFused() == 0){
                    response.add(client);
                }
            }
        }
        
        return response;
    }
    
    /**
     * 包含服务
     * @param name
     * @return 
     */
    public boolean containsService(String name) {
        boolean contains = limitedServiceClients.containsKey(name);
        if(contains == false){
            return serviceClients.containsKey(name);
        }
        return true;
    }
    
}
