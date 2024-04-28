package com.minden.ai.scm.exception;

import java.util.Date;

/**
 * @author mahendrasridayarathna
 * @created 28/04/2024 - 11:33 am
 * @project IntelliJ IDEA
 */
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;

    public ErrorDetails(Date timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

}
