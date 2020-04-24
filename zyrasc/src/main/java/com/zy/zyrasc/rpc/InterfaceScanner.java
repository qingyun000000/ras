package com.zy.zyrasc.rpc;

import com.zy.zyrasc.annotation.RemoteService;
import java.util.Set;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

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
        return metadata.isInterface() && metadata.isIndependent();
    }
    
    

    private void addFilter() {
        addIncludeFilter(new AnnotationTypeFilter(RemoteService.class));
    }

    private void createBeanDefinition(Set<BeanDefinitionHolder> beanDefinitionHolders) {
        for(BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders){
            GenericBeanDefinition beanDefinition = (GenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanDefinition.getBeanClassName());
            beanDefinition.setBeanClass(ProxyFactoryBean.class);
            beanDefinition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
        }
    }
    
    
    
}
