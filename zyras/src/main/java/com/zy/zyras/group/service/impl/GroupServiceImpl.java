package com.zy.zyras.group.service.impl;

import cn.whl.commonutils.log.LoggerTools;
import com.alibaba.fastjson.JSON;
import com.zy.zyras.group.domain.Ras;
import com.zy.zyras.group.domain.vo.RegistRequest;
import com.zy.zyras.group.domain.vo.RegistResponse;
import com.zy.zyras.group.domain.vo.SynRequest;
import com.zy.zyras.group.domain.vo.SynResponse;
import com.zy.zyras.group.pool.RasPool;
import com.zy.zyras.group.service.GroupService;
import com.zy.zyras.ras.enums.GroupState;
import com.zy.zyras.ras.utils.RasUtils;
import com.zy.zyras.utils.HttpUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 * 业务层实现：集群
 * @author wuhailong
 */
@Service
public class GroupServiceImpl implements GroupService{

    @Override
    public void registTo(List<String> registUrls) {
        LoggerTools.log4j_write.info("开始集群注册");
        for(String url : registUrls){
            Map<String, Object> params = new HashMap<>();
            params.put("name", RasUtils.getRasName());
            params.put("port", RasUtils.getPort());
            String result = HttpUtil.doPost(url + "/group/regist", params);
            try{
                 LoggerTools.log4j_write.info(result);
                RegistResponse response = JSON.parseObject(result, RegistResponse.class);
                if(response.isSuccess()){
                    //注册成功，本方注册
                    registByRegistSuc(url, response.getRasName());
                }
            }catch(Exception e){
                LoggerTools.log4j_write.info("集群注册失败：" + url);
            }
        }
        LoggerTools.log4j_write.info("结束集群注册");
    }
    
    @Override
    public RegistResponse regist(RegistRequest registRequest) {
        RasPool pool = RasPool.getInstance();
        Ras ras = new Ras();
        ras.setName(registRequest.getName());
        ras.setUrl(registRequest.getUrl());
        pool.addRas(ras);
        
        RegistResponse response = new RegistResponse();
        response.setSuccess(true);
        response.setRasName(RasUtils.getRasName());
        LoggerTools.log4j_write.info("注册完成：" +  ras.getName());
        return response;
    }

    @Override
    public void groupSyn() {
        LoggerTools.log4j_write.info("开始集群同步");
        if(RasUtils.getGroupState() == GroupState.equality){
            LoggerTools.log4j_write.info("平等模式");
            //获取连接配置
            int groupSynTime = RasUtils.getGroupSynTime();
            Long date = new Date().getTime();
            //获取池
            RasPool pool = RasPool.getInstance();
            Map<String, Ras> rass = pool.getAllRass();
            for(Ras ras : rass.values()){
                LoggerTools.log4j_write.info("向" + ras.getName() + "同步");
                if(ras.getTms() == null || date - ras.getTms() > groupSynTime){
                    Map<String, Object> params = new HashMap<>();
                    String result = HttpUtil.doPost(ras.getUrl() + "/group/syn", params);
                }
                LoggerTools.log4j_write.info("向" + ras.getName() + "同步完成");
            }
        }
        
        LoggerTools.log4j_write.info("结束集群同步");
    }
    
    @Override
    public SynResponse syn(SynRequest synRequest) {
        LoggerTools.log4j_write.info("处理集群同步信息开始");
        
        //同步全部为增加，删除客户端均由心跳连接处理
        
        //同步注册中心列表
        
        //同步客户端列表
        //1.限制服务提供方
        
        //2.非限制服务提供方
        
        //3.服务调用方
        
        //4.网关
        
        SynResponse response = new SynResponse();
        
        return response;
    }
    
    @Override
    public List<Ras> getAllRass() {
        RasPool pool = RasPool.getInstance();
        Map<String, Ras> rass = pool.getAllRass();
        List<Ras> rasList = new ArrayList<>();
        for(Ras ras : rass.values()){
            rasList.add(ras);
        }
        
        return rasList;
    }

    /**
     * 将对方注册到本方的池中
     * @param response 
     */
    private void registByRegistSuc(String url, String name) {
        RasPool pool = RasPool.getInstance();
        Ras ras = new Ras();
        ras.setName(name);
        ras.setUrl(url);
        pool.addRas(ras);
    }

    

    

    
    
}
