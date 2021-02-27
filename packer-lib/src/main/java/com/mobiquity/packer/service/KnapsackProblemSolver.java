package com.mobiquity.packer.service;

import com.mobiquity.packer.exception.APIException;
import com.mobiquity.packer.model.PackingRequest;
import com.mobiquity.packer.model.PackingResult;
import com.mobiquity.packer.model.ValidationResult;
import java.nio.file.Path;
import java.util.Collection;

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

  public String pack(Path inPath) throws APIException {
    final Collection<PackingRequest> requests = requestReader.readRequests(inPath);
    final ValidationResult validationResult = requestValidator.validate(requests);

    if (!validationResult.isValid()) {
      throw new APIException(validationResult.getValidationMessage());
    }

    final Collection<PackingResult> results = packingSolver.solve(requests);

    return resultWriter.write(results);
  }
}
