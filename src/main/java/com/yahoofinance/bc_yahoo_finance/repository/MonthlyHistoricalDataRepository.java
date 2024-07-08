package com.yahoofinance.bc_yahoo_finance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.yahoofinance.bc_yahoo_finance.entity.MonthlyHistoricalData;

@Repository
public interface MonthlyHistoricalDataRepository extends JpaRepository<MonthlyHistoricalData, Long> {
    List<MonthlyHistoricalData> findBySymbol(String symbol);
}