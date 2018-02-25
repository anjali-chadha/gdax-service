package com.anjalichadha.model;

import java.math.BigDecimal;

public class OrderBook {
    private long sequence;
    private String[][] bids;
    private String[][] asks;

    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public String[][] getBids() {
        return bids;
    }

    public void setBids(String[][] bids) {
        this.bids = bids;
    }

    public String[][] getAsks() {
        return asks;
    }

    public void setAsks(String[][] asks) {
        this.asks = asks;
    }

    private String[][] getOrders(TradeAction action) {
        if (action.equals(TradeAction.BUY)) {
            return getAsks();
        }
        return getBids();
    }

    public Order[] createOrderList(TradeAction action) {
        String[][] orderArray = getOrders(action);
        if(orderArray ==  null) return new Order[]{};

        int n = orderArray.length;
        Order[] ordersList = new Order[n];
        for (int i = 0; i < n; i++) {
            String[] order = orderArray[i];
            BigDecimal price = new BigDecimal(order[0]);
            BigDecimal size = new BigDecimal(order[1]);
            long numberOrders = Long.parseLong(order[2]);
            Order newOrder = new Order(price, size, numberOrders);
            ordersList[i] = newOrder;
        }
        return ordersList;
    }
}