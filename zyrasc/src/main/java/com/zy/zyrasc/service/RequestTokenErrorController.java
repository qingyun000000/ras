package com.zy.zyrasc.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Token异常处理
 * @author wuhailong
 * @createTime 2019-8-7
 * @updateTime 2019-8-7
 */
@RestController
public class RequestTokenErrorController {
    
    @RequestMapping("/requestTokenError")
    public String limitedServiceError(Long eid){
        return "requestToken错误";
    }
}
