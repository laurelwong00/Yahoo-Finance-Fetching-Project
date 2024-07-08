package com.highchart.bc_stock_web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.highchart.bc_stock_web.model.BidAskData;
import com.highchart.bc_stock_web.model.MovingAverageData;
import com.highchart.bc_stock_web.model.QuoteData;
import com.highchart.bc_stock_web.service.QuoteService;

@RestController
public class QuoteController {

  @Autowired
  private QuoteService quoteService;

  @GetMapping(value = "/quote")
  public List<QuoteData> getQuote(@RequestParam(defaultValue = "0388.HK") String symbol) {
    return quoteService.getFiveMinQuoteData(symbol);
  }

  @GetMapping(value = "/ma")
  public List<MovingAverageData> getMovingAverage(@RequestParam(defaultValue = "0388.HK") String symbol) {
    return quoteService.getMovingAverageData(symbol);
  }

  @GetMapping(value = "/bidAsk")
  public BidAskData getBidAskData(@RequestParam(defaultValue = "0388.HK") String symbol) {
    return quoteService.getBidAskData(symbol);
  }

  
}
