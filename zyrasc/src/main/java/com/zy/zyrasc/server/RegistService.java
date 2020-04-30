package com.zy.zyrasc.server;

import com.alibaba.fastjson.JSON;
import com.zy.zyrasc.balance.SimpleLoadBalanceMethod;
import com.zy.zyrasc.client.ClientStatus;
import com.zy.zyrasc.client.Clients;
import com.zy.zyrasc.enums.ClientType;
import com.zy.zyrasc.enums.ServiceType;
import com.zy.zyrasc.utils.HttpUtil;
import com.zy.zyrasc.vo.RegistResponse;
import com.zy.zyrasc.vo.ServiceResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 服务注册
 * @author wuhailong
 */
public class RegistService {

    public static void regist(int port, ClientType type, List<ClientStatus> clientStatuses) throws Exception {
        for(ClientStatus status : clientStatuses){
            regist(port, type, status.getName(), status.getUniName(), status.getServiceType(), status.getInterList(), status.getRasUrl());
        }
    }
    
    public static void regist(int port, ClientType type, String name, String uniName, ServiceType serviceType, Set<String> interList, String rasUrl) throws Exception {
        String result = null;

        Map<String, String> params = new HashMap<>();
        params.put("port", port+"");
        params.put("clientType", type.name());
        params.put("name", name);
        if (uniName != null && !"".equals(uniName.trim())) {
            params.put("uniName", uniName);
        }
        if (type == ClientType.SERVICE) {
            params.put("serviceType", serviceType.name());
            if ("limited".equals(serviceType)) {
                String interListString = "";
                for(String str : interList){
                    interListString = interListString + "," + str;
                }
                params.put("interList", interListString.substring(1));
            }
            result = HttpUtil.doPost(rasUrl + "/client/regist/serviceRegist", params);
        } else if (type == ClientType.CUSTOMER) {
            try{
                result = HttpUtil.doPost(rasUrl + "/client/regist/customerRegist", params);
            }catch(Exception ex){
                System.out.println("暂时无法连接");
            }
        } else if (type == ClientType.SERVICE_AND_CUSTOMER) {
            params.put("serviceType", serviceType.name());
            if(serviceType == ServiceType.LIMITED){
                String interListString = "";
                for(String str : interList){
                    interListString = interListString + "," + str;
                }
                params.put("interList", interListString.substring(1));
            }
            result = HttpUtil.doPost(rasUrl + "/client/regist/serviceAndCustomerRegist", params);
        } else if (type == ClientType.GATEWAY) {
            result = HttpUtil.doPost(rasUrl + "/client/regist/gatewayRegist", params);
        } else {
            params.put("serviceType", serviceType.name());
            if (serviceType == ServiceType.LIMITED) {
                String interListString = "";
                for(String str : interList){
                    interListString = interListString + "," + str;
                }
                params.put("interList", interListString.substring(1));
            }
            result = HttpUtil.doPost(rasUrl + "/client/regist/regist", params);
        }

        //解析报文
        System.out.println("注册返回报文：" + result);
        
        ServiceResult result1 = JSON.parseObject(result, ServiceResult.class);

        if(result1.isSuccess()){
            System.out.println("注册成功！");
            RegistResponse registResponse = JSON.parseObject(result1.getData().toString(), RegistResponse.class);
            ClientStatus status = new ClientStatus();
            status.setRasUrl(rasUrl);
            status.setToken(registResponse.getToken());
            status.setBalanceMethod(new SimpleLoadBalanceMethod());             //负载均衡策略
            Clients.getClientStatusMap().put(registResponse.getRas(), status);
        }else{
            System.out.println("注册失败：" + result1.getMessage());
            throw new Exception("注册失败");
        }
        
        
    }
    
}
