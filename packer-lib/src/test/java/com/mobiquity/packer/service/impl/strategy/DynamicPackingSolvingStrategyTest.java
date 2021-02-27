package com.mobiquity.packer.service.impl.strategy;

import com.google.gson.Gson;
import com.mobiquity.packer.model.BackpackItem;
import com.mobiquity.packer.model.PackingRequest;
import com.mobiquity.packer.model.PackingResult;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DynamicPackingSolvingStrategyTest {
  private static Gson GSON = new Gson();

  @InjectMocks
  private DynamicPackingSolvingStrategy unitUnderTest;

  @MethodSource("parameters")
  @ParameterizedTest
  void solve(TestCase testCase) {
    final PackingRequest request = createRequest(testCase);

    final PackingResult solution = unitUnderTest.solve(request);
    final Set<Integer> items = solution.getItems().stream()
        .map(BackpackItem::getIndex)
        .collect(Collectors.toSet());

    assertAll(
        () -> assertNotNull(solution, "The solution should not be null"),
        () -> assertEquals(testCase.output, items, "Invalid solution")
    );
  }

  private PackingRequest createRequest(TestCase testCase) {
    return new PackingRequest(
        testCase.input.maxWeight,
        testCase.input.items.stream()
            .map(line -> new BackpackItem(
                line.index,
                line.weight,
                Money.of(line.cost, "EUR")
            ))
            .collect(Collectors.toList())
    );
  }

  public static Stream<Arguments> parameters() {
    return Stream
        .of(
            "/samples/sample1.json",
            "/samples/sample2.json",
            "/samples/sample3.json",
            "/samples/sample4.json"
        )
        .map(path -> readCase(path))
        .map(testCase -> Arguments.of(testCase));
  }

  private static TestCase readCase(final String path) {
    try (final InputStream resource = DynamicPackingSolvingStrategyTest.class.getResourceAsStream(path);
         final Reader reader = new InputStreamReader(resource, StandardCharsets.UTF_8)) {

      return GSON.fromJson(reader, TestCase.class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  static class TestCase {
    private CaseInput input;
    private Set<Integer> output;
  }

  static class CaseInput {
    private int maxWeight;
    private List<CaseItem> items;
  }

  static class CaseItem {
    private int index;
    private double weight;
    private int cost;
  }
}