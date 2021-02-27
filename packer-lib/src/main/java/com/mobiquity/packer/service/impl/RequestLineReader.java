package com.mobiquity.packer.service.impl;

import com.mobiquity.packer.model.BackpackItem;
import com.mobiquity.packer.model.PackingRequest;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.money.MonetaryAmount;

/**
 * Reads a single packing request. Every line should follow the pattern:
 *
 * weight : items
 *
 * If the line doesn't follow this pattern, an {@link IllegalArgumentException} is thrown.
 */
public class RequestLineReader {
  private static Pattern LINE_PATTERN =
      Pattern.compile("(?<maxWeight>\\d+)(\\s+)?:(\\s+)?(?<items>.+)");
  private static Pattern ITEM_PATTERN =
      Pattern.compile("\\((?<index>\\d+),(?<weight>[\\d.]+),(?<money>.+)\\)");

  private final MoneyReader moneyReader;

  public RequestLineReader(MoneyReader moneyReader) {
    this.moneyReader = moneyReader;
  }

  public PackingRequest readLine(final String line) {
    final Matcher matcher = LINE_PATTERN.matcher(line);
    if (!matcher.matches()) {
      throw new IllegalArgumentException("Input line doesn't match the pattern");
    }

    final int maxWeight = Integer.parseInt(matcher.group("maxWeight"));
    final String itemsString = matcher.group("items");

    final String[] items = itemsString.split("\\s+");

    return new PackingRequest(
        maxWeight,
        Arrays.stream(items)
          .map(this::readItem)
          .collect(Collectors.toList())
    );
  }

  private BackpackItem readItem(final String itemString) {
    final Matcher matcher = ITEM_PATTERN.matcher(itemString);
    if (!matcher.matches()) {
      throw new IllegalArgumentException("Item doesn't match the pattern");
    }

    final int index = Integer.parseInt(matcher.group("index"));
    final double weight = Double.parseDouble(matcher.group("weight"));
    final String moneyString = matcher.group("money");
    final MonetaryAmount cost = moneyReader.readMoney(moneyString);

    return new BackpackItem(
        index, weight, cost
    );
  }
}
