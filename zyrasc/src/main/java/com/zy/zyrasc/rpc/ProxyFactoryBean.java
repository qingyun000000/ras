package com.zy.zyrasc.rpc;

import com.zy.zyrasc.annotation.RemoteService;
import com.zy.zyrasc.fuse.FuseService;
import com.zy.zyrasc.fuse.SecondaryService;
import com.zy.zyrasc.request.ServiceRequestService;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.cglib.proxy.InvocationHandler;
import org.springframework.cglib.proxy.Proxy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 代理工厂Bean
 * @author wuhailong
 */
public class ProxyFactoryBean<T> implements FactoryBean{
    
    private final Class<T> interfaceClass;

    public ProxyFactoryBean(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    @Override
    public Object getObject() throws Exception {
        return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, (Object o, Method method, Object[] os) -> {
            //获取
            RemoteService rs = interfaceClass.getAnnotation(RemoteService.class);
            System.out.println("注册中心：" + rs.ras());
            System.out.println("服务名：" + rs.service());
            RequestMethod requestMethod = RequestMethod.GET;
            String url = "";
            RequestMapping annotation = method.getAnnotation(RequestMapping.class);
            if(annotation != null){
                System.out.println("服务url:" + annotation.value()[0] + ", 请求类型" + annotation.method()[0]);
                url = annotation.value()[0];
                requestMethod = annotation.method()[0];
            }else{
                GetMapping annotation1 = method.getAnnotation(GetMapping.class);
                if(annotation1 != null){
                    System.out.println("服务url:" + annotation1.value()[0] + ", 请求类型Get" );
                    url = annotation1.value()[0];
                }else{
                    PostMapping annotation2 = method.getAnnotation(PostMapping.class);
                    if(annotation2 != null){
                        System.out.println("服务url:" + annotation2.value()[0] + ", 请求类型Post" );
                        url = annotation2.value()[0];
                        requestMethod = RequestMethod.POST;
                    }else{
                        throw new Exception("远程方法注解配置错误");
                    }
                }
            }
            
            Object response;
            
            //熔断判断
            boolean fuse = FuseService.fuse(rs.ras(), rs.service(), url);
            if(fuse){
                //已熔断,降级方法执行
                response = new SecondaryService().secondaryService(method, o, os,interfaceClass);
            }else{
                //封装请求参数
                Map<String, Object> params = new HashMap<>();
                Parameter[] parameters = method.getParameters();
                for(int i = 0; i < parameters.length; i++){
                    Parameter parameter = parameters[i];
                    Object object  = os[i];
                    params.put(parameter.getName(), object);
                }
                
                //调用服务（包括负载均衡），转request模块处理
                Class<?> returnType = method.getReturnType();
                try{
                    System.out.println("调用远程服务开始");
                    response = ServiceRequestService.request(returnType, rs.ras(), rs.service(), url, requestMethod, params);
                    System.out.println("调用远程服务结束");
                }catch(Exception ex){
                    ex.printStackTrace();
                    //服务失败，降级方法执行
                    response = new SecondaryService().secondaryService(method, o, os,interfaceClass);
                }
            }
            return response;
        });
    }

    @Override
    public Class getObjectType() {
        return interfaceClass;
    }
    
}
