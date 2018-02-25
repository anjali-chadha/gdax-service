package com.anjalichadha.model;

/**
 * Enumerator to identify if the API User has specified a Buy or Sell action in the request
 */
public enum TradeAction {
    BUY("buy"),
    SELL("sell");

    private final String action;

    TradeAction(final String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return action;
    }
}
