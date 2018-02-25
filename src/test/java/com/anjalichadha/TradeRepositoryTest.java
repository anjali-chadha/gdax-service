/*
package com.anjalichadha;

import com.anjalichadha.model.OrderBookResponse;
import com.anjalichadha.repository.TradeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TradeRepositoryTest {

    @Autowired
    private TradeRepository tradeRepository;

    @Before
    public void setUp() {
        tradeRepository = new TradeRepository();
    }

    @Test
    public void testWithValidProductId1() {
        OrderBookResponse response = tradeRepository.getOrderBook("BCH-BTC");
        assert response.getStatus().equals(HttpStatus.OK);
    }

    @Test
    public void testWithValidProductId2() {
        OrderBookResponse response = tradeRepository.getOrderBook("ETH-EUR");
        assert response.getStatus().equals(HttpStatus.OK);
    }

    @Test
    public void testWithInvalidProductId() {
        OrderBookResponse response = tradeRepository.getOrderBook("ETHEUR");
        assert  response.getStatus().equals(HttpStatus.NOT_FOUND);

    }
}
*/
