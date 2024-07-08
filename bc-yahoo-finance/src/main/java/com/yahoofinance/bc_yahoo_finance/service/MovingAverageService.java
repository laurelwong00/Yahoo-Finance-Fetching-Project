package com.yahoofinance.bc_yahoo_finance.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yahoofinance.bc_yahoo_finance.entity.MovingAverageData;
import com.yahoofinance.bc_yahoo_finance.entity.QuoteData;
import com.yahoofinance.bc_yahoo_finance.service.impl.FiveMinDataServiceImpl;

@Service
public class MovingAverageService {

  @Autowired
    private FiveMinDataServiceImpl fiveMinDataService;

    public List<MovingAverageData> getMovingAverageDataList(String symbol, String systemDate) throws Exception{
      Optional<List<QuoteData>> optionalData = fiveMinDataService.getFiveMinData(symbol, systemDate);
      List<QuoteData> quotes = optionalData.orElse(Collections.emptyList());

      List<MovingAverageData> maDataList = new ArrayList<>();
      // Calculate moving averages
      double sum = 0;
      int count = 0;
      for (QuoteData data : quotes) {
          sum += data.getRegularMarketPrice();
          if (count < 5) count++;
          if (count >= 1) { // Calculate 5-period MA
              double movingAverage = sum / count;
              MovingAverageData maData = MovingAverageData.builder()
                                        .timestamp(data.getRegularMarketUnix())
                                        .movingAverage(movingAverage)
                                        .build();
              if (count>=5) sum -= quotes.get(count - 5).getRegularMarketPrice(); // Adjust sum for next window
              maDataList.add(maData);
          }
      }
      return maDataList;
    }
}

