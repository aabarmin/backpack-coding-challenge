package com.mobiquity.packer.service.impl;

import com.mobiquity.packer.model.BackpackItem;
import com.mobiquity.packer.model.PackingRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PackingRequestReaderImplTest {
  @Mock
  private RequestLineReader lineReader;

  @InjectMocks
  private PackingRequestReaderImpl unitUnderTest;

  @Test
  void readRequest_shouldBeAnExceptionIfNoFile() {
    assertThrows(NullPointerException.class, () -> {
      unitUnderTest.readRequests(null);
    });
  }

  @Test
  void readRequest_shouldBeAnExceptionIfFileDoesntExist() {
    assertThrows(RuntimeException.class, () -> {
      unitUnderTest.readRequests(Path.of("random"));
    });
  }

  @Test
  void readRequest_readRequestsFromFile() throws Exception {
    final Path tempFile = Files.createTempFile("backpack_", "source.xml");
    Files.writeString(tempFile, "81 : (1,53.38,€45)");

    when(lineReader.readLine(anyString())).thenReturn(new PackingRequest(81, List.of(
        new BackpackItem(1, 53.38, Money.of(45, "EUR"))
    )));

    final Collection<PackingRequest> requests = unitUnderTest.readRequests(tempFile);

    assertAll(
        () -> assertNotNull(requests, "Requests should not be null"),
        () -> assertEquals(1, requests.size(), "Invalid number of requests")
    );

    final PackingRequest request = requests.iterator().next();

    assertAll(
        () -> assertNotNull(request, "Request should not be null"),
        () -> assertEquals(81, request.getMaxWeight(), "Invalid max weight"),
        () -> assertEquals(1, request.getItems().size(), "Invalid number of items")
    );
  }
}