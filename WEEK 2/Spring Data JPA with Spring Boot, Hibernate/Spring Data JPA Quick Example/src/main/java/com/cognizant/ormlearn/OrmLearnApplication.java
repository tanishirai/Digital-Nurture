package com.cognizant.ormlearn;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.service.CountryService;
import com.cognizant.ormlearn.service.exception.CountryNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

// @SpringBootApplication is a combination of three annotations:
// @Configuration - marks this as a source of bean definitions
// @EnableAutoConfiguration - tells Spring Boot to auto-configure based on classpath
// @ComponentScan - scans this package and sub-packages for Spring beans (@Service, @Repository etc.)
@SpringBootApplication
public class OrmLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

    // Static reference so test methods (which are static) can access the service
    private static CountryService countryService;

    public static void main(String[] args) {
        // SpringApplication.run() boots the Spring context, connects to DB,
        // runs Hibernate validation against the schema, and returns the context.
        ApplicationContext context = SpringApplication.run(OrmLearnApplication.class, args);

        // Get the CountryService bean from the context
        countryService = context.getBean(CountryService.class);

        LOGGER.info("Inside main");

        // Run all test methods in sequence
        testGetAllCountries();
        testFindCountryByCode();
        testAddCountry();
        testUpdateCountry();
        testDeleteCountry();
        testFindByPartialName();
    }

    // Hands on 1: Get all countries
    private static void testGetAllCountries() {
        LOGGER.info("Start - testGetAllCountries");
        List<Country> countries = countryService.getAllCountries();
        LOGGER.debug("Total countries fetched: {}", countries.size());
        countries.forEach(c -> LOGGER.debug("  {}", c));
        LOGGER.info("End - testGetAllCountries");
    }

    // Hands on 6: Find by country code
    private static void testFindCountryByCode() {
        LOGGER.info("Start - testFindCountryByCode");
        try {
            Country country = countryService.findCountryByCode("IN");
            LOGGER.debug("Country found: {}", country);
        } catch (CountryNotFoundException e) {
            LOGGER.error("Country not found: {}", e.getMessage());
        }
        LOGGER.info("End - testFindCountryByCode");
    }

    // Hands on 7: Add new country
    private static void testAddCountry() {
        LOGGER.info("Start - testAddCountry");
        Country newCountry = new Country("XX", "TestLand");
        countryService.addCountry(newCountry);
        LOGGER.debug("Country added: {}", newCountry);

        // Verify it was actually saved by fetching it back
        try {
            Country fetched = countryService.findCountryByCode("XX");
            LOGGER.debug("Verified in DB: {}", fetched);
        } catch (CountryNotFoundException e) {
            LOGGER.error("Verification failed - country not found after add");
        }
        LOGGER.info("End - testAddCountry");
    }

    // Hands on 8: Update country name
    private static void testUpdateCountry() {
        LOGGER.info("Start - testUpdateCountry");
        try {
            countryService.updateCountry("XX", "TestLand Updated");
            Country updated = countryService.findCountryByCode("XX");
            LOGGER.debug("Updated country: {}", updated);
        } catch (CountryNotFoundException e) {
            LOGGER.error("Update failed: {}", e.getMessage());
        }
        LOGGER.info("End - testUpdateCountry");
    }

    // Hands on 9: Delete country
    private static void testDeleteCountry() {
        LOGGER.info("Start - testDeleteCountry");
        countryService.deleteCountry("XX");
        LOGGER.debug("Country XX deleted");

        // Verify deletion
        try {
            countryService.findCountryByCode("XX");
            LOGGER.error("Country still exists - delete may have failed");
        } catch (CountryNotFoundException e) {
            LOGGER.debug("Confirmed: country XX no longer exists in DB");
        }
        LOGGER.info("End - testDeleteCountry");
    }

    // Hands on 5: Find by partial name
    private static void testFindByPartialName() {
        LOGGER.info("Start - testFindByPartialName");
        List<Country> results = countryService.findCountriesByPartialName("land");
        LOGGER.debug("Countries matching 'land': {}", results.size());
        results.forEach(c -> LOGGER.debug("  {}", c));
        LOGGER.info("End - testFindByPartialName");
    }
}
