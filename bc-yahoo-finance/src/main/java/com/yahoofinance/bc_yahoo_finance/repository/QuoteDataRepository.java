package com.yahoofinance.bc_yahoo_finance.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.yahoofinance.bc_yahoo_finance.entity.QuoteData;

@Repository
public interface QuoteDataRepository extends JpaRepository<QuoteData, Long> {

  List<QuoteData> findByMarketTimeBetween(LocalDateTime start, LocalDateTime end);

  List<QuoteData> findByMarketTime(LocalDateTime timeStamp);

  @Query(value = "SELECT MAX(DATE(market_time)) FROM TSTOCK_QUOTE_YAHOO t WHERE t.symbol = :symbol", nativeQuery = true)
  LocalDate findMaxRegularMarketTime(@Param(value = "symbol") String symbol);

  @Query(value = "SELECT * FROM TSTOCK_QUOTE_YAHOO t WHERE t.symbol = :symbol AND DATE(t.market_time) = :myDate ORDER BY t.market_time", nativeQuery = true)
  List<QuoteData> findFiveMinDataBySymbolAndDate(
    @Param(value = "symbol") String symbol, 
    @Param(value = "myDate") LocalDate sysDate
  );

  @Query(value = "SELECT MAX(market_time) FROM TSTOCK_QUOTE_YAHOO t WHERE t.symbol = :symbol AND DATE(t.market_time) = :mydate", nativeQuery = true)
  LocalDateTime findMaxRegularMarketTimeBySymbolAndDate(@Param(value = "symbol") String symbol,
      @Param(value = "mydate") LocalDate date);
}
