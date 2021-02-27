package com.mobiquity.packer.model;

import javax.money.MonetaryAmount;

public class BackpackItem {
  private final int index;
  private final double weight;
  private final MonetaryAmount cost;

  /**
   * Create a backpack item to choose.
   * @param index of the item.
   * @param weight of the item.
   * @param cost of the item.
   */
  public BackpackItem(final int index, final double weight, final MonetaryAmount cost) {
    this.index = index;
    this.weight = weight;
    this.cost = cost;
  }

  public int getIndex() {
    return index;
  }

  public double getWeight() {
    return weight;
  }

  public MonetaryAmount getCost() {
    return cost;
  }
}
