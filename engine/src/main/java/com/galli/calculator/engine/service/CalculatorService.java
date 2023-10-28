package com.galli.calculator.engine.service;

import com.galli.calculator.engine.exception.EngineIllegalArgumentException;
import com.galli.calculator.engine.repository.ResultRepository;
import com.galli.calculator.engine.repository.model.Operation;
import com.galli.calculator.engine.repository.model.Result;
import com.galli.calculator.engine.service.response.OperationResponse;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

  private final ResultRepository repository;

  public CalculatorService(ResultRepository repository) {
    this.repository = repository;
  }

  public OperationResponse add(String number1, String number2) {
    String res;
    try {
      res = new BigDecimal(number1).add(new BigDecimal(number2)).toPlainString();
    } catch (Throwable e) {
      throw new EngineIllegalArgumentException("At least 1 parameter is not a number", "notANumber",
          e);
    }

    repository.save(new Result(number1, number2, Operation.add, res));

    return new OperationResponse(res);
  }

}
