package com.example.novashop22.commun.exception;

import lombok.Getter;

/**
 * Created by Dashy on 04/08/2020.
 */
@Getter
public class NotFoundException extends BusinessException {

    public NotFoundException() {
        super();
    }

    /**
     * @param msg : error message with default value of code :400
     */
    public NotFoundException(String msg) {
        super(msg);
        this.code = "404";
    }

    /**
     * @param message : message
     * @param cause   : cause
     *                code : default value of code : 400
     */
    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.code = "404";
    }

    public NotFoundException(String msg, String code) {
        super(msg);
        this.code = code;
    }

}
