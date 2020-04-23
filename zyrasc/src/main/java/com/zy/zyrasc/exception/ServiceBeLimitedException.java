package com.zy.zyrasc.exception;

/**
 * 服务被限制访问异常
 * @author wuhailong
 */
public class ServiceBeLimitedException extends Exception{

    public ServiceBeLimitedException() {
    }

    public ServiceBeLimitedException(String message) {
        super(message);
    }

    public ServiceBeLimitedException(Throwable cause) {
        super(cause);
    }

}
