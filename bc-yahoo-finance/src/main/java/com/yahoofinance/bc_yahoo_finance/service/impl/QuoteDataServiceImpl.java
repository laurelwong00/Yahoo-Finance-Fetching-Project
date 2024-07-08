package com.yahoofinance.bc_yahoo_finance.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yahoofinance.bc_yahoo_finance.entity.QuoteData;
import com.yahoofinance.bc_yahoo_finance.repository.QuoteDataRepository;
import com.yahoofinance.bc_yahoo_finance.service.QuoteDataService;

@Service
public class QuoteDataServiceImpl implements QuoteDataService {

  @Autowired
  private QuoteDataRepository quoteDataRepository;

  public boolean isTradingDay(LocalDate date) {
    return !date.getDayOfWeek().name().equals("SATURDAY") && !date.getDayOfWeek().name().equals("SUNDAY");
  }

  public List<QuoteData> getQuoteData(LocalDateTime startDateTime, LocalDateTime endDateTime) {
    return quoteDataRepository.findByMarketTimeBetween(startDateTime, endDateTime);
  }

  public LocalDate getLastTradingDate(LocalDate currentDate) {
    LocalDate date = currentDate;
    while (!isTradingDay(date)) {
      date = date.minusDays(1);
    }
    return date;
  }
}
