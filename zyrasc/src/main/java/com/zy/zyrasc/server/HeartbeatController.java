package com.zy.zyrasc.server;

import com.zy.zyrasc.status.ClientStatus;
import com.zy.zyrasc.vo.HeartbeatResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 心跳服务
 * @author wuhailong
 * @createTime 2020-03-30
 * @updateTime 2020-03-30
 */
@RestController
public class HeartbeatController {
    
    @PostMapping("/zyras/heartbeat")
    public HeartbeatResponse heartbeat(){
        HeartbeatResponse response = new HeartbeatResponse();
        response.setSuccess(true);
        response.setToken(ClientStatus.getToken());
        return response;
    }
}
