package com.zy.zyrasc.fuse;

import com.zy.zyrasc.client.Clients;
import com.zy.zyrasc.client.ServicePool;
import com.zy.zyrasc.vo.ServiceClient;
import java.util.List;

/**
 * 熔断器
 * @author wuhailong
 */
public class FuseService {
    
    /**
     * 熔断状态
     * @param ras
     * @param serviceName
     * @param url
     * @return 
     */
    public static boolean fuse(String ras, String serviceName, String url){
        
        ServicePool pool = Clients.getServicePoolMap().get(ras);
        if(pool == null){
            return false;
        }
        
        //新服务不熔断（服务的全部客户端掉线完后，视为新服务）
        if(!pool.containsService(serviceName)){
            return false;
        }
        List<ServiceClient> service = pool.getServiceForName(serviceName, url);
        if(service.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 服务失败时调用，修改熔断状态
     * @param serviceName 
     */
    public static void serviceFail(String serviceName){
        
    }
    
}
