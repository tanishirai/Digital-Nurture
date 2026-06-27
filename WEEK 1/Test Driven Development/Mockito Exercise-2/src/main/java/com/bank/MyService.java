package com.bank;

public class MyService {

    private final ExternalApi externalApi;

    public MyService(ExternalApi externalApi) {
        this.externalApi = externalApi;
    }

    // Calls getData() on the API - no arguments
    public String fetchData() {
        return externalApi.getData();
    }

    // Calls getDataById() on the API - passes a specific ID argument
    public String fetchDataById(int id) {
        return externalApi.getDataById(id);
    }
}
