package com.zy.zyras.group.equality.service.impl;

import cn.whl.commonutils.log.LoggerTools;
import com.zy.zyras.token.RasEqualityModeToken;
import cn.whl.commonutils.token.TokenTool;
import com.alibaba.fastjson.JSON;
import com.zy.zyras.client.pool.ClientPool;
import com.zy.zyras.client.service.ClientService;
import com.zy.zyras.group.equality.domain.Ras;
import com.zy.zyras.group.equality.domain.vo.RegistRequest;
import com.zy.zyras.group.equality.domain.vo.RegistResponse;
import com.zy.zyras.group.equality.domain.vo.SynRequest;
import com.zy.zyras.group.equality.domain.vo.SynResponse;
import com.zy.zyras.group.pool.RasPool;
import com.zy.zyras.utils.HttpUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zy.zyras.group.equality.service.EqualityGroupService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 业务层实现：集群
 *
 * @author wuhailong
 */
@Service
public class EqualityGroupServiceImpl implements EqualityGroupService {

    //服务提供方修改锁
    private final Lock updateRassLock = new ReentrantLock();

    @Autowired
    private ClientService clientService;

    @Override
    public void registTo(String name, int port, List<String> registUrls) {
        LoggerTools.log4j_write.info("开始集群注册");

        //创建线程池
        ExecutorService threadPool = Executors.newCachedThreadPool();
        for (String url : registUrls) {
            threadPool.submit(() -> {
                Map<String, Object> params = new HashMap<>();
                params.put("name", name);
                params.put("port", port);
                String result = HttpUtil.doPost(url + "/group/regist", params);
                try {
                    LoggerTools.log4j_write.info(result);
                    RegistResponse response = JSON.parseObject(result, RegistResponse.class);
                    if (response.isSuccess()) {
                        //注册成功，本方注册
                        registByRegistSuc(url, response.getRasName());
                    }
                } catch (Exception e) {
                    LoggerTools.log4j_write.info("集群注册失败：" + url);
                }
            });
        }
        LoggerTools.log4j_write.info("结束集群注册");
    }

    @Override
    public RegistResponse regist(String rasName, RegistRequest registRequest) {
        RasPool pool = RasPool.getInstance();
        Ras ras = new Ras();
        ras.setName(registRequest.getName());
        ras.setUrl(registRequest.getUrl());
        System.out.println("接受" + JSON.toJSON(ras) + "注册");
        pool.addRas(ras);

        RegistResponse response = new RegistResponse();
        response.setSuccess(true);
        response.setRasName(rasName);
        LoggerTools.log4j_write.info("注册完成：" + ras.getName());

        return response;
    }

    @Override
    public void groupSyn(int groupSynTime) {
        LoggerTools.log4j_write.info("开始集群同步-平等模式");
        Long date = new Date().getTime();
        //获取池
        RasPool pool = RasPool.getInstance();
        Map<String, Ras> rass = pool.getAllRass();
        for (Ras ras : rass.values()) {
            LoggerTools.log4j_write.info("向" + ras.getName() + "同步");
            if (ras.getTms() == null || date - ras.getTms() > groupSynTime) {
                Map<String, Object> params = new HashMap<>();
                params.put("rasPoolJson", JSON.toJSON(RasPool.getInstance()));
                params.put("clientPoolJson", JSON.toJSON(ClientPool.getInstance()));
                System.out.println("同步至" + ras.getUrl());
                String result = HttpUtil.doPost(ras.getUrl() + "/group/syn", params);
            }
            LoggerTools.log4j_write.info("向" + ras.getName() + "同步完成");
            ras.setTms(date);
        }

        LoggerTools.log4j_write.info("结束集群同步");
    }

    @Override
    public SynResponse syn(SynRequest synRequest) {
        LoggerTools.log4j_write.info("处理集群同步信息开始");

        //同步注册中心列表
        RasPool rasPoolLocal = RasPool.getInstance();
        System.out.println(synRequest.getRasPoolJson());
        com.zy.zyras.group.equality.domain.vo.RasPool pool = JSON.parseObject(synRequest.getRasPoolJson(), com.zy.zyras.group.equality.domain.vo.RasPool.class);
        Map<String, Ras> newRass = pool.getAllRass();
        updateRassLock.lock();
        rasPoolLocal.synRass(newRass);
        updateRassLock.unlock();

        System.out.println("客户端：" + synRequest.getClientPoolJson());
        com.zy.zyras.group.equality.domain.vo.ClientPool pool2 = JSON.parseObject(synRequest.getClientPoolJson(), com.zy.zyras.group.equality.domain.vo.ClientPool.class);
        clientService.synClients(pool2);

        SynResponse response = new SynResponse();

        return response;
    }

    /**
     * 将对方注册到本方的池中
     *
     * @param response
     */
    private void registByRegistSuc(String url, String name) {
        RasPool pool = RasPool.getInstance();
        Ras ras = new Ras();
        ras.setName(name);
        ras.setUrl(url);
        updateRassLock.lock();
        pool.addRas(ras);
        updateRassLock.unlock();
    }

    @Override
    public String updateTokenByGroup(String groupName) {
        String token = null;
        try {
            token = TokenTool.createToken(groupName, new RasEqualityModeToken());
        } catch (Exception ex) {
            Logger.getLogger(EqualityGroupServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return token;
    }

}
