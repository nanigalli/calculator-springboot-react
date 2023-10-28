package com.galli.calculator.engine.repository.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "result")
public class Result {

  @Id
  @GeneratedValue
  private Long id;

  private String number1;

  private String number2;

  private Operation operation;

  private String result;

  public Result(String number1, String number2, Operation operation, String result) {
    this.number1 = number1;
    this.number2 = number2;
    this.operation = operation;
    this.result = result;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNumber1() {
    return number1;
  }

  public void setNumber1(String number1) {
    this.number1 = number1;
  }

  public String getNumber2() {
    return number2;
  }

  public void setNumber2(String number2) {
    this.number2 = number2;
  }

  public Operation getOperation() {
    return operation;
  }

  public void setOperation(Operation operation) {
    this.operation = operation;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

}
