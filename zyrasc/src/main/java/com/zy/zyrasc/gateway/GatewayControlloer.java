package com.zy.zyrasc.gateway;

import com.zy.zyrasc.fuse.FuseService;
import com.zy.zyrasc.request.ServiceRequestService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
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
        RequestMethod requestMethod = RequestMethod.POST;
        if ("GET".equals(request.getMethod()) || "get".equals(request.getMethod())) {
            requestMethod = RequestMethod.GET;
        }

        try {
            //获取ras和真实服务
            RealService service = ServiceNameService.getRealRasName(serviceName);
            Map<String, Object> params = new HashMap<>();

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
