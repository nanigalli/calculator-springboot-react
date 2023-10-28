package com.galli.calculator.engine.exception;

public class EngineException extends RuntimeException {

  private final String code;

  public EngineException(String message, String code) {
    super(message);
    this.code = code;
  }

  public EngineException(String message, String code, Throwable e) {
    super(message, e);
    this.code = code;
  }

  public String getCode() {
    return code;
  }

}
