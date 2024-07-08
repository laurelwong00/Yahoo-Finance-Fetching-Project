package com.yahoofinance.bc_yahoo_finance.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.yahoofinance.bc_yahoo_finance.entity.Stock;
import com.yahoofinance.bc_yahoo_finance.infra.RedisHelper;
import com.yahoofinance.bc_yahoo_finance.repository.StockRepository;
import com.yahoofinance.bc_yahoo_finance.service.StockService;
import com.yahoofinance.bc_yahoo_finance.service.SysDateService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AppRunner implements CommandLineRunner {

  @Autowired
  private SysDateService sysDateService;

  @Autowired
  private StockRepository stockRepository;

  @Autowired
  private RedisHelper redisHelper;

  @Autowired
  private StockService stockService;

  public AppRunner(StockRepository stockRepository, RedisHelper redisHelper) {
    this.stockRepository = stockRepository;
    this.redisHelper = redisHelper;
  }

  @Override
  public void run(String... args) throws Exception {
    stockService.clearStockSymbolsFromRedis();
    System.out.println("Redis stock symbols cleared at startup.");

    Stock entity1 = new Stock("0388.HK");
    Stock entity2 = new Stock("0700.HK");
    Stock entity3 = new Stock("0005.HK");
    // Stock entity4 = new Stock("9988.HK");
    stockRepository.save(entity1);
    stockRepository.save(entity2);
    stockRepository.save(entity3);
    // stockRepository.save(entity4);

    System.out.println("Stock symbols have been loaded into the database.");

    sysDateService.clearAllSystemDatesCache();
    
    log.info("before redis");
    redisHelper.set("test", "teast");
  }

}