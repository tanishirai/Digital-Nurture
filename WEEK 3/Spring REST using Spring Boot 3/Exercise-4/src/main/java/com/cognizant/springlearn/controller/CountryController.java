package com.cognizant.springlearn.controller;

import com.cognizant.springlearn.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @RestController = @Controller + @ResponseBody
// @Controller marks this class as a Spring MVC controller (handles HTTP requests)
// @ResponseBody tells Spring to convert the return value directly to JSON/XML
// instead of treating it as a view name to look up
// The two combined = every method returns the response body directly as JSON
@RestController
public class CountryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);

    // @RequestMapping maps HTTP requests to this method.
    // Any HTTP request to GET /country will be handled here.
    // The method name getCountryIndia() is as specified in the exercise.
    @RequestMapping("/country")
    public Country getCountryIndia() {
        LOGGER.debug("Start - getCountryIndia");

        // Load the Spring XML configuration file from the classpath
        // (src/main/resources/country.xml is on the classpath after build)
        ApplicationContext context =
                new ClassPathXmlApplicationContext("country.xml");

        // Retrieve the "india" bean by its id defined in country.xml
        // Spring reads the XML, creates a Country object with code="IN"
        // and name="India", and returns it here
        Country country = (Country) context.getBean("india");

        LOGGER.debug("Country loaded from XML: {}", country);
        LOGGER.debug("End - getCountryIndia");

        // Spring Boot's Jackson library intercepts this return value,
        // reads each getter method on the Country object, and converts
        // it to JSON: { "code": "IN", "name": "India" }
        // This JSON is then written to the HTTP response body
        // with Content-Type: application/json header set automatically
        return country;
    }
}
