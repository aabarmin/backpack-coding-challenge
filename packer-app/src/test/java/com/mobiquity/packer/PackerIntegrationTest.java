package com.mobiquity.packer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PackerIntegrationTest {
  @ParameterizedTest
  @CsvSource({
      "/example_input,/example_output"
  })
  void pack(final String inputFilepathPart, final String outputFilepath) throws Exception {
    final String inputFilepath = getInputFilepath(inputFilepathPart);
    final String outputContent = getOutputContent(outputFilepath);
    final String result = Packer.pack(inputFilepath);

    assertAll(
        () -> assertNotNull(result, "Result should not be null"),
        () -> assertEquals(outputContent, result, "Invalid result")
    );
  }

  private String getInputFilepath(String inputFilepathPart) {
    return getClass().getResource(inputFilepathPart).getFile();
  }

  private String getOutputContent(String outputFilepath) {
    try (final BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(outputFilepath)))) {
      final StringBuilder builder = new StringBuilder();
      String s;
      while ((s = reader.readLine()) != null) {
        builder.append(s).append("\n");
      }
      return builder.toString();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}