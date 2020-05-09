package com.zy.zyras.token;

import cn.whl.commonutils.token.Token;
import java.util.Date;

/**
 * ras注册中心集群平等工作模式token
 * @author wuhailong
 */
public class RasEqualityModeToken implements Token{

    private final static String START  = "TOKEN";

    @Override
    public String createToken(String str) throws Exception{
        return START + str + "_" + new Date().getTime();
    }

    @Override
    public String decodeToken(String code) throws Exception{
        code = code.substring(START.length(), code.indexOf("_")); 
        return code;
    }

}
