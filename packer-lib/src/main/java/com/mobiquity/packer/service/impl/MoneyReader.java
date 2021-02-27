package com.mobiquity.packer.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.money.MonetaryAmount;
import org.javamoney.moneta.Money;

/**
 * This component parses money strings.
 */
public class MoneyReader {
  private final Pattern MONEY_PATTERN = Pattern.compile("(?<amount>[\\d.]+)");

  /**
   * Read an instance of the {@link MonetaryAmount} from a give string.
   * @param moneyString is a string which contains monetary data.
   * @return an instance of {@link MonetaryAmount}.
   */
  public MonetaryAmount readMoney(final String moneyString) {
    final Matcher matcher = MONEY_PATTERN.matcher(moneyString);
    if (!matcher.find()) {
      throw new IllegalArgumentException("Can't parse money string");
    }

    final double amount = Double.parseDouble(matcher.group("amount"));

    return Money.of(amount, "EUR");
  }
}
