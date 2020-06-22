package com.zy.zyras.group.service.impl;

import cn.whl.commonutils.log.LoggerUtils;
import com.zy.coordc.exception.NodeExistExcepiton;
import com.zy.zyras.group.coord.service.CoordGroupService;
import com.zy.zyras.group.equality.domain.Ras;
import com.zy.zyras.group.equality.domain.vo.RegistRequest;
import com.zy.zyras.group.equality.domain.vo.RegistResponse;
import com.zy.zyras.group.equality.domain.vo.SynRequest;
import com.zy.zyras.group.equality.domain.vo.SynResponse;
import com.zy.zyras.group.equality.service.EqualityGroupService;
import com.zy.zyras.group.pool.RasPool;
import com.zy.zyras.group.service.GroupService;
import com.zy.zyras.enums.GroupMode;
import com.zy.zyras.set.RasSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 业务层实现：集群
 *
 * @author wuhailong
 */
@Service
public class GroupServiceImpl implements GroupService {
    
    @Autowired
    private EqualityGroupService equalityGroupService;
    
    @Autowired
    private CoordGroupService coordGroupService;
    
    @Override
    public void registTo() {
        if(RasSet.getGroupMode() == GroupMode.EQUALITY){
            equalityGroupService.registTo(RasSet.getName(), RasSet.getPort(), RasSet.getRegistUrls());
        }else if(RasSet.getGroupMode() == GroupMode.COORD){
            try {
                coordGroupService.regist(RasSet.getCoordUrl(), RasSet.getGroupName(), RasSet.getPort());
                coordGroupService.getMasterRAS(RasSet.getGroupName(), RasSet.getPort());
            } catch (NodeExistExcepiton ex) {
                LoggerUtils.log4j_write.error(ex);
            }
        }
    }

    @Override
    public void groupSyn() {
        if(RasSet.getGroupMode() == GroupMode.EQUALITY){
            equalityGroupService.groupSyn(RasSet.getGroupSynTime());
        }else if(RasSet.getGroupMode() == GroupMode.COORD){
            
        }
    }

    @Override
    public List<Ras> getAllRass() {
        RasPool pool = RasPool.getInstance();
        Map<String, Ras> rass = pool.getAllRass();
        List<Ras> rasList = new ArrayList<>();
        for (Ras ras : rass.values()) {
            rasList.add(ras);
        }

        return rasList;
    }

    @Override
    public RegistResponse regist(RegistRequest registRequest) {
        RegistResponse registResponse = null;
        if(RasSet.getGroupMode() == GroupMode.EQUALITY){
            return equalityGroupService.regist(RasSet.getName(), registRequest);
        }
        return registResponse;
    }

    @Override
    public SynResponse syn(SynRequest synRequest) {
        SynResponse synResponse = null;
        if(RasSet.getGroupMode() == GroupMode.EQUALITY){
            synResponse = equalityGroupService.syn(synRequest);
        }
        return synResponse;
    }

    @Override
    public void updateTokenByGroup() {
        String token = "";
        if(RasSet.getGroupMode() == GroupMode.EQUALITY){
            token = equalityGroupService.updateTokenByGroup(RasSet.getGroupName());
        }else if(RasSet.getGroupMode() == GroupMode.COORD){
            try {
                token = coordGroupService.updateRequestToken(RasSet.getGroupName());
            } catch (Exception ex) {
                Logger.getLogger(GroupServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        RasSet.setRequestToken(token);
    }

}
