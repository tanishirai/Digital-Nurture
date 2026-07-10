package com.cognizant.ormlearn.service;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.repository.CountryRepository;
import com.cognizant.ormlearn.service.exception.CountryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// @Service marks this as a Spring-managed service bean.
// Spring will create one instance of this and inject it wherever needed.
@Service
public class CountryService {

    // @Autowired tells Spring to inject the CountryRepository bean here.
    // We don't call new CountryRepository() - Spring handles that.
    @Autowired
    private CountryRepository countryRepository;

    // @Transactional - Spring creates a Hibernate session, opens a transaction,
    // runs the method, then commits and closes the session automatically.
    // Without this, the Hibernate session would close before lazy-loaded
    // relationships could be accessed.

    // Hands on 1: Get all countries
    @Transactional
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    // Hands on 6: Find by country code
    @Transactional
    public Country findCountryByCode(String countryCode) throws CountryNotFoundException {
        Optional<Country> result = countryRepository.findById(countryCode);
        if (!result.isPresent()) {
            throw new CountryNotFoundException("Country not found for code: " + countryCode);
        }
        return result.get();
    }

    // Hands on 7: Add a new country
    @Transactional
    public void addCountry(Country country) {
        countryRepository.save(country);
    }

    // Hands on 8: Update a country's name by code
    @Transactional
    public void updateCountry(String code, String newName) throws CountryNotFoundException {
        Optional<Country> result = countryRepository.findById(code);
        if (!result.isPresent()) {
            throw new CountryNotFoundException("Country not found for code: " + code);
        }
        Country country = result.get();
        country.setName(newName);
        // save() on an existing entity = UPDATE in SQL (not INSERT)
        // because the primary key already exists in the DB
        countryRepository.save(country);
    }

    // Hands on 9: Delete a country by code
    @Transactional
    public void deleteCountry(String code) {
        countryRepository.deleteById(code);
    }

    // Hands on 5: Find countries matching partial name
    @Transactional
    public List<Country> findCountriesByPartialName(String keyword) {
        return countryRepository.findByNameContaining(keyword);
    }
}
