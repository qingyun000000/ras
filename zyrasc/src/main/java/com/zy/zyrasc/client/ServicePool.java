package com.zy.zyrasc.client;

import com.zy.zyrasc.enums.FuseState;
import com.zy.zyrasc.vo.LimitedServiceClient;
import com.zy.zyrasc.vo.ServiceClient;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
    private volatile Map<String, List<ServiceClient>> serviceClients = new HashMap<>();

    /*
    * 有限服务提供方客户端列表
     */
    private volatile Map<String, List<LimitedServiceClient>> limitedServiceClients = new HashMap<>();

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
        for(ServiceClient client : scs){
            client.setFused(FuseState.NORMAL);
            client.setFuseTime(new Date());
            client.setFuseTimes(0);
        }
        serviceClients.put(name, scs);
    }

    /**
     * 增加限制服务
     * @param name
     * @param lscs
     */
    public void addLimitedService(String name, List<LimitedServiceClient> lscs) {
        for(LimitedServiceClient client : lscs){
            client.setFused(FuseState.NORMAL);
            client.setFuseTime(new Date());
            client.setFuseTimes(0);
        }
        limitedServiceClients.put(name, lscs);
    }

    /**
     * 获取指定服务（的client列表）:非熔断状态的client，含半开状态
     * @param name
     * @param url
     * @return
     */
    public List<ServiceClient> getNotFusedServiceForName(String name, String url) {
        List<ServiceClient> response = new ArrayList<>();
        List<ServiceClient> service = serviceClients.get(name);
        if (service == null || service.isEmpty()) {
            System.out.println("获取服务客户端");
            List<LimitedServiceClient> service2 = limitedServiceClients.get(name);
            if(service == null){
                return response;
            }
            for (LimitedServiceClient client : service2) {
                System.out.println(client.getUniName());
                if ((client.getFused() == FuseState.NORMAL|| client.getFused() == FuseState.HALF_FUSED) && client.getInterList().contains(url)) {
                    response.add(client);
                }
            }
        } else {
            System.out.println("获取服务客户端");
            for (ServiceClient client : service) {
                System.out.println(client.getUniName());
                if (client.getFused() == FuseState.NORMAL|| client.getFused() == FuseState.HALF_FUSED) {
                    response.add(client);
                }
            }
        }

        return response;
    }

    /**
     * 获取指定服务（的client列表）:正常状态的client，不含半开状态
     * @param name
     * @param url
     * @return
     */
    public List<ServiceClient> getNormalServiceForName(String name, String url) {
        List<ServiceClient> response = new ArrayList<>();
        List<ServiceClient> service = serviceClients.get(name);
        if (service == null || service.isEmpty()) {
            List<LimitedServiceClient> service2 = limitedServiceClients.get(name);
            for (LimitedServiceClient client : service2) {
                if (client.getFused() == FuseState.NORMAL && client.getInterList().contains(url)) {
                    response.add(client);
                }
            }
        } else {
            for (ServiceClient client : service) {
                if (client.getFused() == FuseState.NORMAL) {
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
        if (contains == false) {
            return serviceClients.containsKey(name);
        }
        return true;
    }

    /**
     * 获取服务客户端
     * @param name
     * @param client
     * @return
     */
    public ServiceClient getServiceClient(String name, ServiceClient client) {
        boolean contains = limitedServiceClients.containsKey(name);
        if (contains == false) {
            if (serviceClients.containsKey(name)) {
                for (ServiceClient cli : serviceClients.get(name)) {
                    if (cli.getUniName().equals(client.getUniName())) {
                        return cli;
                    }
                }
            }
        } else {
            for (LimitedServiceClient cli : limitedServiceClients.get(name)) {
                if (cli.getUniName().equals(client.getUniName())) {
                    return cli;
                }
            }
        }
        return null;
    }

    /**
     * 删除服务客户端
     * @param name
     * @param client
     */
    public void deleteServiceClient(String name, ServiceClient client) {
        boolean contains = limitedServiceClients.containsKey(name);
        if (contains == false) {
            if (serviceClients.containsKey(name)) {
                List<ServiceClient> get = serviceClients.get(name);
                for(ServiceClient cli : get){
                    if (cli.getUniName().equals(client.getUniName())) {
                        get.remove(cli);
                        return;
                    }
                }
            }
        } else {
            List<LimitedServiceClient> get = limitedServiceClients.get(name);
            for (LimitedServiceClient cli : get) {
                if (cli.getUniName().equals(client.getUniName())) {
                    get.remove(cli);
                    return;
                }
            }
        }
        return;
    }

    /**
     * 定时打开熔断至半熔断状态
     */
    public void openFused() {
        System.out.println("开始打开熔断处理");
        Date date = new Date();
        Long date1 = date.getTime();
        for(List<LimitedServiceClient> clients : limitedServiceClients.values()){
            for(LimitedServiceClient client : clients){
                if(client.getFused() == FuseState.FUSED && date1 - client.getFuseTime().getTime() > 10000){
                    client.setFused(FuseState.HALF_FUSED);
                }
            }
        }
        for(List<ServiceClient> clients : serviceClients.values()){
            for(ServiceClient client : clients){
                if(client.getFused() == FuseState.FUSED && date1 - client.getFuseTime().getTime() > 10000){
                    client.setFused(FuseState.HALF_FUSED);
                }
            }
        }
        System.out.println("开始打开熔断处理");
    }

}
