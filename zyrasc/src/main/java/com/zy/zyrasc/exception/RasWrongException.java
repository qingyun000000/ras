package com.zy.zyrasc.exception;

/**
 * ras非法异常
 * @createTime 2019-12-05
 * @updateTime 2019-12-05
 * @author wuhailong
 */
public class RasWrongException extends Exception{

    public RasWrongException() {
    }

    public RasWrongException(String message) {
        super(message);
    }

    public RasWrongException(Throwable cause) {
        super(cause);
    }

}
