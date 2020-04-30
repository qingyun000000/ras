package com.zy.zyrasc.request;

import com.zy.zyrasc.balance.LoadBalanceService;
import com.alibaba.fastjson.JSON;
import com.zy.zyrasc.client.Clients;
import com.zy.zyrasc.enums.ClientType;
import com.zy.zyrasc.enums.FuseState;
import com.zy.zyrasc.exception.ServiceNotExistException;
import com.zy.zyrasc.fuse.FuseService;
import com.zy.zyrasc.utils.HttpUtil;
import com.zy.zyrasc.vo.ServiceClient;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 服务请求(调用服务)
 *
 * @author wuhailong
 */
public class ServiceRequestService {

    /**
     * 调用服务方法
     *
     * @param <T>
     * @param clazz
     * @param ras
     * @param serviceName
     * @param url
     * @param method
     * @param params
     * @return
     * @throws java.lang.Exception
     */
    public static <T> T request(Class<T> clazz, String ras, String serviceName, String url, RequestMethod method, Map<String, Object> params) throws Exception {

        //负载均衡
        ServiceClient client = LoadBalanceService.getServiceClient(ras, serviceName, url);

        if (client != null) {
            //补充ras, requestToken信息
            params.put("zyras", ras);
            params.put("zyrasRequestToken", Clients.getClientStatusMap().get(ras).getRequestToken1());
            System.out.println(Clients.getClientStatusMap().get(ras).getRequestToken1());
            T response = null;
            try {
                System.out.println("访问：" + client.getUniName() + "服务" + url);
                response = request(method, client, url, params, clazz);
                System.out.println("访问：" + client.getUniName() + ",服务" + url + ",结果："  + response.toString());
                if(client.getFused() == FuseState.HALF_FUSED){
                    FuseService.serviceSuccess(ras, serviceName, client);
                }
            } catch(Exception ex) {
                //修改熔断状态
                FuseService.serviceFail(ras, serviceName, client);
                
                //重新负载均衡一次，这次只取正常客户端
                client = LoadBalanceService.getServiceNormalClient(ras, serviceName, url);
                if (client != null) {
                    try {
                        response = request(method, client, url, params, clazz);
                    } catch(Exception ex2) {
                        //修改熔断状态
                        FuseService.serviceFail(ras, serviceName, client);
                    }
                }
            }
            
            
            return response;
        } else {
            throw new ServiceNotExistException(ras + "_" + serviceName + "不存在");
        }

    }

    /**
     * 请求Http服务
     *
     * @param <T>
     * @param method
     * @param client
     * @param url
     * @param params
     * @param clazz
     * @return
     */
    private static <T> T request(RequestMethod method, ServiceClient client, String url, Map<String, Object> params, Class<T> clazz) {
        try{
            String result = null;
            if (method == RequestMethod.GET) {
                if(!params.isEmpty()){
                    url = url + "?";
                    for(String str : params.keySet()){
                        System.out.println(JSON.toJSON(params.get(str)));
                        url = url + addGetParams(str, params.get(str), params.get(str).getClass());
                    }
                }
                System.out.println(client.getUrl() + url.substring(0, url.length() - 1));
                result = HttpUtil.doGet(client.getUrl() + url.substring(0, url.length() - 1));
            } else {
                Map<String, Object> params1 = new HashMap();
                if(!params.isEmpty()){
                    url = url + "?";
                    for(String str : params.keySet()){
                        System.out.println(JSON.toJSON(params.get(str)));
                        params1.putAll(addPostParams(str, params.get(str), params.get(str).getClass()));
                    }
                }
                result = HttpUtil.doPost(client.getUrl() + url, params1);
            }
            System.out.println(result);
            T t = JSON.parseObject(result, clazz);
            return t;
        }catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
    
    /**
     * 递归组装参数
     * @param key
     * @param param
     * @param clazz
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException 
     */
    private static String addGetParams(String key, Object param, Class<?> clazz) throws IllegalArgumentException, IllegalAccessException{
        //基础类型或者字符串，直接组装
        //也是递归终止条件
        if(clazz.isPrimitive() || clazz == String.class){
            return key + "=" + param + "&";
        }
        String url = "";
        System.out.println(clazz.getSimpleName());
        //非基础类型和字符串，获取本类属性，递归处理
        Field[] fields = clazz.getDeclaredFields();
        System.out.println(fields.length);
        for(Field field : fields){
            boolean isAcc = field.isAccessible();
            if(!field.isAccessible()){
                field.setAccessible(true);
            }
            System.out.println(field.getName());
            
            //对每个属性进行递归处理
            url = url + addGetParams(field.getName(), field.get(param), field.getType());
            
            field.setAccessible(isAcc);
        }
        //获取父类属性，递归处理。父类已经是非基础类型，这里key值并没有用。
        if(clazz.getSuperclass() != null){
            url = url + addGetParams(key, param, clazz.getSuperclass());
        }
        return url;
    }
    
    /**
     * 递归组装参数
     * @param key
     * @param param
     * @param clazz
     * @return
     * @throws IllegalArgumentException
     * @throws IllegalAccessException 
     */
    private static Map<String, String> addPostParams(String key, Object param, Class<?> clazz) throws IllegalArgumentException, IllegalAccessException{
        Map<String, String> params = new HashMap<>();
        //基础类型或者字符串，直接组装
        //也是递归终止条件
        if(clazz.isPrimitive() || clazz == String.class){
            params.put(key, String.valueOf(param));
        }
        System.out.println(clazz.getSimpleName());
        //非基础类型和字符串，获取本类属性，递归处理
        Field[] fields = clazz.getDeclaredFields();
        System.out.println(fields.length);
        for(Field field : fields){
            boolean isAcc = field.isAccessible();
            if(!field.isAccessible()){
                field.setAccessible(true);
            }
            System.out.println(field.getName());
            
            //对每个属性进行递归处理
            params.putAll(addPostParams(field.getName(), field.get(param), field.getType()));
            
            field.setAccessible(isAcc);
        }
        //获取父类属性，递归处理。父类已经是非基础类型，这里key值并没有用。
        if(clazz.getSuperclass() != null){
            params.putAll(addPostParams(key, param, clazz.getSuperclass()));
        }
        return params;
    }
}
