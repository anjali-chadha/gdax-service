package com.anjalichadha.exceptionHandling;

import org.springframework.http.HttpStatus;

public class GdaxException extends RuntimeException{
    private String message;
    private ErrorResource errorResource;

    public GdaxException() {

    }
    public GdaxException(String message) {
        super(message);
        this.message = message;
    }

    public ErrorResource getErrorResource() {
        if(errorResource == null) {
            errorResource = new ErrorResource(HttpStatus.SERVICE_UNAVAILABLE.toString(),
                    "Unexpected Error");
        }
        return errorResource;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
