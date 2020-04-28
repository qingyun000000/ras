package com.zy.zyrasc.gateway;

import com.alibaba.fastjson.JSON;
import com.zy.zyrasc.client.GatewayMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务名业务
 * @author wuhailong
 */
public class ServiceNameService {
    
    public static Map<String, RealService> map = new HashMap<>();
    
    public static RealService getRealRasName(String url) throws Exception{
        if(map.containsKey(url)){
            //服务配置了访问路径，且访问成功
            return map.get(url);
        }else{
            String ras;
            String service;
            try{
                String[] split = url.split("-");
                ras = split[0];
                service = split[1];
            }catch(Exception ex){
                throw new Exception("服务名格式错误");
            }
                
            //服务已经配置路径，无法通过默认url访问
            for(RealService serv : map.values()){
                if(serv.getRas().equals(ras) && serv.getService().equals(service)){
                    throw new Exception("访问服务已配置别名，不能使用默认名访问");
                }
            }

            //没有配置，可以使用默认url访问
            RealService serv2 = new RealService();
            serv2.setRas(ras);
            serv2.setService(service);
            return serv2;
        }
    }

    public static void setRealRasName(String mapString) {
        List<GatewayMap> maps = JSON.parseArray(mapString, GatewayMap.class);
        for(GatewayMap m : maps){
            RealService service = new RealService();
            service.setRas(m.getRas());
            service.setService(m.getService());
            map.put(m.getUrl(), service);
        }
        System.out.println(map);
    }
}
