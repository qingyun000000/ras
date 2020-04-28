package com.zy.zyras.client.controller;

import cn.whl.commonutils.log.LoggerTools;
import cn.whl.commonutils.service.result.ResultParam;
import cn.whl.commonutils.service.result.ServiceResult;
import cn.whl.commonutils.service.result.ServiceResultTool;
import cn.whl.commonutils.verificate.VerificateTool;
import com.zy.zyras.client.domain.vo.FindServiceRequest;
import com.zy.zyras.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务发现Controller
 * @author wuhailong
 */
@RestController
@RequestMapping("/client/find")
public class FindController {
    
    /*
     * 客户端业务组件
     */
    @Autowired
    private ClientService clientService; 
    
    /**
     * 服务发现接口
     * @param findServiceRequest
     * @return 
     */
    @PostMapping("/service")
    public ServiceResult findService(FindServiceRequest findServiceRequest){
        ServiceResult result = ServiceResultTool.action(ResultParam.Data, ()->{
            if(VerificateTool.isEmpty(findServiceRequest.getToken())){
                throw new NullPointerException("输入token");
            }
            if(VerificateTool.isEmpty(findServiceRequest.getName())){
                throw new NullPointerException("输入服务名");
            }
        }, ()->clientService.findService(findServiceRequest));
        return result;
    }
    
    /**
     * 全部服务发现接口
     * @param findServiceRequest
     * @return 
     */
    @PostMapping("/allService")
    public ServiceResult findAllService(FindServiceRequest findServiceRequest){
        LoggerTools.log4j_write.info("同步服务列表开始");
        ServiceResult result = ServiceResultTool.action(ResultParam.Data, ()->{
            if(VerificateTool.isEmpty(findServiceRequest.getToken())){
                throw new NullPointerException("输入token");
            }
        }, ()->clientService.findAllService(findServiceRequest.getToken()));
        LoggerTools.log4j_write.info("同步服务列表结束");
        return result;
    }
    
}
