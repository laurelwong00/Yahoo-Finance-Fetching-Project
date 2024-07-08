package com.yahoofinance.bc_yahoo_finance.infra;


import lombok.Getter;

@Getter
public enum ErrorCode {
  NPE(99, "Null Pointer Exception."), //
  NFE(99, "Number Format Exception."), //
  AE(99, "Arithmetic Exception."), //
  ;

  private int code;
  private String desc;

  private ErrorCode(int code, String desc) {
    this.code = code;
    this.desc = desc;
  }
}
