package com.mobiquity.packer.service.impl;

import com.mobiquity.packer.model.BackpackItem;
import com.mobiquity.packer.model.PackingResult;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StringPackingResultWriterTest {
  @InjectMocks
  private StringPackingResultWriter unitUnderTest;

  @ParameterizedTest
  @MethodSource("data")
  void write_shouldGenerateString(TestCase testCase) {
    final String output = unitUnderTest.write(List.of(
        createResult(testCase.items)
    ));

    assertAll(
        () -> assertNotNull(output, "Output should not be null"),
        () -> assertEquals(testCase.output, output, "Output data is incorrect")
    );
  }

  public static Stream<Arguments> data() {
    return Stream.of(
        Arguments.of(new TestCase("1,2,3,4\n", 1, 2, 3, 4)),
        Arguments.of(new TestCase("-\n"))
    );
  }

  private PackingResult createResult(int... items) {
    return new PackingResult(Arrays.stream(items)
        .mapToObj(item -> new BackpackItem(
            item,
            0,
            Money.of(0, "EUR")
        ))
        .collect(Collectors.toList())
    );
  }

  static class TestCase {
    private final String output;
    private final int[] items;

    public TestCase(String output, int... items) {
      this.output = output;
      this.items = items;
    }
  }
}