package com.highchart.bc_stock_web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BidAskData {
  private double bidPrice;
  private double askPrice;
  private int bidVolume;
  private int askVolume;

  // private double bid;
  // private int bidSize;
  // private double ask;
  // private int askSize;
}
