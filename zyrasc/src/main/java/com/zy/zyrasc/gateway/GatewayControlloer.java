package com.zy.zyrasc.gateway;

import com.alibaba.fastjson.JSON;
import com.zy.zyrasc.fuse.FuseService;
import com.zy.zyrasc.request.ServiceRequestService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.http.HttpEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 网关功能
 * @author wuhailong
 */
@RestController
public class GatewayControlloer {

    @RequestMapping("/gateway")
    public Object getewayRequest(HttpServletRequest request) {
        String serviceName = (String) request.getAttribute("zyRasServiceName");
        String url = (String) request.getAttribute("zyRasUrl");
        System.out.println(serviceName);
        System.out.println(url);
        RequestMethod requestMethod = RequestMethod.POST;
        if ("GET".equals(request.getMethod()) || "get".equals(request.getMethod())) {
            requestMethod = RequestMethod.GET;
        }

        try {
            //获取ras和真实服务
            RealService service = ServiceNameService.getRealRasName(serviceName);
            Map<String, Object> params = new HashMap<>();
            Map<String, String[]> parameterMap = request.getParameterMap();
            for(String str : parameterMap.keySet()){
                params.put(str, request.getParameter(str));
            }
            System.out.println(JSON.toJSON(parameterMap));
            Object response;

            //熔断判断
            boolean fuse = FuseService.fuse(service.getRas(), service.getService(), url);
            if (fuse) {
                //已熔断,降级方法执行
                return "方法已经熔断";
            } else {
                try {
                    response = ServiceRequestService.request(Object.class, service.getRas(), service.getService(), url, requestMethod, params);
                    return response;
                } catch (Exception ex) {
                    return ex.getMessage();
                }
            }
        } catch (Exception ex) {
            return ex.getMessage();
        }

    }

}
