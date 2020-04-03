/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zy.zyrasclient.remote;

import com.zy.zyrasc.annotation.RemoteService;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author wuhailong
 */
@RemoteService(serviceName = "comm5")
public interface TestRemoteClass2 {
    
    @RequestMapping("/url2")
    public String test2();
}
