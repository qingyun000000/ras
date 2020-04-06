package com.zy.zyras.group.domain;

/**
 * 实体：注册中心
 * @author wuhailong
 * @createTime 2020-03-27
 * @updateTime 2020-03-27
 */
public class Ras {
    
    /*
     * 名
     */
    private String name;
    
    /*
    * 地址
    */
    private String url; 
    
    /*
    * 心跳检测连续失败的次数
    */
    private int failNum;
    
    /*
    * 心跳连接时间戳
    */
    private Long tms;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getFailNum() {
        return failNum;
    }

    public void setFailNum(int failNum) {
        this.failNum = failNum;
    }

    public Long getTms() {
        return tms;
    }

    public void setTms(Long tms) {
        this.tms = tms;
    }
    
}
