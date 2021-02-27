package com.mobiquity.packer.service.impl.strategy;

import com.mobiquity.packer.model.BackpackItem;
import com.mobiquity.packer.model.PackingRequest;
import com.mobiquity.packer.model.PackingResult;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Solve a knapsack problem using a dynamic programming approach. You can read more about the
 * algorithm by the following link
 * <a href="https://en.wikipedia.org/wiki/Knapsack_problem#Dynamic_programming_in-advance_algorithm">Knapsack problem</a>.
 *
 * <p>
 * Because the source data is real numbers, it's necessary norming them by multiplying to 100.
 * This simple approach allows solving the knapsack problem using natural numbers.
 * </p>
 */
public class DynamicPackingSolvingStrategy implements PackingSolvingStrategy {
  private static final int SCALE_VALUE = 100;

  @Override
  public PackingResult solve(PackingRequest request) {
    final List<Item> items = toItems(request.getItems());
    int itemsCount = items.size();
    final int capacity = request.getMaxWeight() * SCALE_VALUE;

    // sorting items to have the most valuable first
    Collections.sort(items, Comparator.comparing(Item::getValue)
        .thenComparing(Item::getWeight));

    int[][] store = new int[itemsCount + 1][capacity + 1];
    for (int i = 0; i <= capacity; i++) {
      store[0][i] = 0;
    }

    // populating matrix with values for different cases
    for (int i = 1; i <= itemsCount; i++) {
      for (int j = 0; j <= capacity; j++) {
        final Item current = items.get(i - 1);
        if (current.weight > j) {
          store[i][j] = store[i - 1][j];
        } else {
          store[i][j] = Math.max(
              store[i - 1][j],
              store[i - 1][j - current.weight] + current.value
          );
        }
      }
    }

    // finding items to include into the backpack
    int currentValue = store[itemsCount][capacity];
    int currentCapacity = capacity;
    final List<Item> solution = new ArrayList<>();

    for (int i = itemsCount; i > 0 && currentValue > 0; i--) {
      if (currentValue != store[i - 1][currentCapacity]) {
        final Item current = items.get(i - 1);
        solution.add(current);
        currentValue -= current.value;
        currentCapacity -= current.weight;
      }
    }

    // converting back to backpack items
    return toResult(solution, request);
  }

  private PackingResult toResult(final Collection<Item> items,
                                 final PackingRequest request) {

    final List<BackpackItem> result = items.stream()
        .map(item -> item.index)
        .sorted()
        .map(index -> request.getItem(index))
        .collect(Collectors.toList());

    return new PackingResult(result);
  }

  private List<Item> toItems(Collection<BackpackItem> items) {
    return items.stream()
        .map(i -> new Item(
            i.getIndex(),
            (int) (i.getWeight() * SCALE_VALUE),
            i.getCost().getNumber().intValueExact() * SCALE_VALUE
        ))
        .collect(Collectors.toList());
  }

  static class Item {
    private final int index;
    private final int weight;
    private final int value;

    public Item(int index, int weight, int value) {
      this.index = index;
      this.weight = weight;
      this.value = value;
    }

    public int getWeight() {
      return weight;
    }

    public int getValue() {
      return value;
    }
  }
}
