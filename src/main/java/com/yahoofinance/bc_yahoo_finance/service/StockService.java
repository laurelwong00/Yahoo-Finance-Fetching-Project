package com.yahoofinance.bc_yahoo_finance.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;


public interface StockService {
  List<String> getAllStocks() throws JsonProcessingException;

  void clearStockSymbolsFromRedis();

}
