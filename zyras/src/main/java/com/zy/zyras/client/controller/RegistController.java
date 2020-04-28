package com.zy.zyras.client.controller;

import cn.whl.commonutils.exception.InputWrongException;
import cn.whl.commonutils.log.LoggerTools;
import cn.whl.commonutils.service.result.ResultParam;
import cn.whl.commonutils.service.result.ServiceResult;
import cn.whl.commonutils.service.result.ServiceResultTool;
import cn.whl.commonutils.verificate.VerificateTool;
import com.zy.zyras.client.domain.vo.RegistRequest;
import com.zy.zyras.client.service.ClientService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务注册Controller
 * @author wuhailong
 */
@RestController
@RequestMapping("/client/regist")
public class RegistController {
    
    /*
     * 客户端业务组件
     */
    @Autowired
    private ClientService clientService; 
    
    /**
     * 统一注册接口
     * @param request
     * @param req
     * @return 
     */
    @PostMapping("/regist")
    public ServiceResult regist(RegistRequest request, HttpServletRequest req) {
        ServiceResult result = ServiceResultTool.action(ResultParam.Data, ()->{
            String clientType = request.getClientType();
            if(VerificateTool.isEmpty(clientType)){
                throw new NullPointerException("输入客户端类型");
            }
            if(!"serviceAndCustomer".equals(clientType) && !"service".equals(clientType) && !"customer".equals(clientType) && "gateway".equals(clientType)){
                throw new InputWrongException("输入客户端类型");
            }
            if("service".equals(clientType)){
                if(VerificateTool.isEmpty(request.getServiceType())){
                    throw new NullPointerException("输入服务类型");
                }
                String serviceType = request.getServiceType();
                if(!"all".equals(serviceType) && !"limited".equals(serviceType)){
                    throw new InputWrongException("输入服务类型");
                }
            }
            if(VerificateTool.isEmpty(request.getName())){
                throw new NullPointerException("输入客户端名");
            }
            LoggerTools.log4j_write.info("客户端URL：" + req.getRemoteAddr() + ":" + request.getPort());
            request.setUrl("http://" + req.getRemoteAddr() + ":" + request.getPort());
        }, ()->clientService.regist(request));
        return result;
    }
    
    /**
     * 服务提供方注册接口
     * @param request
     * @return 
     */
    @PostMapping("/serviceRegist")
    public ServiceResult serviceRegist(RegistRequest request, HttpServletRequest req) {
        ServiceResult result = ServiceResultTool.action(ResultParam.Data, ()->{
            String clientType = request.getClientType();
            if(VerificateTool.notEmpty(clientType) && (!"service".equals(clientType) && !"limitedService".equals(clientType))){
                throw new InputWrongException("输入客户端类型");
            }
            if(VerificateTool.isEmpty(request.getServiceType())){
                throw new NullPointerException("输入服务类型");
            }
            String serviceType = request.getServiceType();
            if(!"all".equals(serviceType) && !"limited".equals(serviceType)){
                throw new InputWrongException("输入服务类型");
            }
            if(VerificateTool.isEmpty(request.getName())){
                throw new NullPointerException("输入客户端名");
            }
            LoggerTools.log4j_write.info("客户端URL：" + req.getRemoteAddr() + ":" + request.getPort());
            request.setUrl("http://" + req.getRemoteAddr() + ":" + request.getPort());
        }, ()->clientService.serviceRegist(request));
        return result;
    }
    
    /**
     * 服务调用方注册接口
     * @param request
     * @param req
     * @return 
     */
    @PostMapping("/customerRegist")
    public ServiceResult customerRegist(RegistRequest request, HttpServletRequest req) {
        ServiceResult result = ServiceResultTool.action(ResultParam.Data, ()->{
            String clientType = request.getClientType();
            if(VerificateTool.notEmpty(clientType) && !"customer".equals(clientType)){
                throw new InputWrongException("输入客户端类型");
            }
            if(VerificateTool.isEmpty(request.getName())){
                throw new NullPointerException("输入客户端名");
            }
            LoggerTools.log4j_write.info("客户端" + request.getName() + "调用接入 ");
            LoggerTools.log4j_write.info("客户端URL：" + req.getRemoteAddr() + ":" + request.getPort());
            request.setUrl("http://" + req.getRemoteAddr() + ":" + request.getPort());
        }, ()->clientService.customerRegist(request));
        return result;
    }
    
    /**
     * 服务提供和调用方注册接口
     * @param request
     * @return 
     */
    @PostMapping("/serviceAndCustomerRegist")
    public ServiceResult serviceAndCunstomerRegist(RegistRequest request, HttpServletRequest req) {
        ServiceResult result = ServiceResultTool.action(ResultParam.Data, ()->{
            String clientType = request.getClientType();
            if(VerificateTool.notEmpty(clientType) && !"serviceAndCustomer".equals(clientType)){
                throw new InputWrongException("输入客户端类型");
            }
            if(VerificateTool.isEmpty(request.getServiceType())){
                throw new NullPointerException("输入服务类型");
            }
            String serviceType = request.getServiceType();
            if(!"all".equals(serviceType) && !"limited".equals(serviceType)){
                throw new InputWrongException("输入服务类型");
            }
            if(VerificateTool.isEmpty(request.getName())){
                throw new NullPointerException("输入客户端名");
            }
            LoggerTools.log4j_write.info("客户端URL：" + req.getRemoteAddr() + ":" + request.getPort());
            request.setUrl("http://" + req.getRemoteAddr() + ":" + request.getPort());
        }, ()->clientService.serviceAndCunstomerRegist(request));
        return result;
    }
    
    /**
     * 网关注册接口
     * @param request
     * @return 
     */
    @PostMapping("/gatewayRegist")
    public ServiceResult gatewayRegist(RegistRequest request, HttpServletRequest req) {
        ServiceResult result = ServiceResultTool.action(ResultParam.Data, ()->{
            String clientType = request.getClientType();
            if(VerificateTool.notEmpty(clientType) && !"gateway".equals(clientType)){
                throw new InputWrongException("输入客户端类型");
            }
            if(VerificateTool.isEmpty(request.getName())){
                throw new NullPointerException("输入客户端名");
            }
            LoggerTools.log4j_write.info("客户端URL：" + req.getRemoteAddr() + ":" + request.getPort());
            request.setUrl("http://" + req.getRemoteAddr() + ":" + request.getPort());
        }, ()->clientService.gatewayRegist(request));
        return result;
    }
}
