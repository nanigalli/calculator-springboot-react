package com.galli.calculator.engine.repository;

import com.galli.calculator.engine.repository.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<Result, Long> {

}
