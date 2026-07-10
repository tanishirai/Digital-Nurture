package com.cognizant.ormlearn.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// @Entity tells Spring Data JPA that this class maps to a database table.
// Hibernate (the JPA implementation) uses this to know it should manage
// the lifecycle of Country objects - loading, saving, updating, deleting.
@Entity

// @Table maps this class to the specific table named "country" in the DB.
// Without this, Hibernate would look for a table named "country" by default
// (same as class name in lowercase), but it's good practice to be explicit.
@Table(name = "country")
public class Country {

    // @Id marks this field as the primary key column
    @Id
    @Column(name = "co_code")
    private String code;

    // @Column maps this field to the "co_name" column in the table.
    // The field name (name) and column name (co_name) are different,
    // so we must specify the column name explicitly.
    @Column(name = "co_name")
    private String name;

    // Default no-arg constructor required by JPA/Hibernate
    public Country() {
    }

    public Country(String code, String name) {
        this.code = code;
        this.name = name;
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
