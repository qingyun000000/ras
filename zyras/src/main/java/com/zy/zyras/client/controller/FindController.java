package com.zy.zyras.client.controller;

import cn.whl.commonutils.exception.InputWrongException;
import cn.whl.commonutils.service.result.ResultParam;
import cn.whl.commonutils.service.result.ServiceResult;
import cn.whl.commonutils.service.result.ServiceResultTool;
import cn.whl.commonutils.verificate.VerificateTool;
import com.zy.zyras.client.domain.vo.FindServiceRequest;
import com.zy.zyras.client.domain.vo.RegistRequest;
import com.zy.zyras.client.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务发现Controller
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
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
    
}
