package com.zy.zyrasc.filter;

import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Filter
 * @author wuhailong
 */
@Configuration  //配置文件注解
public class WebConfiguration {
    
    @Autowired
    private ZyrasFilter filter;

    /**
     * 这个过滤器可以将用户代理IP转换为用户真实IP
     * @return
     */
    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }

    /**
     * 自定义过滤器，配置zyrasc
     * @return
     */
    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);   //添加过滤器
        registration.addUrlPatterns("/*");  //设置过滤路径
        registration.setName("ZyrasFilter");
        registration.setOrder(1);//设置优先级
        return registration;
    }
}
