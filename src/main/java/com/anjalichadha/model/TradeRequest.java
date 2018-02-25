package com.anjalichadha.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TradeRequest {

    private String action;

    @JsonProperty("base_currency")
    private String baseCurrency;

    @JsonProperty("quote_currency")
    private String quoteCurrency;

    private String amount;

    public String getAction() {
        return action;
    }

    public TradeAction getTradeAction() {
        return TradeAction.valueOf(action.toUpperCase());
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getQuoteCurrency() {
        return quoteCurrency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAction(String action) {
        this.action = action.trim();
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency.trim();
    }

    public void setQuoteCurrency(String quoteCurrency) {
        this.quoteCurrency = quoteCurrency.trim();
    }

    public void setAmount(String amount) {
        this.amount = amount.trim();
    }

    @Override
    public String toString() {
        return "TradeRequest{" +
                "action='" + action + '\'' +
                ", baseCurrency='" + baseCurrency + '\'' +
                ", quoteCurrency='" + quoteCurrency + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }

    public String getCurrencyPair() {
        return getCurrencyPair(false);
    }

    /**
     * Simply concatenates the base and quote currency to be consumed by the GDAX API
     *
     * @param inverse : Whether the currency pair should be inverted
     * @return : The pair as a dash separated string
     */
    public String getCurrencyPair(boolean inverse) {
        if (inverse) {
            return quoteCurrency + "-" + baseCurrency;
        }
        return baseCurrency + "-" + quoteCurrency;
    }
}

