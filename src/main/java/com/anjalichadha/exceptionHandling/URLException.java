package com.anjalichadha.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class URLException extends RuntimeException {

    private static final long serialVersionUID = -55620100049175983L;

    public URLException() {
        super("Problem accessing the backend server");
    }

    public URLException(Exception cause) {
        super("Problem accessing the backend server", cause);
    }
}
