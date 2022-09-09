package com.charter.user.rewards.Exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value= {ApiException.class})
    public ResponseEntity<Object> handleApiException (ApiException ex) {
        return new ResponseEntity<>(ex.getExceptionMessage(), ex.getHttpStatus());
    }
}
