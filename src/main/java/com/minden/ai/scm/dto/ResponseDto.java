package com.minden.ai.scm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author mahendrasridayarathna
 * @created 27/04/2024 - 11:38 am
 * @project IntelliJ IDEA
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode
public class ResponseDto {

    private ResponseEnum status;
    private Object data;

    /**
     * success Message
     *
     * @param data
     * @return
     */
    public static ResponseDto success(Object data) {
        return new ResponseDto(ResponseEnum.Ok, data);
    }

    /**
     * Fail Message
     *
     * @param data
     * @return
     */
    public static ResponseDto failure(Object data) {
        return new ResponseDto(ResponseEnum.FAILURE, data);
    }
}
