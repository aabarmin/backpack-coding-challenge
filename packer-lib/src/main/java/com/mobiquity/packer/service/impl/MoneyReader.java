package com.mobiquity.packer.service.impl;

import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This component parses money strings.
 */
public class MoneyReader {
  private final Pattern MONEY_PATTERN = Pattern.compile("(?<amount>[\\d.]+)");

  public MonetaryAmount readMoney(final String moneyString) {
    final Matcher matcher = MONEY_PATTERN.matcher(moneyString);
    if (!matcher.find()) {
      throw new IllegalArgumentException("Can't parse money string");
    }

    final double amount = Double.parseDouble(matcher.group("amount"));

    return Money.of(amount, "EUR");
  }
}
