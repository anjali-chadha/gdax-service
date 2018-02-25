package com.anjalichadha.model;

import java.math.BigDecimal;

public class Order {
    private BigDecimal price;
    private BigDecimal size;
    private long numOrders;

    public Order(BigDecimal price, BigDecimal size, long numOrders) {
        this.price = price;
        this.size = size;
        this.numOrders = numOrders;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getSize() {
        return size;
    }

    public long getNumOrders() {
        return numOrders;
    }
}
