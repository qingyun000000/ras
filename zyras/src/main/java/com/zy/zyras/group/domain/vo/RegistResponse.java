package com.zy.zyras.group.domain.vo;

/**
 * 注册返回参数
 * @author wuhailong
 */
public class RegistResponse {
 
    private boolean success;
    
    private String rasName;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getRasName() {
        return rasName;
    }

    public void setRasName(String rasName) {
        this.rasName = rasName;
    }
    
}
