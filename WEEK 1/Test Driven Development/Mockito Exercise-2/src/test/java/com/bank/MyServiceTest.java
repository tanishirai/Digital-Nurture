package com.bank;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MyServiceTest {

    // ---- Test 1: exact solution from the exercise sheet ----
    // Creates a mock, calls fetchData() through the service,
    // then verifies that getData() was actually called on the mock.
    @Test
    public void testVerifyInteraction() {
        // Arrange
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        MyService service = new MyService(mockApi);

        // Act
        service.fetchData();

        // Assert - verify getData() was called on the mock (default = exactly once)
        verify(mockApi).getData();
    }

    // ---- Test 2: verify with a specific argument ----
    // This is what "verifying with specific arguments" actually means.
    // verify(mock).method(specificValue) checks that the method was called
    // with THAT exact value, not just any value.
    @Test
    public void testVerifyInteractionWithSpecificArgument() {
        // Arrange
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        when(mockApi.getDataById(101)).thenReturn("Account 101 Data");
        MyService service = new MyService(mockApi);

        // Act
        service.fetchDataById(101);

        // Assert - verify getDataById() was called specifically with argument 101
        verify(mockApi).getDataById(101);
    }

    // ---- Test 3: verify called exactly N times ----
    // times(n) lets you check how many times a method was called.
    // Useful when a service might call an API method in a loop or multiple times.
    @Test
    public void testVerifyCalledTwice() {
        // Arrange
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        MyService service = new MyService(mockApi);

        // Act - call fetchData twice
        service.fetchData();
        service.fetchData();

        // Assert - verify getData() was called exactly 2 times
        verify(mockApi, times(2)).getData();
    }

    // ---- Test 4: verify a method was NEVER called ----
    // never() checks that a method was not called at all.
    // Useful for making sure the service doesn't call the wrong API method.
    @Test
    public void testVerifyNeverCalled() {
        // Arrange
        ExternalApi mockApi = Mockito.mock(ExternalApi.class);
        MyService service = new MyService(mockApi);

        // Act - only fetchData() is called, not fetchDataById()
        service.fetchData();

        // Assert - getDataById() should never have been called
        verify(mockApi, never()).getDataById(Mockito.anyInt());
    }
}
