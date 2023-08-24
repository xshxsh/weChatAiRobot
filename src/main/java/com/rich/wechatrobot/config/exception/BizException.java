package com.rich.wechatrobot.config.exception;

import com.rich.wechatrobot.model.common.ResponseEnum;

/**
 * @author: xsh
 * @create: 2021-06-15 17:21
 * @description: 通用异常
 **/

public class BizException extends RuntimeException {
    /**
     * 错误码
     */
    protected String DEFAULT_CODE = ResponseEnum.R_500.getCode();

    public BizException() {
        super();
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public BizException(String code, String message) {
        super(message);
        this.DEFAULT_CODE = code;
    }

    public static void throwIf(boolean condition, String code, String message) {
        if (condition) {
            throw new BizException(code, message);
        }
    }

    public static void throwIf(boolean condition, String message) {
        if (condition) {
            throw new BizException(ResponseEnum.R_500.getCode(), message);
        }
    }

    public static void throwIf(String message, Throwable throwable) {
        throw new BizException(message, throwable);
    }

    public String getCode() {
        return DEFAULT_CODE;
    }

    public void setCode(String DEFAULT_CODE) {
        this.DEFAULT_CODE = DEFAULT_CODE;
    }

}