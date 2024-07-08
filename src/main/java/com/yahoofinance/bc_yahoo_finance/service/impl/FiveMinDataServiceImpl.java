package com.yahoofinance.bc_yahoo_finance.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yahoofinance.bc_yahoo_finance.entity.QuoteData;
import com.yahoofinance.bc_yahoo_finance.repository.QuoteDataRepository;

@Service
public class FiveMinDataServiceImpl {

  @Autowired
  private StringRedisTemplate redisTemplate;

  @Autowired
  private QuoteDataRepository quoteDataRepository;

  @Autowired
  private ObjectMapper objectMapper;

  private static final String REDIS_KEY_PREFIX = "5MINS-";

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public Optional<List<QuoteData>> getFiveMinData(String symbol, String systemDate) throws Exception {
    String redisKey = REDIS_KEY_PREFIX + symbol;
    String redisValue = redisTemplate.opsForValue().get(redisKey);

    LocalDate systemLocalDate = LocalDate.parse(systemDate, DATE_FORMATTER);
    if (redisValue == null) {
        List<QuoteData> dataList = quoteDataRepository.findFiveMinDataBySymbolAndDate(symbol, systemLocalDate);
        if (!dataList.isEmpty()) {
            updateRedisWithFiveMinData(redisKey, dataList);
            return Optional.of(dataList);
        }
        return Optional.empty();
    } else {
        Map<String, Object> redisData = objectMapper.readValue(redisValue, new TypeReference<Map<String, Object>>() {});
        LocalDateTime redisMaxTime = LocalDateTime.parse((String) redisData.get("regularMarketTime"));
        LocalDateTime dbMaxTime = quoteDataRepository.findMaxRegularMarketTimeBySymbolAndDate(symbol, systemLocalDate);
        if (dbMaxTime == null) {
            return Optional.empty();
        }
        if (dbMaxTime.isAfter(redisMaxTime)) {
            List<QuoteData> dataList = quoteDataRepository.findFiveMinDataBySymbolAndDate(symbol, systemLocalDate);
            updateRedisWithFiveMinData(redisKey, dataList);
            return Optional.of(dataList);
        } else {
            List<QuoteData> dataList = objectMapper.convertValue(redisData.get("data"), new TypeReference<List<QuoteData>>() {});
            return Optional.of(dataList);
        }
    }
}

  private void updateRedisWithFiveMinData(String redisKey, List<QuoteData> dataList) throws Exception {
    Map<String, Object> redisEntry = Map.of(
        "regularMarketTime", dataList.get(0).getMarketTime().toString(),
        "data", dataList);
    String redisValue = objectMapper.writeValueAsString(redisEntry);
    redisTemplate.opsForValue().set(redisKey, redisValue, 12, TimeUnit.HOURS);
  }

}
