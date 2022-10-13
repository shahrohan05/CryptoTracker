package com.rohandev.cryptotracker.repository;

import com.rohandev.cryptotracker.model.CoinPrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Repository
public interface CoinPriceRepository extends CrudRepository<CoinPrice, Long> {

    Page<CoinPrice> findAllByTimestampBetween(Instant fromDate, Instant toDate, Pageable pageable);
    // pageable does not support offset, requires page only, JPQL does not support offset either, so must use native query

    long countByTimestampBetween(Instant fromDate, Instant toDate);

    @Query(value = "select * from coin_price where CAST(timestamp as date) = ?1 limit ?2 offset ?3", nativeQuery = true)
    List<CoinPrice> getCoinPricesForGivenDate(String date, int limit, int offset);

}