package com.example.novashop22.commun.exception;

import lombok.Getter;

/**
 * Created by Dashy on 04/08/2020.
 */
@Getter
public class BusinessException extends RuntimeException {

    protected String code;

    public BusinessException() {
        super();
    }

    /**
     * @param msg : error message with default value of code :400
     */
    public BusinessException(String msg) {
        super(msg);
        this.code = "400";
    }

    /**
     * @param message : message
     * @param cause   : cause
     *                code : default value of code : 400
     */
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = "400";
    }

    public BusinessException(String msg, String code) {
        super(msg);
        this.code = code;
    }

}
