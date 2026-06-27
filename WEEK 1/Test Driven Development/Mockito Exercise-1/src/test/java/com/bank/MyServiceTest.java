package com.bank;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

public class MyServiceTest {

    // ---- Test 1: exact solution from the exercise sheet ----
    // Steps followed:
    // 1. Create a mock object for ExternalApi
    // 2. Stub getData() to return a predefined value
    // 3. Pass the mock into MyService and verify the result
    @Test
    public void testExternalApi() {
        // Arrange - create mock and stub it
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        when(mockApi.getData()).thenReturn("Mock Data");

        // Act - create the service using the mock, then call fetchData
        MyService service = new MyService(mockApi);
        String result = service.fetchData();

        // Assert - confirm the service returned exactly what the mock was told to return
        assertEquals("Mock Data", result);
    }

    // ---- Test 2: verify the mock was actually called ----
    // This uses Mockito's verify() to confirm that MyService
    // genuinely called externalApi.getData() and didn't skip it somehow.
    @Test
    public void testExternalApiIsCalled() {
        // Arrange
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        when(mockApi.getData()).thenReturn("Bank API Response");

        MyService service = new MyService(mockApi);

        // Act
        service.fetchData();

        // Assert - verify getData() was called exactly once on the mock
        verify(mockApi, Mockito.times(1)).getData();
    }

    // ---- Test 3: stub returning null (edge case) ----
    // By default, Mockito returns null for unstubbed methods.
    // This test explicitly stubs it to return null to show the behavior.
    @Test
    public void testExternalApiReturnsNull() {
        // Arrange
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        when(mockApi.getData()).thenReturn(null);

        MyService service = new MyService(mockApi);

        // Act
        String result = service.fetchData();

        // Assert
        assertNull(result);
    }
}
