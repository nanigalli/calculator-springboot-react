package com.galli.calculator.engine.controller;

import com.galli.calculator.engine.exception.EngineIllegalArgumentException;
import com.galli.calculator.engine.service.CalculatorService;
import com.galli.calculator.engine.service.response.OperationResponse;
import java.math.BigDecimal;
import java.util.Arrays;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/calculator/")
public class CalculatorController {

  private final CalculatorService service;

  public CalculatorController(CalculatorService service) {
    this.service = service;
  }

  @PostMapping("add")
  public OperationResponse add(@RequestParam String leftNumber, @RequestParam String rightNumber) {
    return service.add(convert(leftNumber), convert(rightNumber.strip()));
  }

  protected BigDecimal convert(String number) {
    try {
      return new BigDecimal(number.strip());
    } catch (NumberFormatException e) {
      System.out.println(Arrays.toString(e.getStackTrace()));
      throw new EngineIllegalArgumentException("At least 1 parameter is not a number", "notANumber",
          e);
    }
  }

}
