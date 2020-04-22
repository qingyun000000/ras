package com.zy.zyrasc.gateway;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务名业务
 * @author wuhailong
 */
public class ServiceNameService {
    
    public static Map<String, RealService> map = new HashMap<>();
    
    public static RealService getRealRasName(String serviceName) throws Exception{
        if(map.containsKey(serviceName)){
            //服务配置了访问路径，且访问成功
            return map.get(serviceName);
        }else{
            throw new Exception("访问服务不存在");
        }
    }
}
