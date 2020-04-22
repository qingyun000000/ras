package com.zy.zyrasc.client;

import com.zy.zyrasc.balance.LoadBalanceMethod;
import com.zy.zyrasc.enums.ServiceType;
import java.util.Set;


/**
 *
 * @author wuhailong
 */
public class ClientStatus {
    
    /*
     * 客户端名
     */
    private String name;
    
    /*
     * 客户端唯一名
     */
    private String uniName;
    
    /*
     * 服务类型
     */
    private ServiceType serviceType;
    
    //接口列表
    private Set<String> interList;
    
    /*
     * 注册中心地址
     */
    private String rasUrl; 
    
    /*
    * 客户端token
    */
    private String token;
    
    /*
    * 请求token1
    */
    private String requestToken1;
    
    /*
    * 请求token2
    */
    private String requestToken2;
    
    /*
    * 负载均衡方法
    */
    private LoadBalanceMethod balanceMethod;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRasUrl() {
        return rasUrl;
    }

    public String getUniName() {
        return uniName;
    }

    public void setUniName(String uniName) {
        this.uniName = uniName;
    }

    public void setRasUrl(String rasUrl) {
        this.rasUrl = rasUrl;
    }

    public  String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRequestToken1() {
        return requestToken1;
    }

    public void setRequestToken1(String requestToken1) {
        this.requestToken1 = requestToken1;
    }

    public String getRequestToken2() {
        return requestToken2;
    }

    public void setRequestToken2(String requestToken2) {
        this.requestToken2 = requestToken2;
    }

    public LoadBalanceMethod getBalanceMethod() {
        return balanceMethod;
    }

    public void setBalanceMethod(LoadBalanceMethod balanceMethod) {
        this.balanceMethod = balanceMethod;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public Set<String> getInterList() {
        return interList;
    }

    public void setInterList(Set<String> interList) {
        this.interList = interList;
    }
    
}
