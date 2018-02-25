package com.anjalichadha.exceptionHandling;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResource {
    private String code;
    private String message;

    public ErrorResource() { }

    public ErrorResource(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() { return code; }

    public void setCode(String code) { this.code = code; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }
}