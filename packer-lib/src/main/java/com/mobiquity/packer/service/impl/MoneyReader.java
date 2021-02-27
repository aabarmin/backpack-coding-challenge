package com.mobiquity.packer.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.money.MonetaryAmount;
import org.javamoney.moneta.Money;

/**
 * This component parses money strings.
 */
public class MoneyReader {
  private Pattern moneyPattern = Pattern.compile("(?<amount>[\\d.]+)");

  /**
   * Read an instance of the {@link MonetaryAmount} from a give string.
   * @param moneyString is a string which contains monetary data.
   * @return an instance of {@link MonetaryAmount}.
   * @throws IllegalArgumentException in case if given string doesn't follow the money pattern.
   */
  public MonetaryAmount readMoney(final String moneyString) {
    final Matcher matcher = moneyPattern.matcher(moneyString);
    if (!matcher.find()) {
      throw new IllegalArgumentException("Can't parse money string");
    }

    final double amount = Double.parseDouble(matcher.group("amount"));

    return Money.of(amount, "EUR");
  }
}
