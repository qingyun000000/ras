package com.zy.zyrasc.initialization;

import com.zy.zyrasc.server.RegistService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 注册服务
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
 */
@Component
public class InitialService implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${zy.zyras.ras.url:emp}")
    private String rasUrl;

    @Value("${zy.zyras.ras.client.type:serviceAndCustomer}")
    private String type;

    @Value("${zy.zyras.ras.client.serviceType:all}")
    private String serviceType;

    @Value("${zy.zyras.ras.client.name:notNamed}")
    private String name;

    @Value("${zy.zyras.ras.client.uniName:notNamed}")
    private String uniName;

    @Value("${zy.zyras.ras.client.interList:emp}")
    private String interList;

    

    @Override
    public void onApplicationEvent(ContextRefreshedEvent e) {
        try {
            //校验
            verificate();
            
            //注册
            RegistService.regist(type, name, uniName, serviceType, interList, rasUrl);
            
            //从注册中心获取配置
            
        } catch (Exception ex) {
            
        }
    }

    private void verificate() throws Exception {
        System.out.println("校验配置信息开始……");
        if (rasUrl == null || "".equals(rasUrl)) {
            System.out.println("错误配置：rasUrl不能为空");
            throw new Exception("rasUrl不能为空");
        }
        if (type == null || "".equals(type)) {
            System.out.println("错误配置：type不能为空");
            throw new Exception("type不能为空");
        }
        if (!"serviceAndCustomer".equals(type) && !"service".equals(type) && !"customer".equals(type) && "gateway".equals(type)) {
            System.out.println("错误配置：type值错误[只能为serviceAndCustomer(默认值)/service/customer/gateway]");
            throw new Exception("type值错误[只能为serviceAndCustomer(默认值)/service/customer/gateway]");
        }
        if (name == null || "".equals(name)) {
            System.out.println("错误配置：name不能为空");
            throw new Exception("name不能为空");
        }
        if (serviceType != null && !("all".equals(serviceType) || "limited".equals(serviceType))) {
            System.out.println("错误配置：service值错误[只能为all(默认值）/limited]");
            throw new Exception("service值错误[只能为all(默认值）/limited]");
        }
        if ("limited".equals(serviceType)) {
            if (interList == null || "".equals(interList)) {
                System.out.println("错误配置：serviceType=limited时，interList不能为空");
                throw new Exception("serviceType=limited时，interList不能为空");
            }
        }
        System.out.println("校验配置信息结束！");
    }
    

}
