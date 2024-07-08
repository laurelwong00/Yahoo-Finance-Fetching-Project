package com.yahoofinance.bc_yahoo_finance.infra;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum QuoteType {
  FIVEMIN("5M"), DAILY("D"), WEEKLY("W"), MONTHLY("M");

  private String type;

}
