package com.anjalichadha.repository;

import com.anjalichadha.exceptionHandling.GdaxException;
import com.anjalichadha.exceptionHandling.InvalidCurrencyPairException;
import com.anjalichadha.model.OrderBook;
import com.anjalichadha.model.OrderBookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TradeRepository {

    @Value("${GDAX_PRODUCT_REQ_URL}")
    private String url;

    private Logger logger= LoggerFactory.getLogger(TradeRepository.class);

    public OrderBookResponse getOrderBook(String productId) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map = new HashMap<>();
        map.put("productId", productId);

        OrderBookResponse bookResponse = new OrderBookResponse();
        try {
            ResponseEntity<OrderBook> responseEntity = restTemplate.getForEntity(url, OrderBook.class, map);
            bookResponse.setHttpStatus(responseEntity.getStatusCode());
            bookResponse.setOrderBook(responseEntity.getBody());
        } catch(HttpClientErrorException | HttpServerErrorException e) {
            bookResponse.setHttpStatus(e.getStatusCode());
        } catch (Exception e) {
            throw new GdaxException();
        }
        return bookResponse;
    }


    public static void main(String[] args) {
        TradeRepository o = new TradeRepository();
        o.getOrderBook("USD-BTC");
    }
}
