## Introduction-
The web service handles request to buy or sell a particular amount of a currency (the base currency) with another currency (the quote currency). The service uses the orderbook to determine the best price the user would be able to get for that request by executing trades on GDAX.

The web service has only one endpoint that receives JSON requests and responds with JSON. If there are any errors processing the request, it responds with a JSON object including an error message.


## Technology Stack -
- Server - Tomcat 7
- Web application - Spring

## Steps to deploy the jar/war -
1. Build the war file
```
mvn clean install
```
Extract the gdax-service.zip file.
```
unzip gdax-service.zip
```
2. Execute the following commands:
```
cd gdax-service
 ./startScript.sh
 ```
3. Now, our service is deployed on tomcat.
4. To ensure the proper workings of the service, execute the following command. If it
returns a valid response, then the service is deployed successfully.
Instead of curl, we can use other REST client like Postman to test the application.
```
curl -d '{"action": "buy","base_currency": "BTC","quote_currency": "USD",
"amount":"1.00000000"}' \
-H "Content-Type: application/json" \
-X POST http://localhost:8080/gdax/quotes
```

### Examples-
Following are few requests handled by our api and the responses returned:

**Case 1** Valid Request
```
{
"action": "sell",
"base_currency": "BTC",
"quote_currency": "USD",
"amount": "10"
}
```
```
{
"price": "8825.25853411",
"total": "88252.58534115",
"currency": "USD"
}
```
**Case 2** Action Name is invalid.
```
{
"action": "foo",
"base_currency": "BTC",
"quote_currency": "USD",
"amount": "10"
}
```
```
{
"code": "400",
"message": "action field is invalid. Action can only be Buy/Sell"
}
```
**Case 3** One of the currency fields is empty.
```
{
"action": "sell",
"base_currency": "BTC",
"quote_currency": "",
"amount": "10"
}
```

```
{
"code": "400",
"message": "quoteCurrency field is invalid. Both quote and base
currencies should be non empty"
}
```
**Case 4** Amount field is not a valid number.
```
{
"action": "Buy",
"base_currency": "BTC",
"quote_currency": "USD",
"amount": "10dss"
}
```
```
{
"code": "400",
"message": "amount field is invalid. Amount field is not a valid
floating point number"
}
```
**Case 5** Amount field is 0.
```
{
"action": "Buy",
"base_currency": "BTC",
"quote_currency": "USD",
"amount": "0"
}
```
```
{
"code": "400",
"message": "amount field is invalid. Amount field should be non
zero"
}
```
**Case 6** Currency pair is not a valid product id.
```
{
"action": "Buy",
"base_currency": "BTC",
"quote_currency": "INR",
"amount": "10"
}
```
```
{
"code": "400",
"message": "Invalid Currency Pair: INR-BTC"
}
```
**Case 7** Requested amount exceeds the sum of sizes present in top 50 bids/asks
```
{
"action": "Sell",
"base_currency": "BTC",
"quote_currency": "ETH",
"amount": "10000000"
}
```
```
{
"code": "500",
"message": "The amount requested is too large to be processed.
Please try again with smaller amount."
}
```
**Case 8** Gdax orderbook api is not working
```
{
"code": "503",
"message": "Unexpected Error"
}
```


