/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zy.zyrasc.fuse;

import java.lang.reflect.Method;
import java.util.ServiceLoader;

/**
 * 降级服务
 *
 * @author wuhailong
 */
public class SecondaryService {

    public static <T> Object secondaryService(Method method, Object o, Object[] os, Class<T> interfaceClass) throws Exception {

        ServiceLoader<T> load = ServiceLoader.load(interfaceClass);
        int count = 0;
        for (T t : load) {
            count++;
        }
        if (count < 1) {
            //没有配置降级类，返回错误信息
            throw new Exception("请求服务失败，降级实现类不存在，无法进行降级处理");
        } else if (count > 1) {
            //没有配置降级类，返回错误信息
            throw new Exception("请求服务失败，降级实现类冲突（存在多个），无法进行降级处理");
        } else {
            //执行第一个实现类方法（本质时只有一个）
            T t = load.iterator().next();
            System.out.println(t.getClass().getSimpleName());
            Method method1 = t.getClass().getMethod(method.getName(), method.getParameterTypes());
            Object response = method1.invoke(o, os);
            return response;
        }

    }
}
