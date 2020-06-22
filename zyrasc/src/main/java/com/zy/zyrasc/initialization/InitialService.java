package com.zy.zyrasc.initialization;

import com.alibaba.fastjson.JSON;
import com.zy.zyrasc.client.ClientStatus;
import com.zy.zyrasc.client.Clients;
import com.zy.zyrasc.enums.ClientType;
import com.zy.zyrasc.enums.ServiceType;
import com.zy.zyrasc.gateway.ServiceNameService;
import com.zy.zyrasc.server.FindServiceService;
import com.zy.zyrasc.server.RegistService;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 客户端初始化服务
 *
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
 */
@Component
public class InitialService implements ApplicationListener<ContextRefreshedEvent> {
    
    @Value("${server.port:8080}")
    private int port;

    @Value("${zyras.singleton:true}")
    private boolean singleton;

    @Value("${zyras.url:emp}")
    private String rasUrl;

    @Value("${zyras.type:serviceAndCustomer}")
    private String type;

    @Value("${zyras.client.serviceType:all}")
    private String serviceType;

    @Value("${zyras.client.name:notNamed}")
    private String name;

    @Value("${zyras.client.uniName:notNamed}")
    private String uniName;

    @Value("${zyras.client.interList:[]}")
    private String interList;

    @Value("${zyras.linkwords:[]}")
    private String linkwords;
    
    @Value("${zyras.gateway.map:[]}")
    private String map;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent e) {
        try {
            //校验
            List<ClientStatus> clientStatuses = verificate();
            
            //网关处理映射
            if(Clients.getType() == ClientType.GATEWAY){
                ServiceNameService.setRealRasName(map);
            }

            //注册
            RegistService.regist(port, Clients.getType(), clientStatuses);
            
            //发现服务
            if(Clients.getType() != ClientType.SERVICE){
                FindServiceService.getAllService();
            }
            
        } catch (Exception ex) {
            Logger.getLogger(InitialService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private List<ClientStatus> verificate() throws Exception {
        System.out.println("校验配置信息开始……");
        Clients.setSingleton(singleton);
        verificateType();
        List<ClientStatus> clientStatuses = new ArrayList<>();
        if (singleton) {
            //向单注册中心集群注册
            ClientStatus status = new ClientStatus();
            if (rasUrl == null || "".equals(rasUrl)) {
                System.out.println("错误配置：rasUrl不能为空");
                throw new Exception("rasUrl不能为空");
            }else{
                status.setRasUrl(rasUrl);
            }
            if (name == null || "".equals(name)) {
                System.out.println("错误配置：name不能为空");
                throw new Exception("name不能为空");
            }else{
                status.setName(name);
            }
            if(uniName != null && !"".equals(uniName)){
                status.setUniName(uniName);
            }
            if (!"all".equals(serviceType) && !"limited".equals(serviceType)) {
                System.out.println("错误配置：service值错误[只能为all(默认值）/limited]");
                throw new Exception("service值错误[只能为all(默认值）/limited]");
            }else if("all".equals(serviceType)){
                status.setServiceType(ServiceType.ALL);
            }else{
                status.setServiceType(ServiceType.LIMITED);
            }
            if ("limited".equals(serviceType)) {
                if (interList == null || "".equals(interList)) {
                    System.out.println("错误配置：serviceType=limited时，interList不能为空");
                    throw new Exception("serviceType=limited时，interList不能为空");
                }
            }else{
                
            }
            clientStatuses.add(status);
        }else{
            //向多注册中心集群注册
            clientStatuses = JSON.parseArray(linkwords, ClientStatus.class);
        }

        System.out.println("校验配置信息结束！");

        return clientStatuses;
    }

    private void verificateType() throws Exception {
        if (type == null || "".equals(type)) {
            System.out.println("错误配置：type不能为空");
            throw new Exception("type不能为空");
        }
        if ("serviceAndCustomer".equals(type)) {
            Clients.setType(ClientType.SERVICE_AND_CUSTOMER);
        } else if ("service".equals(type)) {
            Clients.setType(ClientType.SERVICE);
        } else if ("customer".equals(type)) {
            Clients.setType(ClientType.CUSTOMER);
        } else if ("gateway".equals(type)) {
            Clients.setType(ClientType.GATEWAY);
        } else {
            System.out.println("错误配置：type值错误[只能为serviceAndCustomer(默认值)/service/customer/gateway]");
            throw new Exception("type值错误[只能为serviceAndCustomer(默认值)/service/customer/gateway]");
        }
    }

}
