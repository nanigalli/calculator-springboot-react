package com.galli.calculator.engine.repository;

import com.galli.calculator.engine.repository.model.Operator;
import com.galli.calculator.engine.repository.model.Result;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends JpaRepository<Result, String> {

  Optional<Result> findByLeftNumberAndRightNumberAndOperator(String left, String right, Operator operator);

}
