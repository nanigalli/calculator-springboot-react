package com.galli.calculator.engine.service;

import static com.galli.calculator.engine.repository.model.Operator.add;

import com.galli.calculator.engine.repository.ResultRepository;
import com.galli.calculator.engine.repository.model.Operator;
import com.galli.calculator.engine.repository.model.Result;
import com.galli.calculator.engine.service.response.OperationResponse;
import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

  private final ResultRepository repository;

  public CalculatorService(ResultRepository repository) {
    this.repository = repository;
  }

  public OperationResponse add(BigDecimal leftNumber, BigDecimal rightNumber) {
    Optional<OperationResponse> oldResult = getOldResult(leftNumber, rightNumber, add);
    if (oldResult.isPresent()) {
      return oldResult.get();
    }

    String result = leftNumber.add(rightNumber).toString();
    repository.save(new Result(leftNumber.toString(), rightNumber.toString(), add, result));

    return new OperationResponse(result);
  }

  protected Optional<OperationResponse> getOldResult(BigDecimal leftNumber, BigDecimal rightNumber,
      Operator operator) {
    return repository.findByLeftNumberAndRightNumberAndOperator(leftNumber.toString(),
            rightNumber.toString(), operator)
        .map(Result::getResult).map(OperationResponse::new);
  }

}
