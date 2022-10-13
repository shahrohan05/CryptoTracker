package com.rohandev.cryptotracker.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Map;

@FeignClient(value = "coingeckofeign", url = "${crypto-tracker.coin-gecko.base-url}", fallback = CoinGeckoFeignClient.CoinGeckoFeignClientFallback.class)
public interface CoinGeckoFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/simple/price")
    Map<String, Object> getCoinPrice(@RequestParam("ids") String ids, @RequestParam("vs_currencies") String currencies);

    @Component
    class CoinGeckoFeignClientFallback implements CoinGeckoFeignClient {

        @Override
        public Map<String, Object> getCoinPrice(String ids, String currencies) {
            return Collections.emptyMap();
        }
    }
}
