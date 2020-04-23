package com.zy.zyrasc.rpc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 接口代理配置
 * @author wuhailong
 */
@Component
public class InterfaceProxyConfigure implements BeanDefinitionRegistryPostProcessor{
    
    @Value("${zy.zyras.ras.client.base:emp}")
    private String basePackge;
    
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("基础包：" + basePackge);
        InterfaceScanner scanner = new InterfaceScanner(registry);
        scanner.scan("*");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory clbf) throws BeansException {
        
    }
    
}
