package com.mobiquity.packer.service;

import com.mobiquity.packer.exception.APIException;
import com.mobiquity.packer.model.ValidationResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KnapsackProblemSolverTest {
  @Mock
  private PackingRequestReader requestReader;

  @Mock
  private PackingRequestValidator requestValidator;

  @Mock
  private PackingSolver packingSolver;

  @Mock
  private PackingResultWriter resultWriter;

  @InjectMocks
  private KnapsackProblemSolver unitUnderTest;

  @Test
  void solve_shouldHaveProperInvocationOrder() throws Exception {
    when(requestValidator.validate(anyCollection())).thenReturn(ValidationResult.success());

    final String result = unitUnderTest.pack(Path.of(""));

    final InOrder ordered = inOrder(requestReader, requestValidator, packingSolver, resultWriter);

    ordered.verify(requestReader).readRequests(any(Path.class));
    ordered.verify(requestValidator).validate(anyCollection());
    ordered.verify(packingSolver).solve(anyCollection());
    ordered.verify(resultWriter).write(anyCollection());
  }

  @Test
  void solve_exceptionInCaseOfViolation() {
    when(requestValidator.validate(anyCollection())).thenReturn(ValidationResult.error(""));

    assertThrows(APIException.class, () -> {
      unitUnderTest.pack(Path.of(""));
    });
  }
}