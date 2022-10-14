<h2> Cryptocurrency tracker app </h2>

Java Springboot based REST API app that continuously monitors the price of Bitcoin using CoinGecko API and alerts a given email when the price either goes above or below the given limits.

<h4>Steps to run - </h4> 

1. Add .env file at the root of the project having following required parameters -  
   ```
   mail_host = <smtp_host>
   mail_port = <smtp_port>
   mail_username = <smtp_username>
   mail_password = <smtp_password>
   send_to_email = <email>
   crypto_price_min = <expected_min_price>
   crypto_price_max = <expected_max_price>
   ```
2. Run ```./gradlew build``` from project's directory 
3. Run ```docker-compose up```
4. Hit the following endpoint to get found prices from DB - 
   <br>```curl --location --request GET 'http://localhost:8080/api/prices/btc?date=<DATE>&offset=0&limit=100'```
   <br>**[OR]**
   <br> Use this [postman collection](https://github.com/shahrohan05/CryptoTracker/blob/master/CryptoTracker.postman_collection.json)
