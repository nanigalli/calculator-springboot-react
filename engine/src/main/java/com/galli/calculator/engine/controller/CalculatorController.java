package com.galli.calculator.engine.controller;

import com.galli.calculator.engine.exception.EngineIllegalArgumentException;
import com.galli.calculator.engine.service.CalculatorService;
import com.galli.calculator.engine.service.response.GetAllResultsResponse;
import com.galli.calculator.engine.service.response.OperationResponse;
import java.math.BigDecimal;
import java.util.Arrays;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/calculator/")
public class CalculatorController {

  private final CalculatorService service;

  public CalculatorController(CalculatorService service) {
    this.service = service;
  }

  @GetMapping("results")
  public GetAllResultsResponse getAllResults(
      @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
      @RequestParam(required = false, defaultValue = "50") Integer pageSize) {
    if (pageNumber < 0) {
      throw new EngineIllegalArgumentException("PageNumber must be greater than or equal to 0",
          "pageNumLessThan0");
    }
    if (pageSize < 1) {
      throw new EngineIllegalArgumentException("PageSize must be greater than 0",
          "pageSizeLessThan1");
    }
    return service.getAllResults(pageNumber, pageSize);
  }

  @PostMapping("add")
  public OperationResponse add(@RequestParam String leftNumber, @RequestParam String rightNumber) {
    return service.add(convert(leftNumber), convert(rightNumber.strip()));
  }

  @PostMapping("subtract")
  public OperationResponse subtract(@RequestParam String leftNumber,
      @RequestParam String rightNumber) {
    return service.subtract(convert(leftNumber), convert(rightNumber.strip()));
  }

  @PostMapping("multiply")
  public OperationResponse multiply(@RequestParam String leftNumber,
      @RequestParam String rightNumber) {
    return service.multiply(convert(leftNumber), convert(rightNumber.strip()));
  }

  @PostMapping("divide")
  public OperationResponse divide(@RequestParam String leftNumber,
      @RequestParam String rightNumber) {
    return service.divide(convert(leftNumber), convert(rightNumber.strip()));
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
