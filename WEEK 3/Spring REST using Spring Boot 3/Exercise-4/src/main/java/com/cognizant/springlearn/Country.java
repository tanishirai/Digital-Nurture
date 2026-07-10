package com.cognizant.springlearn;

// Country is a plain Java class (POJO).
// When the controller returns a Country object, Spring Boot's
// Jackson library reads the getter methods and converts the object
// into JSON automatically:
// getCode() -> "code": "IN"
// getName() -> "name": "India"
// Result: { "code": "IN", "name": "India" }
public class Country {

    private String code;
    private String name;

    // Default no-arg constructor required for Spring XML bean instantiation
    public Country() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Country{code='" + code + "', name='" + name + "'}";
    }
}
