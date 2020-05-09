package com.zy.zyras.group.service;

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
public interface GroupService {

    /**
     * 向其他注册中心注册
     */
    public void registTo();
    
    /**
     * 向集群中的其他主机同步
     */
    public void groupSyn();

    /**
     * 全部注册中心
     * @return
     */
    public List<Ras> getAllRass();

    /**
     * 注册
     * @param registRequest
     * @return 
     */
    public RegistResponse regist(RegistRequest registRequest);

    /**
     * 同步
     * @param synRequest
     * @return 
     */
    public SynResponse syn(SynRequest synRequest);

    /**
     * 更新token
     */
    public void updateTokenByGroup();

    
    
}
