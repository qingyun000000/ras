/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zy.zyrasclient.remote;

import org.springframework.stereotype.Service;

/**
 *
 * @author wuhailong
 */
@Service
public class TestRemoteClassImpl implements TestRemoteClass{

    @Override
    public String test() {
        return "这里时降级方法";
    }
    
}
