package com.galli.calculator.engine.service;

import com.galli.calculator.engine.exception.EngineIllegalArgumentException;
import com.galli.calculator.engine.repository.ResultRepository;
import com.galli.calculator.engine.repository.model.Operator;
import com.galli.calculator.engine.repository.model.Result;
import com.galli.calculator.engine.service.response.OperationResponse;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

  private final ResultRepository repository;

  @Autowired
  public CalculatorService(ResultRepository repository) {
    this.repository = repository;
  }

  public OperationResponse add(String leftNumber, String rightNumber) {
    Optional<String> oldResult = getOldResult(leftNumber, rightNumber, Operator.add);
    if (oldResult.isPresent()) {
      return new OperationResponse(oldResult.get());
    }

    String res;
    try {
      res = new BigDecimal(leftNumber).add(new BigDecimal(rightNumber)).toPlainString();
    } catch (Throwable e) {
      throw new EngineIllegalArgumentException("At least 1 parameter is not a number", "notANumber",
          e);
    }

    repository.save(new Result(leftNumber, rightNumber, Operator.add, res));

    return new OperationResponse(res);
  }

  protected Optional<String> getOldResult(String leftNumber, String rightNumber,
      Operator operator) {
    return repository.findByLeftNumberAndRightNumberAndOperator(leftNumber, rightNumber, operator)
        .map(Result::getResult);
  }

}
