package com.bank;

// The service we actually want to test.
// It depends on ExternalApi to get data, but we don't want
// to use the real ExternalApi in tests, so we inject it
// through the constructor (this is called Dependency Injection).
// That makes it easy to swap in a mock during testing.
public class MyService {

    private final ExternalApi externalApi;

    public MyService(ExternalApi externalApi) {
        this.externalApi = externalApi;
    }

    public String fetchData() {
        return externalApi.getData();
    }
}
