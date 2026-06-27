package com.bank;

// External API interface - same as Exercise 1 but with an extra method
// that takes an argument, so we can properly demonstrate argument-specific verification.
public interface ExternalApi {

    String getData();

    String getDataById(int id);
}
