package com.zy.zyras.group.equality.service;

import com.zy.zyras.group.equality.domain.Ras;
import com.zy.zyras.group.equality.domain.vo.RegistRequest;
import com.zy.zyras.group.equality.domain.vo.RegistResponse;
import com.zy.zyras.group.equality.domain.vo.SynRequest;
import com.zy.zyras.group.equality.domain.vo.SynResponse;
import java.util.List;

/**
 * 业务层接口：集群
 * @author wuhailong
 */
public interface EqualityGroupService {

    /**
     * 向其他注册中心注册
     * @param name
     * @param port
     * @param registUrls 
     */
    public void registTo(String name, int port, List<String> registUrls);
    
    /**
     * 注册
     * @param rasName
     * @param registRequest
     * @return
     */
    public RegistResponse regist(String rasName, RegistRequest registRequest);

    /**
     * 向集群中的其他主机同步
     * @param groupSynTime
     */
    public void groupSyn(int groupSynTime);
    
    /**
     * 同步处理
     * @param synRequest
     * @return 
     */
    public SynResponse syn(SynRequest synRequest);

    /**
     * 更新requestToken
     * @param groupName
     * @return 
     */
    public String updateTokenByGroup(String groupName);

    

    
    
}
