/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zy.zyrasc.fuse;

/**
 * 熔断器
 * @author wuhailong
 */
public class FuseService {
    
    /**
     * 熔断状态
     * @param serviceName
     * @return 
     */
    public static boolean fuse(String serviceName){
        
        return true;
    }
    
    /**
     * 服务失败时调用，修改熔断状态
     * @param serviceName 
     */
    public static void serviceFail(String serviceName){
        
    }
    
}
