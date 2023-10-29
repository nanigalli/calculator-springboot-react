package com.galli.calculator.engine.service;

import static com.galli.calculator.engine.repository.model.Operator.add;
import static com.galli.calculator.engine.repository.model.Operator.divide;

import com.galli.calculator.engine.exception.EngineIllegalArgumentException;
import com.galli.calculator.engine.repository.ResultRepository;
import com.galli.calculator.engine.repository.model.Operator;
import com.galli.calculator.engine.repository.model.Result;
import com.galli.calculator.engine.service.response.GetAllResultsResponse;
import com.galli.calculator.engine.service.response.OperationResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

  private static final int SCALE = 10;

  private final ResultRepository repository;

  public CalculatorService(ResultRepository repository) {
    this.repository = repository;
  }

  public GetAllResultsResponse getAllResults() {
    return new GetAllResultsResponse(repository.findAll());
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

  public OperationResponse divide(BigDecimal leftNumber, BigDecimal rightNumber) {
    if (rightNumber.compareTo(BigDecimal.ZERO) == 0) {
      throw new EngineIllegalArgumentException("The right number must not be 0",
          "rightNumberIsZero");
    }

    Optional<OperationResponse> oldResult = getOldResult(leftNumber, rightNumber, divide);
    if (oldResult.isPresent()) {
      return oldResult.get();
    }

    String result = leftNumber.divide(rightNumber, SCALE, RoundingMode.HALF_EVEN)
        .stripTrailingZeros().toString();
    repository.save(new Result(leftNumber.toString(), rightNumber.toString(), divide, result));

    return new OperationResponse(result);
  }

  protected Optional<OperationResponse> getOldResult(BigDecimal leftNumber, BigDecimal rightNumber,
      Operator operator) {
    return repository.findByLeftNumberAndRightNumberAndOperator(leftNumber.toString(),
            rightNumber.toString(), operator)
        .map(Result::getResult).map(OperationResponse::new);
  }

}
