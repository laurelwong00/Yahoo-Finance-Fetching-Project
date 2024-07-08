package com.yahoofinance.bc_yahoo_finance.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.yahoofinance.bc_yahoo_finance.entity.DailyHistoricalData;
import com.yahoofinance.bc_yahoo_finance.entity.MonthlyHistoricalData;
import com.yahoofinance.bc_yahoo_finance.entity.WeeklyHistoricalData;
import com.yahoofinance.bc_yahoo_finance.repository.DailyHistoricalDataRepository;
import com.yahoofinance.bc_yahoo_finance.repository.MonthlyHistoricalDataRepository;
import com.yahoofinance.bc_yahoo_finance.repository.WeeklyHistoricalDataRepository;

@Service
public class HistoricalDataService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DailyHistoricalDataRepository dailyHistoricalDataRepository;

    @Autowired
    private WeeklyHistoricalDataRepository weeklyHistoricalDataRepository;

    @Autowired
    private MonthlyHistoricalDataRepository monthlyHistoricalDataRepository;
    
    public void fetchAndSaveDailyData(String symbol) {
        String url = String.format(
                "https://query1.finance.yahoo.com/v8/finance/chart/%s?range=1mo&interval=1d",
                symbol);
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        Map<String, Object> chart = (Map<String, Object>) response.get("chart");
        List<Object> result = (List<Object>) chart.get("result");
        Map<String, Object> data = (Map<String, Object>) result.get(0);
        Map<String, Object> indicators = (Map<String, Object>) data.get("indicators");
        Map<String, Object> quote = (Map<String, Object>) ((List<Object>) indicators.get("quote")).get(0);
        List<Double> opens = (List<Double>) quote.get("open");
        List<Double> highs = (List<Double>) quote.get("high");
        List<Double> lows = (List<Double>) quote.get("low");
        List<Double> closes = (List<Double>) quote.get("close");
        List<Integer> volumes = (List<Integer>) quote.get("volume");
        List<Integer> timestamps = (List<Integer>) data.get("timestamp");

        List<LocalDate> dates = timestamps.stream()
                .map(timestamp -> Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDate())
                .collect(Collectors.toList());

        DailyHistoricalData dailyhistoricalData = DailyHistoricalData.builder()
                .symbol(symbol)
                .dates(dates)
                .opens(opens)
                .highs(highs)
                .lows(lows)
                .closes(closes)
                .volumes(volumes)
                .build();
        dailyHistoricalDataRepository.deleteAll();
        dailyHistoricalDataRepository.save(dailyhistoricalData);
    }

    public void fetchAndSaveWeeklyData(String symbol) {
        String url = String.format(
                "https://query1.finance.yahoo.com/v8/finance/chart/%s?range=1y&interval=1wk",
                symbol);
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        Map<String, Object> chart = (Map<String, Object>) response.get("chart");
        List<Object> result = (List<Object>) chart.get("result");
        Map<String, Object> data = (Map<String, Object>) result.get(0);
        Map<String, Object> indicators = (Map<String, Object>) data.get("indicators");
        Map<String, Object> quote = (Map<String, Object>) ((List<Object>) indicators.get("quote")).get(0);
        List<Double> opens = (List<Double>) quote.get("open");
        List<Double> highs = (List<Double>) quote.get("high");
        List<Double> lows = (List<Double>) quote.get("low");
        List<Double> closes = (List<Double>) quote.get("close");
        List<Integer> volumes = (List<Integer>) quote.get("volume");
        List<Integer> timestamps = (List<Integer>) data.get("timestamp");

        List<LocalDate> dates = timestamps.stream()
                .map(timestamp -> Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDate())
                .collect(Collectors.toList());

        WeeklyHistoricalData weeklyHistoricalData = WeeklyHistoricalData.builder()
                .symbol(symbol)
                .dates(dates)
                .opens(opens)
                .highs(highs)
                .lows(lows)
                .closes(closes)
                .volumes(volumes)
                .build();
        weeklyHistoricalDataRepository.deleteAll();
        weeklyHistoricalDataRepository.save(weeklyHistoricalData);
    }

    public void fetchAndSaveMonthlyData(String symbol) {
        String url = String.format(
                "https://query1.finance.yahoo.com/v8/finance/chart/%s?range=5y&interval=1mo",
                symbol);
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        Map<String, Object> chart = (Map<String, Object>) response.get("chart");
        List<Object> result = (List<Object>) chart.get("result");
        Map<String, Object> data = (Map<String, Object>) result.get(0);
        Map<String, Object> indicators = (Map<String, Object>) data.get("indicators");
        Map<String, Object> quote = (Map<String, Object>) ((List<Object>) indicators.get("quote")).get(0);
        List<Double> opens = (List<Double>) quote.get("open");
        List<Double> highs = (List<Double>) quote.get("high");
        List<Double> lows = (List<Double>) quote.get("low");
        List<Double> closes = (List<Double>) quote.get("close");
        List<Integer> volumes = (List<Integer>) quote.get("volume");
        List<Integer> timestamps = (List<Integer>) data.get("timestamp");

        List<LocalDate> dates = timestamps.stream()
                .map(timestamp -> Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDate())
                .collect(Collectors.toList());

        MonthlyHistoricalData monthlyHistoricalData = MonthlyHistoricalData.builder()
                .symbol(symbol)
                .dates(dates)
                .opens(opens)
                .highs(highs)
                .lows(lows)
                .closes(closes)
                .volumes(volumes)
                .build();
        monthlyHistoricalDataRepository.deleteAll();
        monthlyHistoricalDataRepository.save(monthlyHistoricalData);
    }

    public List<Map<String, Object>> getDailyHistoricalData(String symbol) {
        List<DailyHistoricalData> data = dailyHistoricalDataRepository.findBySymbol(symbol);

        if(data == null || data.isEmpty()) {
            fetchAndSaveDailyData(symbol);  
            data = dailyHistoricalDataRepository.findBySymbol(symbol);
        }

        return data.stream().flatMap(d -> {
            List<LocalDate> dates = d.getDates();
            List<Double> opens = d.getOpens();
            List<Double> highs = d.getHighs();
            List<Double> lows = d.getLows();
            List<Double> closes = d.getCloses();
            List<Integer> volumes = d.getVolumes();

            System.out.println(dates.size());

            return IntStream.range(0, dates.size()-1)
                    .mapToObj(i -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("timestamp", dates.get(i).atStartOfDay(ZoneId.systemDefault()).toEpochSecond());
                        map.put("open", opens.get(i));
                        map.put("high", highs.get(i));
                        map.put("low", lows.get(i));
                        map.put("close", closes.get(i));
                        map.put("volume", volumes.get(i));
                        return map;
                    });
        }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> getWeeklyHistoricalData(String symbol) {
        List<WeeklyHistoricalData> data = weeklyHistoricalDataRepository.findBySymbol(symbol);

        if(data== null || data.isEmpty()) {
            fetchAndSaveWeeklyData(symbol);
            data = weeklyHistoricalDataRepository.findBySymbol(symbol);
        }

        return data.stream().flatMap(d -> {
            List<LocalDate> dates = d.getDates();
            List<Double> opens = d.getOpens();
            List<Double> highs = d.getHighs();
            List<Double> lows = d.getLows();
            List<Double> closes = d.getCloses();
            List<Integer> volumes = d.getVolumes();

            return IntStream.range(0, dates.size()-1)
                    .mapToObj(i -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("timestamp", dates.get(i).atStartOfDay(ZoneId.systemDefault()).toEpochSecond());
                        map.put("open", opens.get(i));
                        map.put("high", highs.get(i));
                        map.put("low", lows.get(i));
                        map.put("close", closes.get(i));
                        map.put("volume", volumes.get(i));
                        return map;
                    });
        }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> getMonthlyHistoricalData(String symbol) {
        List<MonthlyHistoricalData> data = monthlyHistoricalDataRepository.findBySymbol(symbol);

        if(data== null || data.isEmpty()) {
            fetchAndSaveMonthlyData(symbol);
            data = monthlyHistoricalDataRepository.findBySymbol(symbol);
        }

        return data.stream().flatMap(d -> {
            List<LocalDate> dates = d.getDates();
            List<Double> opens = d.getOpens();
            List<Double> highs = d.getHighs();
            List<Double> lows = d.getLows();
            List<Double> closes = d.getCloses();
            List<Integer> volumes = d.getVolumes();

            return IntStream.range(0, dates.size()-1)
                    .mapToObj(i -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("timestamp", dates.get(i).atStartOfDay(ZoneId.systemDefault()).toEpochSecond());
                        map.put("open", opens.get(i));
                        map.put("high", highs.get(i));
                        map.put("low", lows.get(i));
                        map.put("close", closes.get(i));
                        map.put("volume", volumes.get(i));
                        return map;
                    });
        }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> calculateMovingAverage(List<Double> prices, List<LocalDate> dates, int period) {
        List<Map<String, Object>> maData = new ArrayList<>();

        for (int i = period - 1; i < prices.size(); i++) {
            double sum = 0.0;
            for (int j = i; j > i - period; j--) {
                sum += prices.get(j);
            }
            double movingAverage = sum / period;
            Map<String, Object> maPoint = new HashMap<>();
            maPoint.put("timestamp", dates.get(i).atStartOfDay(ZoneId.systemDefault()).toEpochSecond());
            maPoint.put("movingAverage", movingAverage);
            maData.add(maPoint);
        }

        return maData;
    }

    public List<Map<String, Object>> getDailyMovingAverageData(String symbol, int period) {
        List<DailyHistoricalData> data = dailyHistoricalDataRepository.findBySymbol(symbol);

        if(data == null || data.isEmpty()) {
            fetchAndSaveDailyData(symbol);  
            data = dailyHistoricalDataRepository.findBySymbol(symbol);
        }

        List<Double> closes = data.get(0).getCloses();  // Assuming only one entry per symbol
        List<LocalDate> dates = data.get(0).getDates();

        return calculateMovingAverage(closes, dates, period);
    }

    public List<Map<String, Object>> getWeeklyMovingAverageData(String symbol, int period) {
        List<WeeklyHistoricalData> data = weeklyHistoricalDataRepository.findBySymbol(symbol);

        if(data == null || data.isEmpty()) {
            fetchAndSaveWeeklyData(symbol);
            data = weeklyHistoricalDataRepository.findBySymbol(symbol);
        }

        List<Double> closes = data.get(0).getCloses();  // Assuming only one entry per symbol
        List<LocalDate> dates = data.get(0).getDates();

        return calculateMovingAverage(closes, dates, period);
    }

    public List<Map<String, Object>> getMonthlyMovingAverageData(String symbol, int period) {
        List<MonthlyHistoricalData> data = monthlyHistoricalDataRepository.findBySymbol(symbol);

        if(data == null || data.isEmpty()) {
            fetchAndSaveMonthlyData(symbol);
            data = monthlyHistoricalDataRepository.findBySymbol(symbol);
        }

        List<Double> closes = data.get(0).getCloses();  // Assuming only one entry per symbol
        List<LocalDate> dates = data.get(0).getDates();

        return calculateMovingAverage(closes, dates, period);
    }
}
