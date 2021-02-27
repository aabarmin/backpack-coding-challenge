package com.mobiquity.packer.service;

import com.mobiquity.packer.model.PackingRequest;
import com.mobiquity.packer.model.PackingResult;

import java.util.Collection;

public interface PackingSolver {
  Collection<PackingResult> solve(Collection<PackingRequest> requests);
}
