package com.zy.zyrasc.server;

import com.zy.zyrasc.client.ClientStatus;
import com.zy.zyrasc.client.Clients;
import com.zy.zyrasc.vo.HeartbeatResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 心跳服务
 * @author wuhailong
 */
@RestController
public class HeartbeatController {
    
    @PostMapping("/zyras/heartbeat")
    public HeartbeatResponse heartbeat(String ras){
        System.out.println("ras=" + ras);
        HeartbeatResponse response = new HeartbeatResponse();
        response.setSuccess(true);
        response.setToken(Clients.getClientStatusMap().get(ras).getToken());
        return response;
    }
}
