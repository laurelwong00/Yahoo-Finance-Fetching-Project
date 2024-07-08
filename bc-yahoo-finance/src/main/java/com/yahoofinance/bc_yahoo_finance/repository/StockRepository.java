package com.yahoofinance.bc_yahoo_finance.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yahoofinance.bc_yahoo_finance.entity.Stock;

public interface StockRepository extends JpaRepository<Stock, Long>{
  
}
