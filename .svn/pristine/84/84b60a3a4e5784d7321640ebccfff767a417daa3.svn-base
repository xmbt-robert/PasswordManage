package com.fx.passform.Exception;

/**
 * 系统异常
 * <p/>
 * Created by fan.xu on 2014/10/21.
 */
public class PassFormException extends RuntimeException {

    /**
     * 错误码
     */
    private int code;

    public int getCode() {
        return code;
    }

    public PassFormException(int code) {
        this.code = code;
    }

    public PassFormException(String message, int code) {
        super(message);
        this.code = code;
    }

    public PassFormException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public PassFormException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }
}
