/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zy.zyrasclient.remote;

import com.zy.zyrasc.annotation.RemoteService;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author wuhailong
 */
@RemoteService(ras = "ras", service = "comm")
public interface TestRemoteClass {
    
    @RequestMapping(value = "/url", method = RequestMethod.GET)
    public Map<String, String> test();
}
