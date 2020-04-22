package com.zy.zyrasc.vo;

/**
 * 服务端结果返回
 * @author wuhailong
 */
public class ServiceResult {
    
    private boolean success;
    
    private String message;
    
    //返回对象数据
    private Object data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    
    //Boolean类型参数, 用于返回Boolean值
    private Boolean beTrue;
    
    //Long类型参数, 用于返回id等信息
    private Long resultId;
    
    //String类型参数, 用于返回url
    private String url;
    
    //String类型参数，用于返回各种名称、title、stem等
    private String name;
    
    //int类型参数，用于返回int型数据
    private int intValue;
    
    private String exceptionCode;

    public Long getResultId() {
        return resultId;
    }

    public void setResultId(Long resultId) {
        this.resultId = resultId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public Boolean getBeTrue() {
        return beTrue;
    }

    public void setBeTrue(Boolean beTrue) {
        this.beTrue = beTrue;
    }

    public String getExceptionCode() {
        return exceptionCode;
    }

    public void setExceptionCode(String exceptionCode) {
        this.exceptionCode = exceptionCode;
    }
    
}
