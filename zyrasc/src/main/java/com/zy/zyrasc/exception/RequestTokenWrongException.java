package com.zy.zyrasc.exception;

/**
 * requestToken错误异常
 * @author wuhailong
 */
public class RequestTokenWrongException extends Exception{

    public RequestTokenWrongException() {
    }

    public RequestTokenWrongException(String message) {
        super(message);
    }

    public RequestTokenWrongException(Throwable cause) {
        super(cause);
    }

}
