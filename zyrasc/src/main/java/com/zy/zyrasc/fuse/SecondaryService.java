/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zy.zyrasc.fuse;

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
        Class<?> clazz = Class.forName(interfaceClass.getName()+ "Impl");
        Method method1 = clazz.getMethod(method.getName(), method.getParameterTypes());
        Object response = method1.invoke(clazz.newInstance(), os);
        return response;
    }

}
