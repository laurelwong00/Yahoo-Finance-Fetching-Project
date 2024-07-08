package com.yahoofinance.bc_yahoo_finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yahoofinance.bc_yahoo_finance.entity.WeeklyHistoricalData;

@Repository
public interface WeeklyHistoricalDataRepository extends JpaRepository<WeeklyHistoricalData, Long> {
    List<WeeklyHistoricalData> findBySymbol(String symbol);
}