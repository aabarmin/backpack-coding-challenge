package com.mobiquity.packer.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * A single request regarding items to be included into the backpack.
 */
public class PackingRequest {
  /**
   * Max weight of the backpack.
   */
  private final int maxWeight;
  /**
   * List of items to choose.
   */
  private final Map<Integer, BackpackItem> items = new TreeMap<>();

  /**
   * Create a packing request.
   * @param maxWeight of the backpack
   * @param items to choose.
   */
  public PackingRequest(final int maxWeight,
                        final List<BackpackItem> items) {
    this.maxWeight = maxWeight;
    items.forEach(i -> this.items.put(
        i.getIndex(),
        i
    ));
  }

  public BackpackItem getItem(final int index) {
    return items.get(index);
  }

  /**
   * Get max weight.
   * @return weight.
   */
  public int getMaxWeight() {
    return maxWeight;
  }

  /**
   * Get items.
   * @return a collection of items.
   */
  public List<BackpackItem> getItems() {
    return new ArrayList<>(items.values());
  }
}
