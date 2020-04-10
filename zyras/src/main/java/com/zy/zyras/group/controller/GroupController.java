package com.zy.zyras.group.controller;

import com.zy.zyras.group.domain.vo.RegistRequest;
import com.zy.zyras.group.domain.vo.RegistResponse;
import com.zy.zyras.group.domain.vo.SynRequest;
import com.zy.zyras.group.domain.vo.SynResponse;
import com.zy.zyras.group.service.GroupService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 集群Controller
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
 */
@RestController
@RequestMapping("/group")
public class GroupController {
    
    /*
     * 客户端业务组件
     */
    @Autowired
    private GroupService groupService; 
    
    @PostMapping("/regist")
    public RegistResponse regist(RegistRequest registRequest, HttpServletRequest req) {
        registRequest.setUrl("http://" + req.getRemoteAddr() + ":" + registRequest.getPort());
        return groupService.regist(registRequest);
    }
    
    @PostMapping("/syn")
    public SynResponse syn(SynRequest synRequest, HttpServletRequest req) {
        return groupService.syn(synRequest);
    }
   
}
