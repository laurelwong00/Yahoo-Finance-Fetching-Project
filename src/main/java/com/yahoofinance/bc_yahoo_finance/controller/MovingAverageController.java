package com.yahoofinance.bc_yahoo_finance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yahoofinance.bc_yahoo_finance.entity.MovingAverageData;
import com.yahoofinance.bc_yahoo_finance.service.MovingAverageService;
import com.yahoofinance.bc_yahoo_finance.service.SysDateService;

@RestController
public class MovingAverageController {

    @Autowired
    private MovingAverageService movingAverageService;

    @Autowired
    private SysDateService sysDateService;

    @GetMapping("/api/quote/ma-data")
    public List<MovingAverageData> getMovingAverage(@RequestParam String symbol) throws Exception{
        String systemDate = sysDateService.getSystemDate(symbol);
        return movingAverageService.getMovingAverageDataList(symbol, systemDate);
    }
}

