# Difference between JPA, Hibernate and Spring Data JPA

## Overview

In the context of Java-based database interaction, three terms are frequently used together — JPA, Hibernate, and Spring Data JPA. While they are related, each serves a distinct purpose. This document explains what each one is, how they differ, and why all three are used together in a typical Spring Boot application.

---

## 1. Java Persistence API (JPA)

JPA stands for **Java Persistence API**. It is a **specification** defined under **JSR 338** (Java Specification Request 338). A specification means it only defines the rules, interfaces, and annotations — it does not contain any actual implementation.

JPA defines standards such as:
- `@Entity` — to mark a Java class as a database-mapped entity
- `@Table` — to specify the mapped table name
- `@Id` — to mark the primary key field
- `@Column` — to map a field to a specific column
- JPQL (Java Persistence Query Language) — for writing database queries using object names instead of table names

JPA on its own cannot connect to a database, execute SQL, or manage sessions. It is simply the contract that all JPA implementations must follow.

**In short:** JPA defines *what* needs to happen. It does not define *how*.

---

## 2. Hibernate

Hibernate is an **ORM (Object-Relational Mapping) framework** and one of the most widely used **implementations of the JPA specification**. Other implementations include EclipseLink and OpenJPA, but Hibernate is the default used by Spring Boot.

Since Hibernate implements JPA, all JPA annotations work with Hibernate. Additionally, Hibernate has its own features beyond the JPA spec, such as HQL (Hibernate Query Language) and advanced caching mechanisms.

### What Hibernate does:
- Maps Java classes to relational database tables
- Generates and executes SQL queries at runtime
- Manages database sessions and connections
- Handles transaction lifecycle (begin, commit, rollback)

### Hibernate without Spring — boilerplate code required:

When using Hibernate directly (without Spring), all session and transaction management must be written manually for every operation:

```java
public Integer addEmployee(Employee employee) {
    Session session = factory.openSession();
    Transaction tx = null;
    Integer employeeID = null;

    try {
        tx = session.beginTransaction();
        employeeID = (Integer) session.save(employee);
        tx.commit();
    } catch (HibernateException e) {
        if (tx != null) tx.rollback();
        e.printStackTrace();
    } finally {
        session.close();
    }
    return employeeID;
}
```

Every database operation requires: opening a session, beginning a transaction, executing the operation, committing, handling rollback on error, and closing the session. This results in a significant amount of repetitive code across the application.

**In short:** Hibernate does the actual work of talking to the database. But it requires significant boilerplate to use directly.

---

## 3. Spring Data JPA

Spring Data JPA is **not a JPA implementation**. It does not replace Hibernate. Instead, it is an **abstraction layer built on top of JPA (and by extension, Hibernate)** that eliminates the repetitive boilerplate code required when working with Hibernate directly.

### What Spring Data JPA provides:
- **`JpaRepository` interface** — provides built-in CRUD methods (`findAll()`, `findById()`, `save()`, `deleteById()`) without writing any implementation
- **Query method derivation** — method names like `findByNameContaining(String keyword)` are automatically converted to SQL by Spring Data JPA
- **`@Query` annotation** — for writing custom JPQL or native SQL queries when needed
- **`@Transactional` integration** — transaction management is handled automatically by Spring, no manual session handling required

### Same operation using Spring Data JPA:

```java
// Repository interface — no implementation class needed
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}

// Service class
@Autowired
private EmployeeRepository employeeRepository;

@Transactional
public void addEmployee(Employee employee) {
    employeeRepository.save(employee);
}
```

Spring Data JPA internally delegates to Hibernate, which generates and executes the SQL. However, none of that session and transaction boilerplate needs to be written in the application code.

**In short:** Spring Data JPA removes the boilerplate from Hibernate and makes data access code concise and readable.

---

## Comparison Table

| | JPA | Hibernate | Spring Data JPA |
|---|---|---|---|
| Type | Specification (JSR 338) | ORM framework / JPA implementation | Abstraction layer over JPA |
| Connects to database | No | Yes | No (uses Hibernate) |
| Generates SQL | No | Yes | No (Hibernate does) |
| Session management | Defined in spec only | Written manually by developer | Handled via `@Transactional` |
| Boilerplate code | High (no implementation) | High | Minimal |
| Repository pattern | Not provided | Not provided | `JpaRepository` interface |
| Query from method name | No | No | Yes |

---

## Code Comparison Summary

| Task | Plain Hibernate | Spring Data JPA |
|---|---|---|
| Add employee | ~15 lines (session, tx, try-catch, close) | 1 line: `repository.save(employee)` |
| Find by ID | Manual session + `session.get()` | `repository.findById(id)` |
| Delete | Manual session + `session.delete()` | `repository.deleteById(id)` |
| Transaction | Manual begin/commit/rollback | `@Transactional` on method |

---

## Why All Three Are Used Together

Each layer has a distinct responsibility:

- **JPA** provides portability — code written using JPA annotations works with any JPA-compliant provider (Hibernate, EclipseLink, etc.) without modification.
- **Hibernate** handles the actual database interaction — it generates SQL, manages connections, and performs the ORM mapping.
- **Spring Data JPA** handles developer productivity — it removes repetitive code so developers can focus on business logic rather than infrastructure.

In a standard Spring Boot project, all three are present simultaneously. The developer writes code against Spring Data JPA interfaces, Spring Data JPA coordinates with Hibernate, and Hibernate follows the JPA specification to interact with the database.

---

## Summary

| | One-line definition |
|---|---|
| **JPA** | Specification that defines how Java objects should be persisted — no implementation |
| **Hibernate** | ORM framework that implements JPA and performs actual database operations |
| **Spring Data JPA** | Abstraction over Hibernate that removes boilerplate and simplifies data access code |