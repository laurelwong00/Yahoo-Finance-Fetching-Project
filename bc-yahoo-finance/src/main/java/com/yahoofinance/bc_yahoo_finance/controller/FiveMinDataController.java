package com.yahoofinance.bc_yahoo_finance.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yahoofinance.bc_yahoo_finance.entity.QuoteData;
import com.yahoofinance.bc_yahoo_finance.service.SysDateService;
import com.yahoofinance.bc_yahoo_finance.service.impl.FiveMinDataServiceImpl;

@RestController
@RequestMapping(value = "/api/quote")
public class FiveMinDataController {

  @Autowired
  private FiveMinDataServiceImpl fiveMinDataService;

  @Autowired
  private SysDateService sysDateService;

  @GetMapping(value = "/5min-data")
  public List<QuoteData> getFiveMinData(@RequestParam String symbol) throws Exception {
    String systemDate = sysDateService.getSystemDate(symbol);
    Optional<List<QuoteData>> optionalData = fiveMinDataService.getFiveMinData(symbol, systemDate);
    return optionalData.orElse(Collections.emptyList());
}

}
