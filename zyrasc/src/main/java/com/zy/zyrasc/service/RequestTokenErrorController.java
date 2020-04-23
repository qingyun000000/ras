package com.zy.zyrasc.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Token异常处理
 * @author wuhailong
 */
@RestController
public class RequestTokenErrorController {
    
    @RequestMapping("/requestTokenError")
    public String limitedServiceError(Long eid){
        return "requestToken错误";
    }
}
