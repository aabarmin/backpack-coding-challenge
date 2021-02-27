package com.mobiquity.packer.service;

import com.mobiquity.packer.exception.ApiException;
import com.mobiquity.packer.model.PackingRequest;
import com.mobiquity.packer.model.PackingResult;
import com.mobiquity.packer.model.ValidationResult;
import java.nio.file.Path;
import java.util.Collection;

/**
 * The Knapsack problem solver.
 */
public class KnapsackProblemSolver {
  private final PackingRequestReader requestReader;
  private final PackingRequestValidator requestValidator;
  private final PackingSolver packingSolver;
  private final PackingResultWriter resultWriter;

  KnapsackProblemSolver(final PackingRequestReader requestReader,
                               final PackingRequestValidator requestValidator,
                               final PackingSolver packingSolver,
                               final PackingResultWriter resultWriter) {
    this.requestReader = requestReader;
    this.requestValidator = requestValidator;
    this.packingSolver = packingSolver;
    this.resultWriter = resultWriter;
  }

  /**
   * Solve the Knapsack problem with input data provided in the inPath parameter. The result
   * will be present as string.
   * @param inPath is path to the file with source data.
   * @return a string which contains the solution.
   * @throws ApiException if input data is not valid.
   */
  public String pack(Path inPath) throws ApiException {
    final Collection<PackingRequest> requests = requestReader.readRequests(inPath);
    final ValidationResult validationResult = requestValidator.validate(requests);

    if (!validationResult.isValid()) {
      throw new ApiException(validationResult.getValidationMessage());
    }

    final Collection<PackingResult> results = packingSolver.solve(requests);

    return resultWriter.write(results);
  }
}
