package com.rohandev.cryptotracker.task;

import com.rohandev.cryptotracker.model.CoinPrice;
import com.rohandev.cryptotracker.repository.CoinPriceRepository;
import com.rohandev.cryptotracker.service.CoinGeckoService;
import com.rohandev.cryptotracker.service.EmailNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class ScheduledAPICallTask {

    Logger logger = LoggerFactory.getLogger(ScheduledAPICallTask.class);

    @Autowired
    CoinGeckoService coinGeckoService;

    @Autowired
    CoinPriceRepository coinPriceRepository;

    @Autowired
    EmailNotificationService emailNotificationService;

    @Value("${crypto-tracker.coin-id}")
    private String coinId;


    /**
     * Scheduled method to be called with a fixed delay of 30 seconds between each execution.
     */
    @Scheduled(fixedDelay = 30000)
    public void scheduledAPICallTask() {
        logger.info("starting scheduled task!");
        Optional<Double> price = coinGeckoService.getCoinPrice();
        logger.info("PRICE - " + price);

        if (price.isPresent()) {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(() -> {
                // executing send email logic on a separate thread, so the scheduler thread does not have to wait for it
                emailNotificationService.sendEmailNotification(price.get());
            });

            CoinPrice coinPrice = new CoinPrice(coinId, price.get(), Instant.now());
            coinPriceRepository.save(coinPrice);
        }


    }


}
