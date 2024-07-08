package com.yahoofinance.bc_yahoo_finance.service.impl;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.yahoofinance.bc_yahoo_finance.entity.Stock;
import com.yahoofinance.bc_yahoo_finance.infra.RedisHelper;
import com.yahoofinance.bc_yahoo_finance.repository.StockRepository;
import com.yahoofinance.bc_yahoo_finance.service.StockService;

@Service
public class StockServiceImpl implements StockService {

  public static final String STOCK_LIST_KEY = "STOCK_LIST";
  
  @Autowired
  // private RedisTemplate<String, Object> redisTemplate;
  private RedisHelper redisHelper;

  @Autowired
  private StockRepository stockRepository;

  @Override
  public List<String> getAllStocks() throws JsonProcessingException {
    @SuppressWarnings("unchecked")
    List<String> stockList = (List<String>) redisHelper.get(STOCK_LIST_KEY, List.class);
    if (stockList != null) {
      return stockList;
    } else {
      List<Stock> stocks = stockRepository.findAll();
      stockList = stocks.stream().map(Stock::getSymbol).collect(Collectors.toList());
      redisHelper.set(STOCK_LIST_KEY, stockList, Duration.ofHours(24));
      return stockList;
    }
  }

  @Override
  public void clearStockSymbolsFromRedis() {
    redisHelper.delete(STOCK_LIST_KEY);
}
}

