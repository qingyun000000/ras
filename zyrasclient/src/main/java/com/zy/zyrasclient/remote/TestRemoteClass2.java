package com.zy.zyrasclient.remote;

import com.zy.zyrasc.annotation.RemoteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author wuhailong
 */
@RemoteService(ras = "ras", service = "comm5")
public interface TestRemoteClass2 {
    
    @RequestMapping(value = "/url2", method = RequestMethod.GET)
    public String test2();
}
