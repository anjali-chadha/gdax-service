package com.anjalichadha.model;

public class TradeResponse {
    private String price;
    private String total;
    private String currency;

    public TradeResponse(){

    }

    public TradeResponse(String price, String total, String currency) {
        this.price = price;
        this.total = total;
        this.currency = currency;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
