package com.highchart.bc_stock_web.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.highchart.bc_stock_web.model.BidAskData;
import com.highchart.bc_stock_web.model.MovingAverageData;
import com.highchart.bc_stock_web.model.QuoteData;

@Service
public class QuoteService {

  @Autowired
  RestTemplate restTemplate;

  public List<QuoteData> getFiveMinQuoteData(String symbol) {
    List<QuoteData> quotes = new ArrayList<QuoteData>();

    String url = "http://localhost:8083/api/quote/5min-data?symbol=" + symbol;
    ArrayList<Object> response = (ArrayList<Object>) restTemplate.getForObject(url, List.class);

    for (Object obj : response) {

      Map<String, Object> stockInfo = (Map<String, Object>) obj;

      long regularMarketUnix = ((Number) stockInfo.get("regularMarketUnix")).longValue();
      LocalDateTime marketTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(regularMarketUnix),
          ZoneId.systemDefault());

      QuoteData quoteData = new QuoteData(marketTime, (double) stockInfo.get("regularMarketPrice"));
      quotes.add(quoteData);
    }
    return quotes;
  }

  public List<MovingAverageData> getMovingAverageData(String symbol) {
    List<MovingAverageData> quotes = new ArrayList<MovingAverageData>();

    String url = "http://localhost:8083/api/quote/ma-data?symbol=" + symbol;
    ArrayList<Object> response = (ArrayList<Object>) restTemplate.getForObject(url, List.class);

    for (Object obj : response) {

      Map<String, Object> stockInfo = (Map<String, Object>) obj;

      long regularMarketUnix = ((Number) stockInfo.get("timestamp")).longValue();
      LocalDateTime marketTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(regularMarketUnix),
          ZoneId.systemDefault());

      MovingAverageData quoteData = new MovingAverageData(marketTime, (double) stockInfo.get("movingAverage"));
      quotes.add(quoteData);
    }
    return quotes;
  }

  public BidAskData getBidAskData(String symbol) {
    String url = "http://localhost:8083/api/quote/5min-data?symbol=" + symbol;
    ArrayList<Object> response = (ArrayList<Object>) restTemplate.getForObject(url, List.class);

      Map<String, Object> stockInfo = (Map<String, Object>) response.get(response.size()-1);
      BidAskData bidAskData = BidAskData.builder()
                              .askPrice((double) stockInfo.get("ask"))
                              .askVolume((int) stockInfo.get("askSize"))
                              .bidPrice((double) stockInfo.get("bid"))
                              .bidVolume((int) stockInfo.get("bidSize"))
                              .build();
      return bidAskData;
    } 
  }
