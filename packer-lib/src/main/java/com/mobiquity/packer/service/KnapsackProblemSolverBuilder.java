package com.mobiquity.packer.service;

import com.mobiquity.packer.service.impl.MoneyReader;
import com.mobiquity.packer.service.impl.PackingRequestReaderImpl;
import com.mobiquity.packer.service.impl.PackingSolverImpl;
import com.mobiquity.packer.service.impl.RequestLineReader;
import com.mobiquity.packer.service.impl.StringPackingResultWriter;
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

  /**
   * Return a builder and configure a {@link KnapsackProblemSolver} to read data from the
   * file using provided {@link java.nio.file.Path}.
   * @return a builder for the next step.
   */
  public KnapsackProblemSolverBuilder withPathReader() {
    requestReader = new PackingRequestReaderImpl(
        new RequestLineReader(
            new MoneyReader()
        )
    );
    return this;
  }

  /**
   * Return a builder and configure a {@link KnapsackProblemSolver} to write result to the
   * string.
   * @return a builder for the next step.
   */
  public KnapsackProblemSolverBuilder withStringWriter() {
    resultWriter = new StringPackingResultWriter();
    return this;
  }

  /**
   * Build an instance of {@link KnapsackProblemSolver} using configured parameters.
   * @return an instance of the {@link KnapsackProblemSolver}.
   */
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
