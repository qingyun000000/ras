package com.zy.zyras.manager.service.impl;

import com.zy.zyras.client.domain.CustomerClient;
import com.zy.zyras.client.domain.GatewayClient;
import com.zy.zyras.client.domain.LimitedServiceClient;
import com.zy.zyras.client.domain.ServiceClient;
import com.zy.zyras.client.pool.ClientPool;
import com.zy.zyras.manager.domain.vo.ClientResponse;
import com.zy.zyras.manager.service.ClientService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * 业务层实现：客户端
 *
 * @author wuhailong
 */
@Service
public class ClientServiceImp implements ClientService {

    @Override
    public List<ClientResponse> getCustomerClients() {
        ClientPool pool = ClientPool.getInstance();
        Map<String, CustomerClient> allCustomer = pool.getAllCustomer();
        List<ClientResponse> responses = new ArrayList<>();
        for (CustomerClient client : allCustomer.values()) {
            ClientResponse response = new ClientResponse();
            response.setName(client.getName());
            response.setUniName(client.getUniName());
            response.setUrl(client.getUrl());
            response.setFailNum(client.getFailNum());
            responses.add(response);
        }

        return responses;
    }

    @Override
    public List<ClientResponse> getGatewayClients() {
        ClientPool pool = ClientPool.getInstance();
        Map<String, GatewayClient> allCateway = pool.getAllGateway();
        List<ClientResponse> responses = new ArrayList<>();
        for (GatewayClient client : allCateway.values()) {
            ClientResponse response = new ClientResponse();
            response.setName(client.getName());
            response.setUniName(client.getUniName());
            response.setUrl(client.getUrl());
            response.setFailNum(client.getFailNum());
            responses.add(response);
        }

        return responses;
    }

    @Override
    public List<ClientResponse> getServiceClients() {
        ClientPool pool = ClientPool.getInstance();
        Map<String, Map<String, ServiceClient>> allService = pool.getAllService();
        List<ClientResponse> responses = new ArrayList<>();
        for (Map<String, ServiceClient> map : allService.values()) {
            for(ServiceClient client : map.values()){
                ClientResponse response = new ClientResponse();
                response.setName(client.getName());
                response.setUniName(client.getUniName());
                response.setUrl(client.getUrl());
                response.setFailNum(client.getFailNum());
                responses.add(response);
            }
        }

        return responses;
    }

    @Override
    public List<ClientResponse> getLimitedServiceClients() {
        ClientPool pool = ClientPool.getInstance();
        Map<String, Map<String, LimitedServiceClient>> allService = pool.getAllLimitedService();
        List<ClientResponse> responses = new ArrayList<>();
        for (Map<String, LimitedServiceClient> map : allService.values()) {
            for(ServiceClient client : map.values()){
                ClientResponse response = new ClientResponse();
                response.setName(client.getName());
                response.setUniName(client.getUniName());
                response.setUrl(client.getUrl());
                response.setFailNum(client.getFailNum());
                responses.add(response);
            }
        }

        return responses;
    }

    
}
