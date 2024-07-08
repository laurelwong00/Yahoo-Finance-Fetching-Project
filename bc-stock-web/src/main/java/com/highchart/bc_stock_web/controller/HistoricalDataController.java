package com.highchart.bc_stock_web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.highchart.bc_stock_web.service.HistoricalDataService;

@RestController
public class HistoricalDataController {

    @Autowired
    private HistoricalDataService historicalDataService;

    @GetMapping("/api/historical/daily")
    public List<Map<String, Object>> getDailyHistoricalData(@RequestParam String symbol) {
        return historicalDataService.getDailyHistoricalData(symbol);
    }

    @GetMapping("/api/historical/weekly")
    public List<Map<String, Object>> getWeeklyHistoricalData(@RequestParam String symbol) {
        return historicalDataService.getWeeklyHistoricalData(symbol);
    }

    @GetMapping("/api/historical/monthly")
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
