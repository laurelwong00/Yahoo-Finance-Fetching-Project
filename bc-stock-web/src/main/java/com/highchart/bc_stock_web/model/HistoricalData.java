package com.highchart.bc_stock_web.model;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class HistoricalData {
  private String symbol;
  private List<LocalDate> dates;
  private List<Double> opens;
  private List<Double> highs;
  private List<Double> lows;
  private List<Double> closes;
  private List<Long> volumes;

}