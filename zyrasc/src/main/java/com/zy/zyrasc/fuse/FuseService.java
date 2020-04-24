package com.zy.zyrasc.fuse;

import com.zy.zyrasc.client.Clients;
import com.zy.zyrasc.client.ServicePool;
import com.zy.zyrasc.enums.FuseState;
import com.zy.zyrasc.vo.ServiceClient;
import java.util.Date;
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
            System.out.println("服务池未建立");
            return false;
        }
        
        //新服务不熔断（服务的全部客户端掉线完后，视为新服务）
        if(!pool.containsService(serviceName)){
            System.out.println(serviceName + "服务为新服务");
            return false;
        }
        List<ServiceClient> service = pool.getNotFusedServiceForName(serviceName, url);
        if(service.isEmpty()){
            return true;
        }else{
            return false;
        }
    }
    
    /**
     * 服务失败时调用，修改熔断状态
     * @param ras
     * @param serviceName
     * @param client
     */
    public static void serviceFail(String ras, String serviceName, ServiceClient client){
        ServicePool pool = Clients.getServicePoolMap().get(ras);
        if(pool == null){
            System.out.println("服务池未建立");
            return;
        }
        ServiceClient serviceClient = pool.getServiceClient(serviceName, client);
        if(serviceClient != null){
            //判断连续熔断次数，如果连续熔断已达到4次，则直接删除
            if(serviceClient.getFuseTimes() == 4){
                pool.deleteServiceClient(serviceName, serviceClient);
            }
            //设置熔断状态和熔断时间，更新熔断次数
            serviceClient.setFused(FuseState.熔断);
            serviceClient.setFuseTime(new Date());
            serviceClient.setFuseTimes(serviceClient.getFuseTimes() + 1);
        }
        
        
    }

    public static void serviceSuccess(String ras, String serviceName, ServiceClient client) {
        ServicePool pool = Clients.getServicePoolMap().get(ras);
        if(pool == null){
            System.out.println("服务池未建立");
            return;
        }
        ServiceClient serviceClient = pool.getServiceClient(serviceName, client);
        if(serviceClient != null){
            //设置熔断状态和熔断时间，更新熔断次数
            serviceClient.setFused(FuseState.正常);
            serviceClient.setFuseTime(new Date());
            serviceClient.setFuseTimes(0);
        }
    }

    /**
     * 熔断状态回复半开
     * @param ras 
     */
    public static void openFused(String ras) {
        ServicePool pool = Clients.getServicePoolMap().get(ras);
        pool.openFused();
    }
    
}
