package com.zy.zyrasclient;

import com.zy.zyrasclient.remote.TestRemoteClass;
import com.zy.zyrasclient.remote.TestRemoteClass2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author wuhailong
 */
@RestController
public class TestController {
    
    @Autowired
    private TestRemoteClass remoteClass;
    
    @Autowired
    private TestRemoteClass2 remoteClass2;
    
    @RequestMapping("/test")
    public String test(){
        return remoteClass.test();
    }
    
    @RequestMapping("/test2")
    public String test2(){
        return remoteClass2.test2();
    }
}
