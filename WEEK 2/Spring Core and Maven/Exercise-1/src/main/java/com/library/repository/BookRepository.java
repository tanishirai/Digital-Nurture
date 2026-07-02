package com.library.repository;

import java.util.ArrayList;
import java.util.List;

// BookRepository is responsible for data access - in a real app this would
// talk to a database. Here we're using a simple in-memory list to keep things
// focused on the Spring configuration rather than database setup.
public class BookRepository {

    private List<String> books;

    public BookRepository() {
        // Simulating a pre-loaded book catalog
        books = new ArrayList<>();
        books.add("Clean Code - Robert C. Martin");
        books.add("The Pragmatic Programmer - Andrew Hunt");
        books.add("Design Patterns - Gang of Four");
        System.out.println("BookRepository bean created by Spring.");
    }

    public List<String> findAllBooks() {
        return books;
    }

    public void addBook(String title) {
        books.add(title);
        System.out.println("Book added to repository: " + title);
    }
}
