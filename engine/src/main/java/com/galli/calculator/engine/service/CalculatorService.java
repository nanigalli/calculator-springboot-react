package com.galli.calculator.engine.service;

import static com.galli.calculator.engine.repository.model.Operator.add;
import static com.galli.calculator.engine.repository.model.Operator.divide;
import static com.galli.calculator.engine.repository.model.Operator.multiply;
import static com.galli.calculator.engine.repository.model.Operator.subtract;

import com.galli.calculator.engine.exception.EngineIllegalArgumentException;
import com.galli.calculator.engine.repository.ResultRepository;
import com.galli.calculator.engine.repository.model.Operator;
import com.galli.calculator.engine.repository.model.Result;
import com.galli.calculator.engine.service.response.GetAllResultsResponse;
import com.galli.calculator.engine.service.response.OperationResponse;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.function.BiFunction;
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
    return calculate(leftNumber, rightNumber, add, BigDecimal::add);
  }

  public OperationResponse subtract(BigDecimal leftNumber, BigDecimal rightNumber) {
    return calculate(leftNumber, rightNumber, subtract, BigDecimal::subtract);
  }

  public OperationResponse multiply(BigDecimal leftNumber, BigDecimal rightNumber) {
    return calculate(leftNumber, rightNumber, multiply, BigDecimal::multiply);
  }

  public OperationResponse divide(BigDecimal leftNumber, BigDecimal rightNumber) {
    if (rightNumber.compareTo(BigDecimal.ZERO) == 0) {
      throw new EngineIllegalArgumentException("The right number must not be 0",
          "rightNumberIsZero");
    }

    return calculate(leftNumber, rightNumber, divide, (leftNum, rightNum) ->
        leftNum.divide(rightNum, SCALE, RoundingMode.HALF_EVEN).stripTrailingZeros());
  }

  protected OperationResponse calculate(BigDecimal leftNumber, BigDecimal rightNumber,
      Operator operator, BiFunction<BigDecimal, BigDecimal, BigDecimal> operation) {
    Optional<OperationResponse> oldResult = repository
        .findByLeftNumberAndRightNumberAndOperator(leftNumber.toString(), rightNumber.toString(),
            operator)
        .map(Result::getResult)
        .map(OperationResponse::new);

    if (oldResult.isPresent()) {
      return oldResult.get();
    }

    String result = operation.apply(leftNumber, rightNumber).toString();
    repository.save(new Result(leftNumber.toString(), rightNumber.toString(), operator, result));

    return new OperationResponse(result);
  }

}
