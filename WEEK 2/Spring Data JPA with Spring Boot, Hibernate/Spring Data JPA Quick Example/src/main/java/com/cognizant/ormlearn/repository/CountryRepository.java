package com.cognizant.ormlearn.repository;

import com.cognizant.ormlearn.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

// JpaRepository<Country, String> gives us all standard CRUD methods for free:
// findAll(), findById(), save(), deleteById(), count(), existsById() etc.
// The second type parameter (String) is the type of the primary key (co_code).
// We don't need to write any SQL or implementation - Spring Data JPA generates it.
@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

    // Query method - Spring Data JPA derives the SQL from the method name.
    // "findByNameContaining" -> SELECT * FROM country WHERE co_name LIKE '%keyword%'
    // No @Query annotation needed - naming convention is enough.
    List<Country> findByNameContaining(String keyword);

    // Custom JPQL query using @Query for more complex cases.
    // JPQL works on entity class names and field names (not table/column names).
    @Query("SELECT c FROM Country c WHERE c.name LIKE %:keyword%")
    List<Country> searchByName(String keyword);
}
