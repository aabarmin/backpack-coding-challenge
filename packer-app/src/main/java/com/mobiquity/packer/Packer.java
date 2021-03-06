package com.mobiquity.packer;

import com.mobiquity.packer.exception.ApiException;
import com.mobiquity.packer.service.KnapsackProblemSolver;
import com.mobiquity.packer.service.KnapsackProblemSolverBuilder;
import java.nio.file.Path;

public class Packer {

  private Packer() {
  }

  public static String pack(String filePath) throws ApiException {
    final KnapsackProblemSolver solver = KnapsackProblemSolverBuilder.builder()
        .withPathReader()
        .withStringWriter()
        .withDynamicZeroOneStrategy()
        .build();

    return solver.pack(Path.of(filePath));
  }
}
