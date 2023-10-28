package com.galli.calculator.engine.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.galli.calculator.engine.exception.EngineIllegalArgumentException;
import com.galli.calculator.engine.service.CalculatorService;
import com.galli.calculator.engine.service.response.OperationResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CalculatorControllerTest {

  @Mock
  private CalculatorService service;

  @InjectMocks
  private CalculatorController controller;

  @Test
  public void testAdd_invalidLeftNumber() {
    try {
      controller.add("xx", "1");
      fail();
    } catch (EngineIllegalArgumentException e) {
      verify(service, never()).add(any(), any());
    }
  }

  @Test
  public void testAdd_invalidRightNumber() {
    try {
      controller.add("1", "xxx");
      fail();
    } catch (EngineIllegalArgumentException e) {
      verify(service, never()).add(any(), any());
    }
  }

  @Test
  public void testAdd() {
    OperationResponse expectedResponse = new OperationResponse("1.9");
    when(service.add(any(), any())).thenReturn(expectedResponse);

    OperationResponse response = controller.add(" -1.1 ", "2.0");

    assertEquals(response, expectedResponse);
    verify(service, times(1)).add(any(), any());
  }

  @Test
  public void testAdd_error() {
    when(service.add(any(), any())).thenThrow(new RuntimeException("Ups.."));

    try {
      controller.add("1", "2");
      fail();
    } catch (RuntimeException e) {
      verify(service, times(1)).add(any(), any());
    }
  }

  @Test
  public void testConvert_emptyString() {
    try {
      controller.convert("");
      fail();
    } catch (EngineIllegalArgumentException ignored) {
    }

    try {
      controller.convert(" ");
      fail();
    } catch (EngineIllegalArgumentException ignored) {
    }
  }

  @Test
  public void testConvert_invalidNumbers() {
    try {
      controller.convert("--1");
      fail();
    } catch (EngineIllegalArgumentException ignored) {
    }

    try {
      controller.convert("1.1.2");
      fail();
    } catch (EngineIllegalArgumentException ignored) {
    }
  }

}
