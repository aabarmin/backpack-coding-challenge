package com.mobiquity.packer.service.impl;

import com.mobiquity.packer.model.BackpackItem;
import com.mobiquity.packer.model.PackingRequest;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.money.Monetary;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RequestLineReaderTest {
  @Mock
  private MoneyReader moneyReader;

  @InjectMocks
  private RequestLineReader unitUnderTest;

  @Test
  void readLine_exceptionIfStringDoesntMatchThePattern() {
    assertThrows(IllegalArgumentException.class, () -> {
      unitUnderTest.readLine("abc");
    });
  }

  @Test
  void readLine_exceptionIfItemDoesntMatchThePattern() {
    assertThrows(IllegalArgumentException.class, () -> {
      unitUnderTest.readLine("1:123");
    });
  }

  @ParameterizedTest
  @CsvSource(
      delimiter = '|',
      value = {
          "10:(1,2.0,€3)|10|1|2.0|3"
  })
  void readLine_shouldReadTheLine(final String inputLine,
                                  final int maxWeight,
                                  final int index,
                                  final double weight,
                                  final int amount) {

    when(moneyReader.readMoney(anyString())).thenReturn(
        Money.of(3, "EUR")
    );

    final PackingRequest request = unitUnderTest.readLine(inputLine);

    assertAll(
        () -> assertNotNull(request, "Request should not be null"),
        () -> assertEquals(maxWeight, request.getMaxWeight(), "Invalid max weight"),
        () -> assertEquals(1, request.getItems().size(), "Invalid number of items")
    );

    final BackpackItem item = request.getItems().iterator().next();

    assertAll(
        () -> assertNotNull(item, "Item should not be null"),
        () -> assertEquals(index, item.getIndex(), "Invalid index"),
        () -> assertEquals(weight, item.getWeight(), "Invalid item's weight"),
        () -> assertEquals(
            amount,
            item.getCost().getNumber().intValueExact(),
            "Invalid cost amount"
        ),
        () -> assertEquals(
            Monetary.getCurrency("EUR"),
            item.getCost().getCurrency(),
            "Invalid cost currency"
        )
    );
  }
}