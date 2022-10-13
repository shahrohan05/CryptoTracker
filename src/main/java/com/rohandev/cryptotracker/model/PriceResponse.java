package com.rohandev.cryptotracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PriceResponse {

    private String url;
    private String next;
    private int count;
    private List<CoinPrice> data;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<CoinPrice> getData() {
        return data;
    }

    public void setData(List<CoinPrice> data) {
        this.data = data;
    }
}
