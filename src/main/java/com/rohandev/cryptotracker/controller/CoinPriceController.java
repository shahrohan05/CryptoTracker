package com.rohandev.cryptotracker.controller;

import com.rohandev.cryptotracker.service.CoinGeckoService;
import com.rohandev.cryptotracker.service.CoinPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/prices")
public class CoinPriceController {

    @Autowired
    CoinPriceService coinPriceService;

    @GetMapping("btc")
    public Object getBitcoinPrices(@RequestParam("date") String date, @RequestParam int offset, @RequestParam int limit, HttpServletRequest request) {
        return coinPriceService.getCoinPrices(date, offset, limit, ServletUriComponentsBuilder.fromRequestUri(request).build().toUriString());
    }
}
