package com.bank;

// Represents an external API the service depends on.
// In real life this would call some third-party REST endpoint,
// a payment gateway, an SMS provider, etc.
// We never want to call the real thing during unit tests,
// so Mockito creates a fake stand-in for it instead.
public interface ExternalApi {

    String getData();
}
