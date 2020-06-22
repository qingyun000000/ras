package com.zy.zyras.group.coord.service;

import com.zy.coordc.exception.NodeExistExcepiton;
import com.zy.zyras.group.equality.domain.Ras;
import java.util.List;

/**
 * coord业务接口
 * @author wuhailong
 */
public interface CoordGroupService {
    
    /**
     * 客户端注册和心跳连接
     * @param coordUrl
     * @param group
     * @param port
     * @throws com.zy.coordc.exception.NodeExistExcepiton
     */
    public void regist(String coordUrl, String group, int port) throws NodeExistExcepiton;
    
    /**
     * 获取主设备（没有主设备，则注册自己为主设备(临时节点，自己宕机后则释放节点)）
     * @param group
     * @param port
     */
    public void getMasterRAS(String group, int port);
    
    /**
     * 更新token
     * @param group
     * @return 
     * @throws java.lang.Exception 
     */
    public String updateRequestToken(String group) throws Exception;

}
