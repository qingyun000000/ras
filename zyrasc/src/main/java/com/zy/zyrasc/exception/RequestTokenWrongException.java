package com.zy.zyrasc.exception;

/**
 * requestToken错误异常
 * @createTime 2019-12-05
 * @updateTime 2019-12-05
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
