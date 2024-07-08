package com.yahoofinance.bc_yahoo_finance.entity;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "TSTOCK_QUOTE_YAHOO")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuoteData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;
    private long regularMarketUnix;
    private double regularMarketPrice;
    private double regularMarketChangePercent;
    private double bid;
    private int bidSize;
    private double ask;
    private int askSize;
    private String type;
    private LocalDateTime apiDatetime;
    private LocalDateTime marketTime;

}
