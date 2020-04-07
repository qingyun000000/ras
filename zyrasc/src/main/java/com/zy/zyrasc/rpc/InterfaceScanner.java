/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zy.zyrasc.rpc;

import com.zy.zyrasc.annotation.RemoteService;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

/**
 * 接口扫描器
 * @author wuhailong
 */
public class InterfaceScanner extends ClassPathBeanDefinitionScanner{
    
    public InterfaceScanner(BeanDefinitionRegistry registry) {
        //false，不使用默认typeFilter
        super(registry, false);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        addFilter();
        System.out.println("===========扫描" + Arrays.toString(basePackages) + "==================");
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        if(beanDefinitionHolders.isEmpty()){
            System.out.println("没扫描到");
        }else{
            createBeanDefinition(beanDefinitionHolders);
        }
        
        return beanDefinitionHolders;
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        AnnotationMetadata metadata = beanDefinition.getMetadata();
        System.out.println("扫描接口");
        return metadata.isInterface() && metadata.isIndependent();
    }
    
    

    private void addFilter() {
        addIncludeFilter(new TypeFilter() {
            @Override
            public boolean match(MetadataReader reader, MetadataReaderFactory mrf) throws IOException {
                System.out.println("===========类型过滤器==================");
                return true;
            }
        });
    }

    private void createBeanDefinition(Set<BeanDefinitionHolder> beanDefinitionHolders) {
        for(BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders){
            System.out.println(beanDefinitionHolder.getBeanDefinition().getBeanClassName());
            GenericBeanDefinition beanDefinition = (GenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanDefinition.getBeanClassName());
            beanDefinition.setBeanClass(ProxyFactoryBean.class);
            beanDefinition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
        }
    }
    
    
    
}
