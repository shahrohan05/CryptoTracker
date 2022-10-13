package com.rohandev.cryptotracker.service;

import com.rohandev.cryptotracker.model.CoinPrice;
import com.rohandev.cryptotracker.model.PriceResponse;
import com.rohandev.cryptotracker.repository.CoinPriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class CoinPriceService {

    Logger logger = LoggerFactory.getLogger(CoinPriceService.class);

    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final String SQL_DATE_FORMAT = "yyyy-MM-dd";

    private static final String PARAMS = "<%s?date=%s&offset=%d&limit=%d>";

    @Autowired
    CoinPriceRepository coinPriceRepository;

    public PriceResponse getCoinPrices(String dateStr, int offset, int limit, String requestURI) {
        PriceResponse response = new PriceResponse();
        try {
            LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(DATE_FORMAT));
            Instant fromDate = date.atTime(LocalTime.MIN).toInstant(ZoneOffset.UTC); // start of the day
            Instant toDate = date.atTime(LocalTime.MAX).toInstant(ZoneOffset.UTC); // end of the day

            //Page<CoinPrice> data = coinPriceRepository.findAllByTimestampBetween(fromDate, toDate, PageRequest.of(offset, limit));

            long count = coinPriceRepository.countByTimestampBetween(fromDate, toDate);
            List<CoinPrice> data = coinPriceRepository.getCoinPricesForGivenDate(DateTimeFormatter.ofPattern(SQL_DATE_FORMAT).format(date), limit, offset);

            response.setCount(count);
            response.setUrl(String.format(PARAMS, requestURI, dateStr, offset, limit));
            response.setNext(String.format(PARAMS, requestURI, dateStr, offset + limit, limit));
            response.setData(data);

        } catch (Exception e) {
            logger.error("Error getting coin prices from DB!", e);
        }
        return response;
    }

}
