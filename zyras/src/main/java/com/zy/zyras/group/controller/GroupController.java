package com.zy.zyras.group.controller;

import com.zy.zyras.group.equality.domain.vo.RegistRequest;
import com.zy.zyras.group.equality.domain.vo.RegistResponse;
import com.zy.zyras.group.equality.domain.vo.SynRequest;
import com.zy.zyras.group.equality.domain.vo.SynResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.zy.zyras.group.equality.service.EqualityGroupService;
import com.zy.zyras.group.service.GroupService;

/**
 * 集群Controller
 * @author wuhailong
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
