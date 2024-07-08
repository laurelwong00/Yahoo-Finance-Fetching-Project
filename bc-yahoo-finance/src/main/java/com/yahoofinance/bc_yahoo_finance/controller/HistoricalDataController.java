package com.yahoofinance.bc_yahoo_finance.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yahoofinance.bc_yahoo_finance.service.HistoricalDataService;

@RestController
@RequestMapping(value = "/api/historical")
public class HistoricalDataController {

  @Autowired
  HistoricalDataService historicalDataService;

  @PostMapping("/daily")
  public void fetchAndSaveDailyData(@RequestParam String symbol,
      @RequestParam long period1,
      @RequestParam long period2,
      @RequestParam String crumb) {
    historicalDataService.fetchAndSaveDailyData(symbol);
  }

  @PostMapping("/weekly")
  public void fetchAndSaveWeeklyData(@RequestParam String symbol,
      @RequestParam long period1,
      @RequestParam long period2,
      @RequestParam String crumb) {
    historicalDataService.fetchAndSaveWeeklyData(symbol);
  }

  @PostMapping("/monthly")
  public void fetchAndSaveMonthlyData(@RequestParam String symbol,
      @RequestParam long period1,
      @RequestParam long period2,
      @RequestParam String crumb) {
    historicalDataService.fetchAndSaveMonthlyData(symbol);
  }

  @GetMapping("/daily")
  public List<Map<String, Object>> getDailyHistoricalData(@RequestParam String symbol) {
    return historicalDataService.getDailyHistoricalData(symbol);
  }

  @GetMapping("/weekly")
  public List<Map<String, Object>> getWeeklyHistoricalData(@RequestParam String symbol) {
    return historicalDataService.getWeeklyHistoricalData(symbol);
  }

  @GetMapping("/monthly")
  public List<Map<String, Object>> getMonthlyHistoricalData(@RequestParam String symbol) {
    return historicalDataService.getMonthlyHistoricalData(symbol);
  }

  @GetMapping("/ma/daily")
  public List<Map<String, Object>> getDailyMovingAverageData(@RequestParam String symbol, @RequestParam int period) {
      return historicalDataService.getDailyMovingAverageData(symbol, period);
  }

  @GetMapping("/ma/weekly")
  public List<Map<String, Object>> getWeeklyMovingAverageData(@RequestParam String symbol, @RequestParam int period) {
      return historicalDataService.getWeeklyMovingAverageData(symbol, period);
  }

  @GetMapping("/ma/monthly")
  public List<Map<String, Object>> getMonthlyMovingAverageData(@RequestParam String symbol, @RequestParam int period) {
      return historicalDataService.getMonthlyMovingAverageData(symbol, period);
  }

}
