package com.galli.calculator.engine.service.response;

import com.galli.calculator.engine.repository.model.Result;
import java.util.List;

public record GetAllResultsResponse(List<Result> results, int pageNumber, int totalPages) {

}
