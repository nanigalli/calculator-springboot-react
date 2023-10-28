package com.galli.calculator.engine.exception;

public class EngineIllegalArgumentException extends EngineException {

  public EngineIllegalArgumentException(String message, String code) {
    super(message, code);
  }

  public EngineIllegalArgumentException(String message, String code, Throwable e) {
    super(message, code, e);
  }

}
