package com.cognizant.ormlearn.service.exception;

// Custom checked exception thrown when a country is looked up
// by code but doesn't exist in the database.
// Extending Exception (not RuntimeException) so callers are
// forced to handle or declare it.
public class CountryNotFoundException extends Exception {

    public CountryNotFoundException(String message) {
        super(message);
    }
}
