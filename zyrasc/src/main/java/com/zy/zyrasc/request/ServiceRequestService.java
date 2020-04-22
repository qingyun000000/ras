package com.zy.zyrasc.request;

import com.zy.zyrasc.balance.LoadBalanceService;
import com.alibaba.fastjson.JSON;
import com.zy.zyrasc.client.Clients;
import com.zy.zyrasc.exception.ServiceNotExistException;
import com.zy.zyrasc.utils.HttpUtil;
import com.zy.zyrasc.vo.ServiceClient;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 服务请求(调用服务)
 * @author wuhailong
 */
public class ServiceRequestService {
    
    /**
     * 调用服务方法
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
    public static <T> T request(Class<T> clazz, String ras, String serviceName, String url, RequestMethod method, Map<String, Object> params) throws Exception{
        
        //负载均衡
        ServiceClient client = LoadBalanceService.getServiceClient(ras, serviceName, url);
        
        if(client != null){
            //补充ras, requestToken信息
            params.put("zyras", ras);
            params.put("zyrasRequestToken", Clients.getClientStatusMap().get(ras).getRequestToken1());
            T response = request(method, client, url, params, clazz);
            return response;
        }else{
            throw new ServiceNotExistException(ras + "_" + serviceName + "不存在");
        }
        
    }

    /**
     * 请求Http服务
     * @param <T>
     * @param method
     * @param client
     * @param url
     * @param params
     * @param clazz
     * @return 
     */
    private static <T> T request(RequestMethod method, ServiceClient client, String url, Map<String, Object> params, Class<T> clazz) {
        String result = null;
        if(method == RequestMethod.GET){
            result = HttpUtil.doGet(client.getUrl() + url);
        }else if (method == RequestMethod.GET){
            result = HttpUtil.doPost(client.getUrl() + url, params);
        }
        T t = JSON.parseObject(result, clazz);
        return t;
    }
}
