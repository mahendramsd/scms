package com.minden.ai.scm.utils;

import org.springframework.http.HttpStatus;

/**
 * @author mahendrasridayarathna
 * @created 28/04/2024 - 11:30 am
 * @project IntelliJ IDEA
 */
public enum CustomErrorCodes {


    STUDENT_ALREADY_EXISTS(1001, "Student - Student already exists",HttpStatus.BAD_REQUEST),
    COURSE_ALREADY_EXISTS(1002, "Course - Course already exists",HttpStatus.BAD_REQUEST);


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
