package com.rohandev.cryptotracker.service;

import com.rohandev.cryptotracker.feignclient.CoinGeckoFeignClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

public class CoinGeckoServiceTest {
    CoinGeckoService coinGeckoService = new CoinGeckoService();
    CoinGeckoFeignClient coinGeckoFeignClientMock;

    @BeforeEach
    public void setup() {
        coinGeckoFeignClientMock = Mockito.mock(CoinGeckoFeignClient.class);
        coinGeckoService.setCoinGeckoFeignClient(coinGeckoFeignClientMock);

        Map<String, Object> expectedAPIResponse = new HashMap<>();
        Map<String, Object> priceData = new HashMap<>();
        priceData.put("usd", 19811.29d);
        expectedAPIResponse.put("bitcoin", priceData);

        Mockito.when(coinGeckoFeignClientMock.getCoinPrice("bitcoin", "usd")).thenReturn(expectedAPIResponse);
    }

    /*
    * GIVEN - Working coin gecko api
    * WHEN - Required parameters are supplied
    * THEN - Expected coin price should be returned
    * */
    @Test
    public void getCoinPriceTest() {
        coinGeckoService.setCoinId("bitcoin");
        coinGeckoService.setCurrency("usd");

        Assertions.assertTrue(coinGeckoService.getCoinPrice().isPresent());
    }

    /*
    * GIVEN - Working coin gecko api
    * WHEN - Incorrect parameters are supplied
    * THEN - Empty response should be returned
    * */
    @Test
    public void getCoinPriceTestWithIncorrectInputParams() {
        coinGeckoService.setCoinId("bitcoin_");
        coinGeckoService.setCurrency("usd_");

        Assertions.assertFalse(coinGeckoService.getCoinPrice().isPresent());
    }

    /*
     * GIVEN - Non-working coin gecko api
     * WHEN - Incorrect parameters are supplied
     * THEN - Empty response should be returned
     * */
    @Test
    public void getCoinPriceTestWithBrokenAPI() {
        Mockito.when(coinGeckoFeignClientMock.getCoinPrice("bitcoin", "usd")).thenReturn(null);
        Assertions.assertFalse(coinGeckoService.getCoinPrice().isPresent());
    }

}
