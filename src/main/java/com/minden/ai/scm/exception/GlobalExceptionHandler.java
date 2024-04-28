package com.minden.ai.scm.exception;

import com.minden.ai.scm.dto.AuthErrorDto;
import com.minden.ai.scm.dto.ResponseDto;
import com.minden.ai.scm.utils.CustomErrorCodes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author mahendrasridayarathna
 * @created 27/04/2024 - 11:35 am
 * @project IntelliJ IDEA
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(Exception ex, WebRequest request) {
        ResponseDto responseDto = ResponseDto.failure(ex.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex) {
        ResponseDto responseDto = ResponseDto.failure(ex.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> globalRuntimeExceptionHandler(Exception ex) {
        ResponseDto responseDto = ResponseDto.failure(ex.getMessage());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<?> customExceptionHandler(CustomException customException) {
        CustomErrorCodes customErrorCodes = customException.getCustomErrorCodes();
        AuthErrorDto authErrorDto =
                new AuthErrorDto(customErrorCodes.getId(), customErrorCodes.getMsg());
        return new ResponseEntity<>(authErrorDto, customErrorCodes.getHttpCode());
    }



}

