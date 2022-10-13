package com.rohandev.cryptotracker.model;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "coin_price")
public class CoinPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "coin")
    private String coin;

    @Column(name = "price")
    private Double price;

    @Column(name = "timestamp")
    private Instant timestamp;


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public void setId(Long id) {
        this.id = id;
    }


}