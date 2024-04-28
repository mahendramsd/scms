package com.minden.ai.scm.utils;

import org.springframework.http.HttpStatus;

/**
 * @author mahendrasridayarathna
 * @created 28/04/2024 - 11:30 am
 * @project IntelliJ IDEA
 */
public enum CustomErrorCodes {

    INVALID_USER(1001, "User - Not Found",HttpStatus.NOT_FOUND);

    private final int id;
    private final String msg;
    private final HttpStatus httpCode;

    CustomErrorCodes(int id, String msg, HttpStatus httpCode) {
        this.id = id;
        this.msg = msg;
        this.httpCode = httpCode;
    }

    public int getId() {
        return this.id;
    }

    public String getMsg() {
        return this.msg;
    }

    public HttpStatus getHttpCode() {
        return this.httpCode;
    }
}
