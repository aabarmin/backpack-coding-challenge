package com.mobiquity.packer.service.impl;

import com.mobiquity.packer.model.BackpackItem;
import com.mobiquity.packer.model.PackingResult;
import com.mobiquity.packer.service.PackingResultWriter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class StringPackingResultWriter implements PackingResultWriter {
  @Override
  public String write(Collection<PackingResult> results) {
    final StringBuilder builder = new StringBuilder();
    for (PackingResult result : results) {
      writeResult(builder, result);
      builder.append("\n");
    }
    return builder.toString();
  }

  private void writeResult(StringBuilder builder, PackingResult result) {
    if (result.getItems().isEmpty()) {
      builder.append("-");
    }
    final List<Integer> indexes = result.getItems().stream()
        .map(BackpackItem::getIndex)
        .collect(Collectors.toList());
    boolean isFirst = true;
    for (Integer index : indexes) {
      if (!isFirst) {
        builder.append(",");
      }
      isFirst = false;
      builder.append(index);
    }
//    builder.append(Joiner.on(",").join(indexes));
  }
}
