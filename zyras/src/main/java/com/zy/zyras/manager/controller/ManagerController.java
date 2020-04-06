package com.zy.zyras.manager.controller;

import com.zy.zyras.client.domain.vo.CustomerResponse;
import com.zy.zyras.client.service.ClientService;
import com.zy.zyras.group.domain.Ras;
import com.zy.zyras.group.service.GroupService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 管理页面Controller
 * @author wuhailong
 * @createTime 2020-03-30
 * @updateTime 2020-03-30
 */
@Controller
@RequestMapping("/ras")
public class ManagerController {
    
    /*
     * 客户端业务组件
     */
    @Autowired
    private ClientService clientService; 
    
    /*
    * 集群组件
    */
    @Autowired
    private GroupService groupService; 
    
    /**
     * 管理主页
     * @param model
     * @return 
     */
    @GetMapping("/manager")
    public String mangerMainPage(Model model){
        List<CustomerResponse> customerClients = clientService.getCustomerClients();
        model.addAttribute("customers", customerClients);
        
        List<Ras> rass = groupService.getAllRass();
        model.addAttribute("rass", rass);
        
        return "manager";
    }
    
    /**
     * 客户端页面
     * @param model
     * @return 
     */
    @GetMapping("/client")
    public String clientPage(Model model){
        
        return "client";
    }
    
    /**
     * 服务页面
     * @param model
     * @return 
     */
    @GetMapping("/service")
    public String servicePage(Model model){
        
        return "service";
    }
    
    /**
     * 监控页面
     * @param model
     * @return 
     */
    @GetMapping("/monitor")
    public String monitorPage(Model model){
        
        return "monitor";
    }
}
