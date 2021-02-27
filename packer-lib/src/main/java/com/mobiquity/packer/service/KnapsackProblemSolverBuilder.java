package com.mobiquity.packer.service;

import com.mobiquity.packer.service.impl.*;
import com.mobiquity.packer.service.impl.strategy.DynamicPackingSolvingStrategy;
import com.mobiquity.packer.service.impl.strategy.PackingSolvingStrategy;

import java.util.Objects;

public class KnapsackProblemSolverBuilder {
  private PackingRequestReader requestReader;
  private PackingSolvingStrategy solvingStrategy;
  private PackingResultWriter resultWriter;

  private KnapsackProblemSolverBuilder() {

  }

  public static KnapsackProblemSolverBuilder builder() {
    return new KnapsackProblemSolverBuilder();
  }

  public KnapsackProblemSolverBuilder withDynamicZeroOneStrategy() {
    solvingStrategy = new DynamicPackingSolvingStrategy();
    return this;
  }

  public KnapsackProblemSolverBuilder withPathReader() {
    requestReader = new PackingRequestReaderImpl(
        new RequestLineReader(
            new MoneyReader()
        )
    );
    return this;
  }

  public KnapsackProblemSolverBuilder withStringWriter() {
    resultWriter = new StringPackingResultWriter();
    return this;
  }

  public KnapsackProblemSolver build() {
    Objects.requireNonNull(requestReader, "Request reader should be configured");
    Objects.requireNonNull(solvingStrategy, "Solving strategy should be chosen");
    Objects.requireNonNull(resultWriter, "Result writer should be configured");

    return new KnapsackProblemSolver(
        requestReader,
        new PackingRequestValidator(),
        new PackingSolverImpl(solvingStrategy),
        resultWriter
    );
  }
}
