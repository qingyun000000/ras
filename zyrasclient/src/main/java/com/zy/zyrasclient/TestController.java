package com.zy.zyrasclient;

import com.zy.zyrasclient.remote.TestRemoteClass;
import com.zy.zyrasclient.remote.TestRemoteClass2;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
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
    public Map<String, String> test(){
        System.out.println("test方法");
        return remoteClass.test();
    }
    
    @RequestMapping("/test2")
    public String test2(){
        return remoteClass2.test2();
    }
    
    @RequestMapping("/test3")
    public Map<String, String> test3(){
        Map<String, String> map = new HashMap<>();
        map.put("success", "true");
        map.put("message", "这里是远程方法");
        return map;
    }
}
