package com.zy.zyrasc.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Token异常处理
 * @author wuhailong
 */
@RestController
public class LimitedServiceErrorController {
    
    @RequestMapping("/limitedServiceError")
    public String limitedServiceError(Long eid){
        return "服务端没有提供可访问的资源";
    }
}
