package com.yahoofinance.bc_yahoo_finance.infra;


public class NotFoundException extends BusinessRuntimeException {
  
  public NotFoundException() {
    super(SysCode.NOT_FOUND);
  }

  // public NotFoundException(SysCode sysCode) {
  //   super(sysCode);
  // }

}
