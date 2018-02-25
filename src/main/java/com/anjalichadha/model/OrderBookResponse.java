package com.anjalichadha.model;


import org.springframework.http.HttpStatus;

public class OrderBookResponse {
    private OrderBook orderBook;
    private HttpStatus httpStatus;

    public void setOrderBook(OrderBook orderBook) {
        this.orderBook = orderBook;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public OrderBook getOrderBook() {
        return orderBook;
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }


}
