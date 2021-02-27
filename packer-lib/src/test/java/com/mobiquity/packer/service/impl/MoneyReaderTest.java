package com.mobiquity.packer.service.impl;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MoneyReaderTest {
  @InjectMocks
  private MoneyReader unitUnderTest;

  @ParameterizedTest
  @CsvSource({
      "€45,45,EUR",
      "45$,45,EUR"
  })
  void readMoney_regularTest(final String inputString,
                             final int amount,
                             final String currency) {

    final MonetaryAmount monetaryAmount = unitUnderTest.readMoney(inputString);

    assertAll(
        () -> assertNotNull(monetaryAmount, "Result should not be null"),
        () -> assertEquals(
            amount,
            monetaryAmount.getNumber().intValueExact(),
            "Invalid money amount"
        ),
        () -> assertEquals(
            Monetary.getCurrency(currency),
            monetaryAmount.getCurrency(),
            "Invalid currency"
        )
    );
  }
}