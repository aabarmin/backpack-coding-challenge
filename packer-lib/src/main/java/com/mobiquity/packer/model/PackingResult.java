package com.mobiquity.packer.model;

import java.util.Collection;
import java.util.Collections;

public class PackingResult {
  private final Collection<BackpackItem> items;

  public PackingResult(Collection<BackpackItem> items) {
    this.items = items;
  }

  public Collection<BackpackItem> getItems() {
    return Collections.unmodifiableCollection(items);
  }
}
