package com.charter.user.rewards.Exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiException extends Exception {
    private final HttpStatus httpStatus;
    private final String exceptionMessage;

    public ApiException (HttpStatus httpStatus, String exceptionMessage) {
        this.httpStatus = httpStatus;
        this.exceptionMessage = exceptionMessage;
    }

}
