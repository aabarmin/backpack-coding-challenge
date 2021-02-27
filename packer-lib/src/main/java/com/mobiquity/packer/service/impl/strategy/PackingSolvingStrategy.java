package com.mobiquity.packer.service.impl.strategy;

import com.mobiquity.packer.model.PackingRequest;
import com.mobiquity.packer.model.PackingResult;

public interface PackingSolvingStrategy {
  PackingResult solve(PackingRequest request);
}
