package com.library.service;

import com.library.repository.BookRepository;
import java.util.List;

// BookService contains the business logic for the library application.
// It depends on BookRepository to access data, but instead of creating
// the repository itself (new BookRepository()), Spring injects it through
// the constructor. This is Dependency Injection - BookService doesn't need
// to know how BookRepository is created or configured.
public class BookService {

    private final BookRepository bookRepository;

    // Spring sees the constructor-arg in applicationContext.xml and calls
    // this constructor, passing in the bookRepository bean it already created.
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("BookService bean created by Spring. BookRepository injected successfully.");
    }

    public List<String> getAllBooks() {
        return bookRepository.findAllBooks();
    }

    public void addBook(String title) {
        bookRepository.addBook(title);
    }
}
