package com.anjalichadha.controller;

import com.anjalichadha.exceptionHandling.GdaxException;
import com.anjalichadha.exceptionHandling.InvalidCurrencyPairException;
import com.anjalichadha.exceptionHandling.InvalidRequestException;
import com.anjalichadha.model.TradeAction;
import com.anjalichadha.model.TradeRequest;
import com.anjalichadha.model.TradeResponse;
import com.anjalichadha.service.ITradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.math.BigDecimal;

/**
 * Controller class, rest endpoints are created here
 */
@Controller
public class GdaxTradingController {

    @Autowired
    private ITradeService tradeService;

    private Logger logger = LoggerFactory.getLogger(GdaxTradingController.class);

    @RequestMapping(value = "/quotes",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"})
    @ResponseBody
    public ResponseEntity getBestPrice(@Valid @RequestBody TradeRequest request) throws InvalidRequestException {
        try {
            isValidRequest(request);
            logger.debug("Valid User Request: " + request);
            TradeResponse response = tradeService.getQuotes(request);
            logger.debug("Trade Response: " + response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (InvalidRequestException e) {
            logger.debug("Invalid User Request: " + request);
            return new ResponseEntity<>(e.getErrorResource(), HttpStatus.BAD_REQUEST);
        } catch (GdaxException e) {
            return new ResponseEntity<> (e.getErrorResource(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    /**
     * @param req : The JSON Request as a POJO
     * @return false if the input json is invalid or doesn't meet the standards.
     * 1) None of the input should be empty.
     * 2) Amount should be a valid floating point number and it can't be zero
     * 3) Action can be either buy or sell.
     * If any of the above conditions doesn't meet, return false.
     * @throws InvalidRequestException
     */
    private void isValidRequest(TradeRequest req) throws InvalidRequestException {
        String action = req.getAction();
        String amount = req.getAmount();
        String baseCurrency = req.getBaseCurrency();
        String quoteCurrency = req.getQuoteCurrency();

        if (!TradeAction.BUY.toString().equalsIgnoreCase(action) && !TradeAction.SELL.toString().equalsIgnoreCase(action)) {
            logger.error("Invalid action " + action);
            throw new InvalidRequestException("action", "Action can only be Buy/Sell");
        }
        if (baseCurrency.isEmpty()) {
            logger.error("Base currency should be non empty string");
            throw new InvalidRequestException("baseCurrency", "Both quote and base currencies should be non empty");
        }

        if (quoteCurrency.isEmpty()) {
            logger.error("Quote currency should be non empty string");
            throw new InvalidRequestException("quoteCurrency", "Both quote and base currencies should be non empty");
        }

        BigDecimal amountBD;
        try {
            amountBD = new BigDecimal(amount);
        } catch (NumberFormatException e) {
            logger.error("amount field should be a floating point number");
            throw new InvalidRequestException("amount", "Amount field is not a valid floating point number");
        }
        if (amountBD.equals(BigDecimal.ZERO)) {
            logger.error("amount field should be non-zero.");
            throw new InvalidRequestException("amount", "Amount field should be non zero");
        }
        if(amountBD.signum() < 0) {
            logger.error("amount field should be positive");
            throw new InvalidRequestException("amount", "Amount field should be positive");
        }
        logger.debug("User input is valid");
    }
}
