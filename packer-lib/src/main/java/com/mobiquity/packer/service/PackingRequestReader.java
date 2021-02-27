package com.mobiquity.packer.service;

import com.mobiquity.packer.model.PackingRequest;

import java.nio.file.Path;
import java.util.Collection;

/**
 * A component that provides requests to check.
 */
public interface PackingRequestReader {
  /**
   * Read packing requests from the supported source.
   * @param source is a source of data
   * @return a collection of packing requests.
   */
  Collection<PackingRequest> readRequests(Path source);
}
