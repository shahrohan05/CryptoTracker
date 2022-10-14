package com.rohandev.cryptotracker.service;

import com.rohandev.cryptotracker.feignclient.CoinGeckoFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class CoinGeckoService {

    @Value("${crypto-tracker.coin-id}")
    private String coinId;

    public void setCoinId(String coinId) {
        this.coinId = coinId;
    }

    @Value("${crypto-tracker.currency}")
    private String currency;

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Autowired
    CoinGeckoFeignClient coinGeckoFeignClient;

    public void setCoinGeckoFeignClient(CoinGeckoFeignClient coinGeckoFeignClient) {
        this.coinGeckoFeignClient = coinGeckoFeignClient;
    }

    /**
     * Get coin price from gecko api, based on the coin id and currency defined in the following properties -
     * crypto-tracker.coin-id,
     * crypto-tracker.currency
     *
     * @return @{@link Map} containing coin gecko api response
     */
    @SuppressWarnings("unchecked")
    public Optional<Double> getCoinPrice() {
        Optional<Double> response = Optional.empty();
        Map<String, Object> apiResponse = coinGeckoFeignClient.getCoinPrice(coinId, currency);
        if (!apiResponse.isEmpty() && apiResponse.get(coinId) != null) {
            Map<String, Object> prices = (Map<String, Object>) apiResponse.get(coinId);
            if (prices.get(currency) != null) {
                response = Optional.of((Double) prices.get(currency));
            }
        }
        return response;
    }

}
