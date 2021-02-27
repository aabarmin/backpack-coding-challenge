package com.mobiquity.packer.service;

import com.mobiquity.packer.model.PackingResult;
import java.util.Collection;

/**
 * Convert a packing result to the string.
 */
public interface PackingResultWriter {
  String write(Collection<PackingResult> results);
}
