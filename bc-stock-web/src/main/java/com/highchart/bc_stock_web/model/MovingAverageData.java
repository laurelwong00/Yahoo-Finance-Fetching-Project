package com.highchart.bc_stock_web.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class MovingAverageData {
    private LocalDateTime timestamp;
    private double movingAverage;
}

