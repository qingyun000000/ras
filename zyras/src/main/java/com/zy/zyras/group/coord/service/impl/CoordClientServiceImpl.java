package com.zy.zyras.group.coord.service.impl;

import com.zy.coordc.CoordService;
import com.zy.coordc.CoordState;
import com.zy.coordc.enums.NodeType;
import com.zy.coordc.exception.NodeExistExcepiton;
import com.zy.coordc.vo.CreateNodeRequest;
import com.zy.coordc.vo.CreateNodeResponse;
import com.zy.coordc.vo.GetNodeRequest;
import com.zy.coordc.vo.NodeResponse;
import com.zy.coordc.vo.RegistRequest;
import com.zy.coordc.vo.RegistResponse;
import com.zy.zyras.group.coord.CoordRasState;
import com.zy.zyras.group.coord.service.CoordClientService;
import com.zy.zyras.ras.utils.RasUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 * Coord服务
 * @author wuhailong
 */
@Service
public class CoordClientServiceImpl implements CoordClientService{
    
    @Override
    public void regist(){
        RegistRequest request = new RegistRequest();
        request.setCoordUrl("");
        try {
            RegistResponse registResponse = CoordService.regist(request);
        } catch (NodeExistExcepiton ex) {
            GetNodeRequest request1 = new GetNodeRequest();
            NodeResponse masterNode = CoordService.getNode(request1);
            CoordRasState.setMaster(false);
        }
    }
    
    @Override
    public void getMasterRAS(){
        CreateNodeRequest request = new CreateNodeRequest();
        request.setCoordUrl("");
        request.setNodeType(NodeType.TEMPORARY);
        request.setNodeKey("zyRas_" + RasUtils.getGroupName() +"_Master");
        request.setNodeValue("RemoteAddr:" + RasUtils.getPort());
        try {
            CreateNodeResponse createNode = CoordService.createNode(request);
            CoordRasState.setMaster(true);
        } catch (NodeExistExcepiton ex) {
            GetNodeRequest request1 = new GetNodeRequest();
            NodeResponse masterNode = CoordService.getNode(request1);
            CoordRasState.setMaster(false);
        }
    }
    
    @Override
    public String updateRequestToken(){
        if(CoordRasState.isMaster()){
            
        }else{
            
        }
        return null;
    }
    
    
}
