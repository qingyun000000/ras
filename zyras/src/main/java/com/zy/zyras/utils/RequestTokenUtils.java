package com.zy.zyras.utils;

import com.zy.zyras.token.RasSingleModeToken;
import cn.whl.commonutils.token.TokenTool;
import com.zy.zyras.initiate.RasUtilsInitiate;
import com.zy.zyras.set.RasSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * requestToken生成工具
 * @author wuhailong
 */
public class RequestTokenUtils {
    
    public static void setTokenBySingle() {
        try {
            String name = RasSet.getName();
            String token = TokenTool.createToken(name, new RasSingleModeToken());
            
            RasSet.setRequestToken(token);
            
        } catch (Exception ex) {
            Logger.getLogger(RasUtilsInitiate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
