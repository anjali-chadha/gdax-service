package com.anjalichadha.service;

import com.anjalichadha.exceptionHandling.InsufficientFillsException;
import com.anjalichadha.exceptionHandling.InvalidCurrencyPairException;
import com.anjalichadha.exceptionHandling.URLException;
import com.anjalichadha.model.*;
import com.anjalichadha.repository.TradeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TradeService implements ITradeService {

    @Autowired
    private TradeRepository repository;
    private static Logger logger = LoggerFactory.getLogger(TradeService.class);

    /**
     * @param req
     * @return
     * @throws InvalidCurrencyPairException
     */
    @Override
    public TradeResponse getQuotes(TradeRequest req) throws InvalidCurrencyPairException {

        OrderBookResponse response = repository.getOrderBook(req.getCurrencyPair());

        //If it is not successful for the first time, swap the currencies and try again.
        if (!HttpStatus.OK.equals(response.getStatus())) {
            logger.debug("First attempt with the " + req.getCurrencyPair() + " combination failed.");
            logger.debug("Second attempt with the" + req.getCurrencyPair(true) + "combination");
            response = repository.getOrderBook(req.getCurrencyPair(true));

            if (HttpStatus.NOT_FOUND.equals(response.getStatus())) {
                throw new InvalidCurrencyPairException(req.getCurrencyPair(true));
            } else if (HttpStatus.INTERNAL_SERVER_ERROR.equals(response.getStatus())) {
                throw new URLException();
            }
            return getAggregatedReverseQuote(response.getOrderBook(), req);
        }

        return getAggregatedQuote(response.getOrderBook(), req);
    }

    /**
     * Takes in a trade request, gives back the best possible price to buy/sell for a pair
     *
     * @param orderBook    : The current order book retrieved from GDAX API
     * @param tradeRequest : The request to buy or sell by the user
     * @return TradeResponse containing the aggregated quote
     */
    private TradeResponse getAggregatedQuote(OrderBook orderBook, TradeRequest tradeRequest) {
        Order[] ordersList = orderBook.createOrderList(tradeRequest.getTradeAction());
        BigDecimal tradeAmount = new BigDecimal(tradeRequest.getAmount());
        BigDecimal totalSize = new BigDecimal(0);
        BigDecimal totalPrice = new BigDecimal(0);

        //Calculate breaking index
        int length = ordersList.length;
        if(length == 0) {
            return new TradeResponse("0", "0", tradeRequest.getQuoteCurrency());
        }
        int breakingIndex = 0;
        BigDecimal newSize = null;
        for (int i = 0; i < length; i++) {
            newSize = totalSize.add(ordersList[i].getSize());
            if (newSize.compareTo(tradeAmount) >= 0) {
                breakingIndex = i;
                break;
            }
            totalSize = newSize;
        }
        if(tradeAmount.compareTo(newSize) > 0) {
            throw new InsufficientFillsException();
        }

        for (int i = 0; i < breakingIndex; i++) {
            Order currentOrder = ordersList[i];
            BigDecimal currentOrderPricePerUnit = currentOrder.getPrice();
            BigDecimal currentTotalPrice = currentOrderPricePerUnit.multiply(currentOrder.getSize());
            totalPrice = totalPrice.add(currentTotalPrice);
        }

        BigDecimal remainingSize = tradeAmount.subtract(totalSize);
        Order lastOrder = ordersList[breakingIndex];
        totalPrice = totalPrice.add(remainingSize.multiply(lastOrder.getPrice()));

        TradeResponse tradeResponse = new TradeResponse();
        tradeResponse.setCurrency(tradeRequest.getQuoteCurrency());
        tradeResponse.setTotal(totalPrice.setScale(8, BigDecimal.ROUND_FLOOR).toPlainString());
        tradeResponse.setPrice(totalPrice.divide(tradeAmount, 8, BigDecimal.ROUND_FLOOR).toPlainString());
        return tradeResponse;
    }

    /**
     * Takes in a trade request, gives back the best possible price to buy/sell for a reversed pair (i.e. the pair's inverse exists on GDAX)
     *
     * @param orderBook    : The current order book retrieved from GDAX API
     * @param tradeRequest : The request to buy or sell by the user
     * @return TradeResponse containing the aggregated quote
     */
    private TradeResponse getAggregatedReverseQuote(OrderBook orderBook, TradeRequest tradeRequest) {
        Order[] ordersList = orderBook.createOrderList(tradeRequest.getTradeAction());
        BigDecimal tradeAmount = new BigDecimal(tradeRequest.getAmount());
        BigDecimal totalSize = new BigDecimal(0);
        BigDecimal totalPrice = new BigDecimal(0);

        //Calculate breaking index
        int length = ordersList.length;
        if(length == 0) {
            return new TradeResponse("0", "0", tradeRequest.getQuoteCurrency());
        }
        int breakingIndex = 0;

        BigDecimal newSize = null;
        for (int i = 0; i < length; i++) {
            newSize = totalSize.add(ordersList[i].getSize().multiply(ordersList[i].getPrice()));

            if (newSize.compareTo(tradeAmount) >= 0) {
                breakingIndex = i;
                break;
            }
            totalSize = newSize;
        }

        if (tradeAmount.compareTo(newSize) > 0) {
            throw new InsufficientFillsException();
        }

        for (int i = 0; i < breakingIndex; i++) {
            Order currentOrder = ordersList[i];
            BigDecimal currentTotalPrice = currentOrder.getSize();
            totalPrice = totalPrice.add(currentTotalPrice);
        }

        BigDecimal remainingSize = tradeAmount.subtract(totalSize);
        Order lastOrder = ordersList[breakingIndex];
        BigDecimal fraction = remainingSize.divide(lastOrder.getPrice().multiply(lastOrder.getSize()), 8, BigDecimal.ROUND_FLOOR);
        totalPrice = totalPrice.add(fraction.multiply(lastOrder.getSize()));

        TradeResponse tradeResponse = new TradeResponse();
        tradeResponse.setCurrency(tradeRequest.getQuoteCurrency());
        tradeResponse.setTotal(totalPrice.setScale(8, BigDecimal.ROUND_FLOOR).toPlainString());
        tradeResponse.setPrice(totalPrice.divide(tradeAmount, 8, BigDecimal.ROUND_FLOOR).toPlainString());
        return tradeResponse;
    }
}
