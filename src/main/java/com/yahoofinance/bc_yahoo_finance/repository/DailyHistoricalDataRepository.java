package com.yahoofinance.bc_yahoo_finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yahoofinance.bc_yahoo_finance.entity.DailyHistoricalData;

@Repository
public interface DailyHistoricalDataRepository extends JpaRepository<DailyHistoricalData, Long> {
    List<DailyHistoricalData> findBySymbol(String symbol);
}