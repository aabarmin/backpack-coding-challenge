package com.mobiquity.packer.service.impl;

import com.mobiquity.packer.model.PackingRequest;
import com.mobiquity.packer.service.PackingRequestReader;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * File path request reader.
 */
public class PackingRequestReaderImpl implements PackingRequestReader {
  private final RequestLineReader lineReader;

  public PackingRequestReaderImpl(RequestLineReader lineReader) {
    this.lineReader = lineReader;
  }

  @Override
  public Collection<PackingRequest> readRequests(final Path filepath) {
    Objects.requireNonNull(filepath, "Filepath should not be null");

    if (!Files.exists(filepath)) {
      throw new RuntimeException("File doesn't exist");
    }

    try (final Stream<String> lines = Files.lines(filepath, StandardCharsets.UTF_8)) {
      return lines.map(lineReader::readLine)
          .collect(Collectors.toList());
    } catch (Exception e) {
      throw new RuntimeException("Can't read source file", e);
    }
  }
}
