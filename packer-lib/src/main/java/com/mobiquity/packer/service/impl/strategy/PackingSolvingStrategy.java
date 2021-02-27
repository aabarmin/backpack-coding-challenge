package com.mobiquity.packer.service.impl.strategy;

import com.mobiquity.packer.model.PackingRequest;
import com.mobiquity.packer.model.PackingResult;

/**
 * An abstraction on a top of the Knapsack Problem solving approach. This interface allows having
 * multiple implementations of the solving algorithm and use them interchangeably.
 */
public interface PackingSolvingStrategy {
  PackingResult solve(PackingRequest request);
}
