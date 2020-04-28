package com.zy.zyrasclient.remote;

import com.zy.zyrasc.annotation.RemoteService;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.zy.zyrasclient.TestRequest;
import com.zy.zyrasclient.Test2Request;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author wuhailong
 */
@RemoteService(ras = "ras", service = "serv")
public interface TestRemoteClass {
    
    @PostMapping(value = "/test3")
    public Map<String, String> test(TestRequest test, Test2Request test2);
}
