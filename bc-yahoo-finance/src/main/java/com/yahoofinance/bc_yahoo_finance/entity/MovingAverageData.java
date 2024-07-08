package com.yahoofinance.bc_yahoo_finance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MovingAverageData {
    private Long timestamp;
    private double movingAverage;
}
