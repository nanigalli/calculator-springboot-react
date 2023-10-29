package com.galli.calculator.engine.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.galli.calculator.engine.repository.ResultRepository;
import com.galli.calculator.engine.repository.model.Operator;
import com.galli.calculator.engine.repository.model.Result;
import com.galli.calculator.engine.service.response.OperationResponse;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CalculatorServiceTest {

  @Mock
  private ResultRepository repository;

  @InjectMocks
  private CalculatorService service;

  @Test
  public void testAddNumbers_errorCheckingOldResults() {
    when(repository.findByLeftNumberAndRightNumberAndOperator(anyString(), anyString(), any()))
        .thenThrow(new RuntimeException("Ups.."));

    try {
      service.add(new BigDecimal(1), new BigDecimal(2));
      fail();
    } catch (RuntimeException e) {
      verify(repository, times(1)).findByLeftNumberAndRightNumberAndOperator(anyString(),
          anyString(), any());
      verify(repository, never()).save(any());
    }
  }

  @Test
  public void testAddNumbers_resultExistsInDatabase() {
    String expectedResult = "3";
    when(repository.findByLeftNumberAndRightNumberAndOperator(anyString(), anyString(), any()))
        .thenReturn(Optional.of(new Result("1", "2", Operator.add, expectedResult)));

    OperationResponse result = service.add(new BigDecimal(1), new BigDecimal(2));

    assertEquals(expectedResult, result.result());
    verify(repository, times(1)).findByLeftNumberAndRightNumberAndOperator(anyString(), anyString(),
        any());
    verify(repository, never()).save(any());
  }

  @Test
  public void testAddNumbers_resultDoesNotExistInDatabase() {
    String expectedResult = "3";
    when(repository.findByLeftNumberAndRightNumberAndOperator(anyString(), anyString(), any()))
        .thenReturn(Optional.empty());

    OperationResponse result = service.add(new BigDecimal(1), new BigDecimal(2));

    assertEquals(expectedResult, result.result());
    verify(repository, times(1)).findByLeftNumberAndRightNumberAndOperator(anyString(), anyString(),
        any());
    verify(repository, times(1)).save(any());
  }

  @Test
  public void testAddNumbers_resultDoesNotExistInDatabase_errorSavingResult() {
    when(repository.findByLeftNumberAndRightNumberAndOperator(anyString(), anyString(), any()))
        .thenReturn(Optional.empty());
    when(repository.save(any())).thenThrow(new RuntimeException("Ups.."));

    try {
      service.add(new BigDecimal(1), new BigDecimal(2));
      fail();
    } catch (RuntimeException e) {
      verify(repository, times(1)).findByLeftNumberAndRightNumberAndOperator(anyString(),
          anyString(), any());
      verify(repository, times(1)).save(any());
    }
  }

  @Test
  public void testAddNumbers_resultDoesNotExistInDatabase_bigNumbers() {
    BigDecimal bigNumber = new BigDecimal(
        "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
    String expectedResult = "222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222222";
    when(repository.findByLeftNumberAndRightNumberAndOperator(anyString(), anyString(), any()))
        .thenReturn(Optional.empty());

    OperationResponse result = service.add(bigNumber, bigNumber);

    assertEquals(expectedResult, result.result());
    verify(repository, times(1)).findByLeftNumberAndRightNumberAndOperator(anyString(), anyString(),
        any());
    verify(repository, times(1)).save(any());
  }

  @Test
  public void testAddNumbers_resultDoesNotExistInDatabase_negativeNumbers() {
    String expectedResult = "1.0";
    when(repository.findByLeftNumberAndRightNumberAndOperator(anyString(), anyString(), any()))
        .thenReturn(Optional.empty());

    OperationResponse result = service.add(new BigDecimal("-0.9"), new BigDecimal("1.9"));

    assertEquals(expectedResult, result.result());
    verify(repository, times(1)).findByLeftNumberAndRightNumberAndOperator(anyString(), anyString(),
        any());
    verify(repository, times(1)).save(any());
  }

  @Test
  public void testDivideNumbers_rightNumberEquals0() {
    try {
      service.divide(new BigDecimal(1), new BigDecimal(0));
      fail();
    } catch (RuntimeException e) {
      verify(repository, never()).findByLeftNumberAndRightNumberAndOperator(anyString(),
          anyString(), any());
      verify(repository, never()).save(any());
    }

    try {
      service.divide(new BigDecimal(1), new BigDecimal("0.000"));
      fail();
    } catch (RuntimeException e) {
      verify(repository, never()).findByLeftNumberAndRightNumberAndOperator(anyString(),
          anyString(), any());
      verify(repository, never()).save(any());
    }
  }

  @Test
  public void testDivideNumbers_errorCheckingOldResults() {
    when(repository.findByLeftNumberAndRightNumberAndOperator(anyString(), anyString(), any()))
        .thenThrow(new RuntimeException("Ups.."));

    try {
      service.divide(new BigDecimal(1), new BigDecimal(2));
      fail();
    } catch (RuntimeException e) {
      verify(repository, times(1)).findByLeftNumberAndRightNumberAndOperator(anyString(),
          anyString(), any());
      verify(repository, never()).save(any());
    }
  }

  @Test
  public void testDivideNumbers_resultExistsInDatabase() {
    String expectedResult = "0.5";
    when(repository.findByLeftNumberAndRightNumberAndOperator(anyString(), anyString(), any()))
        .thenReturn(Optional.of(new Result("1", "2", Operator.add, expectedResult)));

    OperationResponse result = service.divide(new BigDecimal(1), new BigDecimal(2));

    assertEquals(expectedResult, result.result());
    verify(repository, times(1)).findByLeftNumberAndRightNumberAndOperator(anyString(), anyString(),
        any());
    verify(repository, never()).save(any());
  }

  @Test
  public void testDivideNumbers_resultDoesNotExistInDatabase() {
    String expectedResult = "0.5";
    when(repository.findByLeftNumberAndRightNumberAndOperator(anyString(), anyString(), any()))
        .thenReturn(Optional.empty());

    OperationResponse result = service.divide(new BigDecimal(1), new BigDecimal(2));

    assertEquals(expectedResult, result.result());
    verify(repository, times(1)).findByLeftNumberAndRightNumberAndOperator(anyString(), anyString(),
        any());
    verify(repository, times(1)).save(any());
  }

  @Test
  public void testDivideNumbers_resultDoesNotExistInDatabase_errorSavingResult() {
    when(repository.findByLeftNumberAndRightNumberAndOperator(anyString(), anyString(), any()))
        .thenReturn(Optional.empty());
    when(repository.save(any())).thenThrow(new RuntimeException("Ups.."));

    try {
      service.divide(new BigDecimal(1), new BigDecimal(2));
      fail();
    } catch (RuntimeException e) {
      verify(repository, times(1)).findByLeftNumberAndRightNumberAndOperator(anyString(),
          anyString(), any());
      verify(repository, times(1)).save(any());
    }
  }

  @Test
  public void testDivideNumbers_resultDoesNotExistInDatabase_bigNumbers() {
    BigDecimal bigNumber = new BigDecimal(
        "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
    String expectedResult = "55555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555555.5";
    when(repository.findByLeftNumberAndRightNumberAndOperator(anyString(), anyString(), any()))
        .thenReturn(Optional.empty());

    OperationResponse result = service.divide(bigNumber, new BigDecimal(2));

    assertEquals(expectedResult, result.result());
    verify(repository, times(1)).findByLeftNumberAndRightNumberAndOperator(anyString(), anyString(),
        any());
    verify(repository, times(1)).save(any());
  }

  @Test
  public void testDivideNumbers_resultDoesNotExistInDatabase_negativeNumbers() {
    String expectedResult = "-0.4736842105";
    when(repository.findByLeftNumberAndRightNumberAndOperator(anyString(), anyString(), any()))
        .thenReturn(Optional.empty());

    OperationResponse result = service.divide(new BigDecimal("-0.9"), new BigDecimal("1.9"));

    assertEquals(expectedResult, result.result());
    verify(repository, times(1)).findByLeftNumberAndRightNumberAndOperator(anyString(), anyString(),
        any());
    verify(repository, times(1)).save(any());
  }

}
