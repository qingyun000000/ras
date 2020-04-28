package com.zy.zyrasc.gateway;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Token异常处理
 * @author wuhailong
 */
@RestController
public class ServiceNotExistController {
    
    @RequestMapping("/serviceNotExist")
    public String limitedServiceError(Long eid){
        return "服务不存在";
    }
}
