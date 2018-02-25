package com.anjalichadha.exceptionHandling;

import org.springframework.http.HttpStatus;

public class InvalidCurrencyPairException extends InvalidRequestException {

    private static final long serialVersionUID = -5562010004917511663L;
    private String productId;
    private String message;
    private ErrorResource errorResource;

    public InvalidCurrencyPairException(String productId) {
        super("Invalid Currency Pair: " + productId);
        this.productId = productId;
    }

    public String getProductId(){
        return productId;
    }

    public String getErrorMessage() {
        return "Invalid Currency Pair: " + productId;
    }

    public ErrorResource getErrorResource() {
        if(errorResource == null) {
            return new ErrorResource(HttpStatus.BAD_REQUEST.toString(), "Invalid Currency Pair: " + productId );
        }
        return errorResource;
    }
}