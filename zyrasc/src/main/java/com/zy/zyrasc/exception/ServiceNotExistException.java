package com.zy.zyrasc.exception;

/**
 * 服务不存在异常
 * @createTime 2019-12-05
 * @updateTime 2019-12-05
 * @author wuhailong
 */
public class ServiceNotExistException extends Exception{

    public ServiceNotExistException() {
    }

    public ServiceNotExistException(String message) {
        super(message);
    }

    public ServiceNotExistException(Throwable cause) {
        super(cause);
    }

}
