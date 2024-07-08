package com.yahoofinance.bc_yahoo_finance.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yahoofinance.bc_yahoo_finance.entity.QuoteData;
import com.yahoofinance.bc_yahoo_finance.service.QuoteDataService;

@RestController
@RequestMapping("/v1/quoteData")
public class QuoteDataController {

  @Autowired
  private QuoteDataService quoteDataService;

  @GetMapping("/5min")
  public List<QuoteData> getFiveMinData() {
    LocalDate today = LocalDate.now();
    LocalTime startTime = LocalTime.of(9, 0);
    LocalTime endTime = LocalTime.of(16, 0);

    if (quoteDataService.isTradingDay(today)) {
      LocalDateTime startDateTime = LocalDateTime.of(today, startTime);
      LocalDateTime endDateTime = LocalDateTime.of(today, endTime);
      return quoteDataService.getQuoteData(startDateTime, endDateTime);
    } else {
      LocalDate lastTradingDate = quoteDataService.getLastTradingDate(today);
      LocalDateTime startDateTime = LocalDateTime.of(lastTradingDate, startTime);
      LocalDateTime endDateTime = LocalDateTime.of(lastTradingDate, endTime);
      return quoteDataService.getQuoteData(startDateTime, endDateTime);
    }
  }
}
