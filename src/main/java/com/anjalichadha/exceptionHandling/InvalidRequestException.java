package com.anjalichadha.exceptionHandling;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class InvalidRequestException extends RuntimeException {
    private String invalidField;
    private String message;
    private ErrorResource errorResource;

    public InvalidRequestException(String invalidField) {
        super("Invalid field");
        this.invalidField = invalidField;
    }

    public InvalidRequestException(String invalidField, String message) {
        super(message);
        this.invalidField = invalidField;
        this.message = message;
    }

    public String getInvalidField() {
        return invalidField;
    }

    public ErrorResource getErrorResource() {
        if(errorResource == null) {
            return new ErrorResource(HttpStatus.BAD_REQUEST.toString(), invalidField + " field is invalid. "+ this.message);
        }
        return errorResource;
    }
}
