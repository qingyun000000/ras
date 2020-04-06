/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zy.zyras.ras.utils;

import cn.whl.commonutils.token.RasEqualityModeToken;
import cn.whl.commonutils.token.RasSingleModeToken;
import cn.whl.commonutils.token.TokenTool;
import com.zy.zyras.ras.initiate.RasUtilsInitiate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class RequestTokenUtils {
    
    
    public static void setTokenBySingle() {
        try {
            String name = RasUtils.getRasName();
            String token = TokenTool.createToken(name, new RasSingleModeToken());
            
            RasUtils.setRequestToken(token);
            
        } catch (Exception ex) {
            Logger.getLogger(RasUtilsInitiate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void setTokenByEquality() {
        try {
            String groupName = RasUtils.getGroupName();
            String token = TokenTool.createToken(groupName, new RasEqualityModeToken());
            
            RasUtils.setRequestToken(token);
            
        } catch (Exception ex) {
            Logger.getLogger(RasUtilsInitiate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
