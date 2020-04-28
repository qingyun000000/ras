package com.zy.zyrasc.exception;

/**
 * 服务池不存在异常
 * @author wuhailong
 */
public class ServicePoolNotExistException extends Exception{

    public ServicePoolNotExistException() {
    }

    public ServicePoolNotExistException(String message) {
        super(message);
    }

    public ServicePoolNotExistException(Throwable cause) {
        super(cause);
    }

}
