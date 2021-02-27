package com.mobiquity.packer.service;

import com.mobiquity.packer.model.PackingResult;

import java.util.Collection;

public interface PackingResultWriter {
  String write(Collection<PackingResult> results);
}
