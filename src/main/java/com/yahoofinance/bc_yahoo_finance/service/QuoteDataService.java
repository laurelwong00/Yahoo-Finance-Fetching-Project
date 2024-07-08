package com.yahoofinance.bc_yahoo_finance.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.yahoofinance.bc_yahoo_finance.entity.QuoteData;

public interface QuoteDataService {

  public boolean isTradingDay(LocalDate today);

  public List<QuoteData> getQuoteData(LocalDateTime startDateTime, LocalDateTime endDateTime);

  public LocalDate getLastTradingDate(LocalDate today);
  
}
