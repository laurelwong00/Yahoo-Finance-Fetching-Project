package com.yahoofinance.bc_yahoo_finance.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.yahoofinance.bc_yahoo_finance.repository.QuoteDataRepository;

@Service
public class SysDateService {
  @Autowired
  private StringRedisTemplate redisTemplate;

  @Autowired
  private QuoteDataRepository quoteDataRepository;

  private static final String REDIS_KEY_PREFIX = "SYSDATE-";
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public String getSystemDate(String symbol) {
    String redisKey = REDIS_KEY_PREFIX + symbol;
    String systemDate = redisTemplate.opsForValue().get(redisKey);

    if (systemDate == null) {
      systemDate = fetchSystemDateFromDatabase(symbol);
      redisTemplate.opsForValue().set(redisKey, systemDate, 4, TimeUnit.HOURS);
    }
    return systemDate;
  }

  private String fetchSystemDateFromDatabase(String symbol) {
    LocalDate maxDate = quoteDataRepository.findMaxRegularMarketTime(symbol);
    return maxDate.format(DATE_FORMATTER);
  }

  public void clearAllSystemDatesCache() {
    redisTemplate.keys(REDIS_KEY_PREFIX + "*").forEach(redisTemplate::delete);
  }

}
