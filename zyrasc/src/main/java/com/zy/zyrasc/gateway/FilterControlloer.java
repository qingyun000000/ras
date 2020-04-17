package com.zy.zyrasc.gateway;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zy.zyrasc.fuse.FuseService;
import com.zy.zyrasc.fuse.SecondaryService;
import com.zy.zyrasc.request.ServiceRequestService;
import com.zy.zyrasc.status.ClientStatus;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 网关功能
 *
 * @author wuhailong
 */
@RestController
public class FilterControlloer {

    @RequestMapping("/gateway")
    public Object getewayRequest(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        System.out.println(requestURI);
        String[] split = requestURI.split("/");
        String serviceName = split[0];
        RequestMethod requestMethod = RequestMethod.POST;
        if ("GET".equals(request.getMethod()) || "get".equals(request.getMethod())) {
            requestMethod = RequestMethod.GET;
        }
        Map<String, Object> params = new HashMap<>();

        Object response;

        //熔断判断
        boolean fuse = FuseService.fuse(serviceName);
        if (fuse) {
            //已熔断,降级方法执行
            return "方法已经熔断";
        } else {
            try {
                response = ServiceRequestService.request(Object.class, serviceName, requestURI, requestMethod, params);
                return response;
            } catch (Exception ex) {
                return ex.getMessage();
            }
        }
    }

}
