package com.zy.zyrasc.server;

import com.alibaba.fastjson.JSON;
import com.zy.zyrasc.status.ClientStatus;
import com.zy.zyrasc.utils.HttpUtil;
import com.zy.zyrasc.vo.RegistResponse;
import com.zy.zyrasc.vo.ServiceResult;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

/**
 * 服务发现
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
 */
@Component
public class RegistService {

    public static boolean regist(String type, String name, String uniName, String serviceType, String interList, String rasUrl) throws Exception {
        String result = null;

        Map<String, String> params = new HashMap<>();
        params.put("clientType", type);
        params.put("name", name);
        if (uniName != null && !"".equals(uniName.trim())) {
            params.put("uniName", uniName);
        }
        if ("service".equals(type)) {
            params.put("serviceType", serviceType);
            if ("limited".equals(serviceType)) {
                params.put("interList", interList);
            }
            result = HttpUtil.doPost(rasUrl + "/client/regist/serviceRegist", params);
        } else if ("customer".equals(type)) {
            try{
                result = HttpUtil.doPost(rasUrl + "/client/regist/customerRegist", params);
            }catch(Exception ex){
                System.out.println("暂时无法连接");
            }
        } else if ("serviceAndCustomerRegist".equals(type)) {
            params.put("serviceType", serviceType);
            if ("limited".equals(serviceType)) {
                params.put("interList", interList);
            }
            result = HttpUtil.doPost(rasUrl + "/client/regist/serviceAndCustomerRegist", params);
        } else if ("gateway".equals(type)) {
            result = HttpUtil.doPost(rasUrl + "/client/regist/gatewayRegist", params);
        } else {
            if (serviceType != null && !"".equals(serviceType.trim())) {
                params.put("serviceType", serviceType);
            }
            if ("limited".equals(serviceType)) {
                params.put("interList", interList);
            }
            result = HttpUtil.doPost(rasUrl + "/client/regist/regist", params);
        }

        //解析报文
        System.out.println("注册返回报文：" + result);
        ServiceResult result1 = JSON.parseObject(result, ServiceResult.class);

        if(result1.isSuccess()){
            System.out.println("注册成功！");
            RegistResponse registResponse = (RegistResponse)result1.getData();
            ClientStatus.setType(type);
            ClientStatus.setRasUrl(rasUrl);
            ClientStatus.setToken(registResponse.getToken());
            return true;
        }else{
            System.out.println("注册失败：" + result1.getMessage());
            throw new Exception("注册失败");
        }
        
        
    }
    
}
