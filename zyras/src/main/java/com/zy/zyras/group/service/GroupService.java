package com.zy.zyras.group.service;

import com.zy.zyras.group.domain.Ras;
import com.zy.zyras.group.domain.vo.RegistRequest;
import com.zy.zyras.group.domain.vo.RegistResponse;
import java.util.List;

/**
 * 业务层接口：集群
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
 */
public interface GroupService {

    /**
     * 向其他注册中心注册
     * @param registUrls 
     */
    public void registTo(List<String> registUrls);
    
    /**
     * 注册
     * @param registRequest
     * @return
     */
    public RegistResponse regist(RegistRequest registRequest);

    /**
     * 集群同步
     */
    public void groupSyn();

    /**
     * 全部注册中心
     * @return
     */
    public List<Ras> getAllRass();

    
    
}
