package com.mobiquity.packer.service.impl;

import com.mobiquity.packer.model.PackingRequest;
import com.mobiquity.packer.model.PackingResult;
import com.mobiquity.packer.service.impl.strategy.PackingSolvingStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PackingSolverImplTest {
  @Mock
  private PackingSolvingStrategy strategy;

  @InjectMocks
  private PackingSolverImpl unitUnderTest;

  @Test
  void solve_shouldUseStrategy() {
    final Collection<PackingResult> result = unitUnderTest.solve(List.of(
        new PackingRequest(10, List.of())
    ));

    verify(strategy, times(1)).solve(any(PackingRequest.class));
  }
}