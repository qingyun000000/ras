package com.zy.zyrasc.fuse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 降级服务
 *
 * @author wuhailong
 */
public class SecondaryService {

    /**
     * 降级方法
     * @param <T>
     * @param method
     * @param o
     * @param os
     * @param interfaceClass
     * @return
     * @throws Exception 
     */
    public <T> Object secondaryService(Method method, Object o, Object[] os, Class<T> interfaceClass) throws Exception {
        try{
            Class<?> clazz = Class.forName(interfaceClass.getName()+ "Impl");
            Method method1 = clazz.getMethod(method.getName(), method.getParameterTypes());
            Object response = method1.invoke(clazz.newInstance(), os);
            return response;
        }catch(ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InstantiationException | NoSuchMethodException | SecurityException | InvocationTargetException ex){
            return "进入降级模式，但没有配置降级方法";
        }
        
    }

}
