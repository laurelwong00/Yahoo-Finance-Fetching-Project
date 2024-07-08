package com.yahoofinance.bc_yahoo_finance.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yahoofinance.bc_yahoo_finance.service.MovingAverageService;
import com.yahoofinance.bc_yahoo_finance.service.ServiceOperator;
import com.yahoofinance.bc_yahoo_finance.service.StockService;
import com.yahoofinance.bc_yahoo_finance.service.SysDateService;

@Component
public class AppScheduler {

  @Autowired
  private StockService stockService;

  @Autowired
  private SysDateService SysDateService;

  @Autowired
  private ServiceOperator serviceOperator;

  @Autowired
  private MovingAverageService movingAverageService;

  @Scheduled(cron = "*/5 * * * * *")
  public void getQuoteData() throws JsonProcessingException {
    List<String> stocks = stockService.getAllStocks();
    serviceOperator.fetchAndSaveStockQuotes(stocks);
  }

  @Scheduled(cron = "0 55 8 * * ?")
  public void clearSystemDates() {
    SysDateService.clearAllSystemDatesCache();
  }
}
