package com.yahoofinance.bc_yahoo_finance.infra;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

// @RestControllerAdvice // @ContollerAdvice + @ResponseBody
public class GlobalExceptionHandler {

  // User ApiResp.class directly, instead of ErrorResponse.class
  @ExceptionHandler(NumberFormatException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ApiResp<Void> numberFormatExceptionHandler(NumberFormatException e) {
    // return ErrorResponse.of(ErrorCode.NFE.getCode(),
    // ErrorCode.NFE.getDesc());
    return ApiResp.<Void>builder() //
        .error(ErrorCode.NFE) //
        .build();
  }

  @ExceptionHandler(NullPointerException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponse nullPointerExceptionHandler(NullPointerException e) {
    return ErrorResponse.of(ErrorCode.NPE.getCode(), ErrorCode.NPE.getDesc());
  }

  @ExceptionHandler(ArithmeticException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponse arithmeticExceptionHandler(ArithmeticException e) {
    return ErrorResponse.of(ErrorCode.AE.getCode(), ErrorCode.AE.getDesc());
  }

  @ExceptionHandler(BusinessRuntimeException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public ErrorResponse businessRuntimeExceptionHandler(
      BusinessRuntimeException e) {
    return new ErrorResponse(e.getCode(), e.getMessage());
  }

  // including all other checked and unchecked exceptions
  // @ExceptionHandler(RuntimeException.class)
  // @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  // public ErrorResponse unhandledRuntimeException() {
  // return new ErrorResponse(99998, "Other unhandled runtime exception.");
  // }

  // @ExceptionHandler(Exception.class)
  // @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  // public ErrorResponse unhandledException() {
  // return new ErrorResponse(99999, "Other unhandled exception.");
  // }

  // Alternative
  // @ExceptionHandler({NumberFormatException.class, NullPointerException.class,
  // ArithmeticException.class})
  // public ErrorResponse exceptionHandler(RuntimeException e) {
  // if (e instanceof NumberFormatException) {
  // return ErrorResponse.of(ErrorCode.NFE.getCode(), ErrorCode.NFE.getDesc());
  // } else if (e instanceof NullPointerException) {
  // return ErrorResponse.of(ErrorCode.NPE.getCode(), ErrorCode.NPE.getDesc());
  // } else if (e instanceof ArithmeticException) {
  // return ErrorResponse.of(ErrorCode.AE.getCode(), ErrorCode.AE.getDesc());
  // }
  // return ErrorResponse.of(99999, "Unhandled Exception.");
  // }

}
