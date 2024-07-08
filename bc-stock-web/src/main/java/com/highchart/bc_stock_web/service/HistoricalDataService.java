package com.highchart.bc_stock_web.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HistoricalDataService {

    @Autowired
    private RestTemplate restTemplate;

    public List<Map<String, Object>> getDailyHistoricalData(String symbol) {
        String url = String.format("http://localhost:8083/api/historical/daily?symbol=%s", symbol);
        return restTemplate.getForObject(url, List.class);
    }

    public List<Map<String, Object>> getWeeklyHistoricalData(String symbol) {
        String url = String.format("http://localhost:8083/api/historical/weekly?symbol=%s", symbol);
        return restTemplate.getForObject(url, List.class);
    }

    public List<Map<String, Object>> getMonthlyHistoricalData(String symbol) {
        String url = String.format("http://localhost:8083/api/historical/monthly?symbol=%s", symbol);
        return restTemplate.getForObject(url, List.class);
    }

    public List<Map<String, Object>> getDailyMovingAverageData(String symbol, int period) {
        String url = String.format("http://localhost:8083/api/historical/ma/daily?symbol=%s&period=%s", symbol, period);
        return restTemplate.getForObject(url, List.class);
    }

    public List<Map<String, Object>> getWeeklyMovingAverageData(String symbol, int period) {
        String url = String.format("http://localhost:8083/api/historical/ma/weekly?symbol=%s&period=%s", symbol, period);
        return restTemplate.getForObject(url, List.class);
    }

    public List<Map<String, Object>> getMonthlyMovingAverageData(String symbol, int period) {
        String url = String.format("http://localhost:8083/api/historical/ma/monthly?symbol=%s&period=%s", symbol, period);
        return restTemplate.getForObject(url, List.class);
    }
}
