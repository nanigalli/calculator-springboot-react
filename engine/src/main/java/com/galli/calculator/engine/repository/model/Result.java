package com.galli.calculator.engine.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "result")
public class Result {

  @Id
  private String id;

  private String leftNumber;

  private String rightNumber;

  private Operator operator;

  private String result;

  @Column(columnDefinition = "TIMESTAMP")
  private LocalDateTime executionDate;

  public Result() {
  }

  public Result(String leftNumber, String rightNumber, Operator operator, String result) {
    this.id = UUID.randomUUID().toString();
    this.leftNumber = leftNumber;
    this.rightNumber = rightNumber;
    this.operator = operator;
    this.result = result;
    this.executionDate = LocalDateTime.now();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getLeftNumber() {
    return leftNumber;
  }

  public void setLeftNumber(String leftNumber) {
    this.leftNumber = leftNumber;
  }

  public String getRightNumber() {
    return rightNumber;
  }

  public void setRightNumber(String rightNumber) {
    this.rightNumber = rightNumber;
  }

  public Operator getOperator() {
    return operator;
  }

  public void setOperator(Operator operator) {
    this.operator = operator;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public LocalDateTime getExecutionDate() {
    return executionDate;
  }

  public void setExecutionDate(LocalDateTime executionDate) {
    this.executionDate = executionDate;
  }

}
