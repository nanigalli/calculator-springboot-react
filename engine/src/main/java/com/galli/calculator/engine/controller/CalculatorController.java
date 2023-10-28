package com.galli.calculator.engine.controller;

import com.galli.calculator.engine.service.CalculatorService;
import com.galli.calculator.engine.service.response.OperationResponse;
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
  public OperationResponse add(@RequestParam String number1, @RequestParam String number2) {
    return service.add(number1.strip(), number2.strip());
  }

}
