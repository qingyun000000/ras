package com.zy.zyras.manager.service;

import com.zy.zyras.manager.domain.vo.ClientResponse;
import java.util.List;
import java.util.Map;

/**
 *
 * @author wuhailong
 */
public interface ClientService {
    
    /**
     * 获取服务调用客户端
     * @return
     */
    public List<ClientResponse> getCustomerClients();

    /**
     * 获取所有网关客户端
     * @return 
     */
    public List<ClientResponse> getGatewayClients();

    public List<ClientResponse> getServiceClients();

    public List<ClientResponse> getLimitedServiceClients();
}
