package com.mobiquity.packer.service;

import com.mobiquity.packer.model.BackpackItem;
import com.mobiquity.packer.model.PackingRequest;
import com.mobiquity.packer.model.ValidationResult;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PackingRequestValidatorTest {
  @InjectMocks
  private PackingRequestValidator unitUnderTest;

  @Test
  void validate_checkInputParameters() {
    assertThrows(NullPointerException.class, () -> {
      unitUnderTest.validate(null);
    });
  }

  @ParameterizedTest
  @CsvSource({
      "-1,false",
      "100,true",
      "101,false",
      "99,true"
  })
  void validate_maxWeight(final int weight, final boolean expected) {
    final PackingRequest request = createRequest(weight);

    final ValidationResult result = unitUnderTest.validate(List.of(request));

    assertAll(
        () -> assertNotNull(result, "Result should not be null"),
        () -> assertEquals(expected, result.isValid(), "Invalid validation status")
    );
  }

  @ParameterizedTest
  @CsvSource({
      "14,true",
      "15,true",
      "16,false"
  })
  void validate_itemsAmount(final int amount, final boolean expected) {
    final PackingRequest request = createRequest(10, createItems(amount));

    final ValidationResult result = unitUnderTest.validate(List.of(request));

    assertAll(
        () -> assertNotNull(result, "Result should not be null"),
        () -> assertEquals(expected, result.isValid(), "Invalid validation status")
    );
  }

  @ParameterizedTest
  @CsvSource({
      "-1,false",
      "99,true",
      "100,true",
      "101,false"
  })
  void validate_itemWeight(final int weight, final boolean expected) {
    final PackingRequest request = createRequest(10, createItems(10, weight));

    final ValidationResult result = unitUnderTest.validate(List.of(request));

    assertAll(
        () -> assertNotNull(result, "Result should not be null"),
        () -> assertEquals(expected, result.isValid(), "Invalid validation status")
    );
  }

  @ParameterizedTest
  @CsvSource({
      "-1,false",
      "99,true",
      "100,true",
      "101,false"
  })
  void validate_itemCost(final int cost, final boolean expected) {
    final PackingRequest request = createRequest(10, createItems(10, 0, cost));

    final ValidationResult result = unitUnderTest.validate(List.of(request));

    assertAll(
        () -> assertNotNull(result, "Result should not be null"),
        () -> assertEquals(expected, result.isValid(), "Invalid validation status")
    );
  }

  private List<BackpackItem> createItems(final int number, final int weight, final int cost) {
    return IntStream.range(0, number)
        .mapToObj(i -> createItem(i, weight, cost))
        .collect(Collectors.toList());
  }

  private List<BackpackItem> createItems(final int number, final int weight) {
    return createItems(number, weight, 0);
  }

  private List<BackpackItem> createItems(final int number) {
    return createItems(number, 0);
  }

  private BackpackItem createItem(final int index, final int weight, final int cost) {
    return new BackpackItem(index, weight, Money.of(cost, "EUR"));
  }

  private PackingRequest createRequest(final int weight) {
    return createRequest(weight, List.of());
  }

  private PackingRequest createRequest(final int weight, final List<BackpackItem> items) {
    return new PackingRequest(weight, items);
  }
}