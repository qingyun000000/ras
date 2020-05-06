package com.zy.zyras.group.coord.service;

/**
 * coord业务接口
 * @author wuhailong
 */
public interface CoordClientService {
    
    /**
     * 客户端注册和心跳连接
     */
    public void regist();
    
    /**
     * 获取主设备（没有主设备，则注册自己为主设备(临时节点，自己宕机后则释放节点)）
     */
    public void getMasterRAS();
    
    /**
     * 更新token
     * @return 
     */
    public String updateRequestToken();
}
