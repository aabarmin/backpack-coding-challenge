package com.mobiquity.packer.service.impl;

import com.mobiquity.packer.model.PackingRequest;
import com.mobiquity.packer.model.PackingResult;
import com.mobiquity.packer.service.PackingSolver;
import com.mobiquity.packer.service.impl.strategy.PackingSolvingStrategy;
import java.util.Collection;
import java.util.stream.Collectors;

public class PackingSolverImpl implements PackingSolver {
  private final PackingSolvingStrategy strategy;

  public PackingSolverImpl(PackingSolvingStrategy strategy) {
    this.strategy = strategy;
  }

  @Override
  public Collection<PackingResult> solve(Collection<PackingRequest> requests) {
    return requests.stream()
        .map(strategy::solve)
        .collect(Collectors.toList());
  }
}
