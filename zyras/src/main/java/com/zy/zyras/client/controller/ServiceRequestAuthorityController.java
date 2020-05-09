package com.zy.zyras.client.controller;

import cn.whl.commonutils.exception.NullException;
import cn.whl.commonutils.service.result.ResultParam;
import cn.whl.commonutils.service.result.ServiceResult;
import cn.whl.commonutils.service.result.ServiceResultTool;
import cn.whl.commonutils.verificate.VerificateTool;
import com.zy.zyras.client.domain.vo.RequestTokenRequest;
import com.zy.zyras.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
* 注册中心定时更新服务调用token
* 服务调用方需要定时从注册中心获取服务调用token
* 在调用服务时需要传递服务调用token
* 服务提供方拦截调用，进行权限校验（1，时校校验， 2，身份识别，开放给注册中心的接口权限校验）
* 服务提供方可定时获取服务调用token（高安全模式），也可解码获得时间和注册中心名来校验（低安全模式） 
* 服务提供方应当在时校上适当延迟（高安全模式需要保存至少前一个token），为网络传输提供时间窗口
*/

/**
 * 服务调用权限Controller
 * @author wuhailong
 */
@RestController
@RequestMapping("/authority/serviceRequest")
public class ServiceRequestAuthorityController {
    
    /*
     * 客户端业务组件
     */
    @Autowired
    private ClientService clientService; 
    
    /**
     * 服务调用token
     * @param request
     * @return 
     */
    @PostMapping("/token")
    public ServiceResult serviceRequestToken(RequestTokenRequest request){
        ServiceResult result = ServiceResultTool.action(ResultParam.Data, ()->{
            if(VerificateTool.isEmpty(request.getToken())){
                throw new NullException("输入token");
            }
        }, ()->clientService.getServiceRequestToken(request));
        return result;
    }
    
}
