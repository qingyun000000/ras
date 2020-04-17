package com.zy.zyrasc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解：远程服务
 * @author wuhailong
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface RemoteService {
    
    /**
     * 没有用
     * @return 
     */
    String value() default "remote";
    
    /**
     * 远程服务名
     * @return
     */
    String serviceName();
}
