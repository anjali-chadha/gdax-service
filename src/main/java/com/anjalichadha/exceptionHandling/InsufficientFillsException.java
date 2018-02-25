package com.anjalichadha.exceptionHandling;

import org.springframework.http.HttpStatus;

public class InsufficientFillsException extends GdaxException {
    private String message;
    private ErrorResource errorResource;

    public InsufficientFillsException() {
    }

    public ErrorResource getErrorResource() {

        if(errorResource == null) {
            return new ErrorResource(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "The amount requested is too large to be processed. Please try again with smaller amount.");
        }
        return errorResource;
    }
}
