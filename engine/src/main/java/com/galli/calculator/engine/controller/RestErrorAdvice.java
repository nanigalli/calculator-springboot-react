package com.galli.calculator.engine.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.galli.calculator.engine.exception.EngineIllegalArgumentException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestErrorAdvice {

  @ResponseStatus(code = INTERNAL_SERVER_ERROR)
  @ExceptionHandler({Throwable.class})
  public ResponseEntity<ApiError> handleException(Throwable e, WebRequest request) {
    return ResponseEntity.status(INTERNAL_SERVER_ERROR)
        .body(
            new ApiError("There was an error in the application, please contact support", "ERROR"));
  }

  @ResponseStatus(code = BAD_REQUEST)
  @ExceptionHandler({EngineIllegalArgumentException.class})
  public ResponseEntity<ApiError> handleEngineIllegalArgumentException(
      EngineIllegalArgumentException e,
      WebRequest request) {
    return ResponseEntity.status(BAD_REQUEST)
        .body(new ApiError(e.getMessage(), e.getCode()));
  }

}
