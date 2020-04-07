package com.zy.zyrasc.server;

import com.zy.zyrasc.status.ClientStatus;
import com.zy.zyrasc.vo.HeartbeatResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 心跳服务
 * @author wuhailong
 * @createTime 2020-03-30
 * @updateTime 2020-03-30
 */
@Controller
public class HeartbeatController {
    
    @RequestMapping("/heartbeat")
    public HeartbeatResponse heartbeat(){
        HeartbeatResponse response = new HeartbeatResponse();
        response.setSuccess(true);
        response.setToken(ClientStatus.getToken());
        return response;
    }
}
