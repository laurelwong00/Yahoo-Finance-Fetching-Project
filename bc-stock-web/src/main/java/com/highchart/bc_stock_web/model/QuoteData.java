package com.highchart.bc_stock_web.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuoteData {
  private String timestamp;
  private double price;

  public QuoteData(int year, int month, int day, int hour, int minute,
      double price) {
    this.timestamp = LocalDateTime.of(year, month, month, hour, minute, minute) //
        .atZone(ZoneId.of("Asia/Hong_Kong")) //
        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    System.out.println(timestamp);
    this.price = price;
  }

  public QuoteData(LocalDateTime timestamp, double price) {
    this.timestamp = timestamp.atZone(ZoneId.of("Asia/Hong_Kong"))
        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    this.price = price;
  }
}
