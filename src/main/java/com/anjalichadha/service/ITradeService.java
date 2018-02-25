package com.anjalichadha.service;

import com.anjalichadha.exceptionHandling.InvalidCurrencyPairException;
import com.anjalichadha.model.TradeRequest;
import com.anjalichadha.model.TradeResponse;

public interface ITradeService {
    TradeResponse getQuotes(TradeRequest req) throws InvalidCurrencyPairException;
}
