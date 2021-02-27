package com.mobiquity.packer.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class KnapsackProblemSolverBuilderTest {
  @Test
  void build_shouldThrowExceptionIfNotConfigured() {
    assertThrows(NullPointerException.class, () -> {
      KnapsackProblemSolverBuilder.builder().build();
    });
  }

  @Test
  void build_shouldReturnConfiguredInstance() {
    final KnapsackProblemSolver instance = KnapsackProblemSolverBuilder.builder()
        .withStringWriter()
        .withPathReader()
        .withDynamicZeroOneStrategy()
        .build();

    assertNotNull(instance);
  }
}