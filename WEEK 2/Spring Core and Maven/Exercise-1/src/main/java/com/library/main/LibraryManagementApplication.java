package com.library.main;

import com.library.service.BookService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.List;

public class LibraryManagementApplication {

    public static void main(String[] args) {

        System.out.println("=== Starting Spring Application Context ===");

        // Load the Spring context from applicationContext.xml
        // ClassPathXmlApplicationContext looks for the file on the classpath,
        // which includes src/main/resources/ after Maven builds the project.
        // At this point Spring reads the XML, creates both beans (BookRepository
        // first, then BookService), and injects BookRepository into BookService.
        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        System.out.println("=== Spring Context Loaded Successfully ===\n");

        // Retrieve the BookService bean from the context by its bean id
        BookService bookService = (BookService) context.getBean("bookService");

        // Test 1: fetch all books
        System.out.println("--- All Books in Library ---");
        List<String> books = bookService.getAllBooks();
        for (String book : books) {
            System.out.println("  > " + book);
        }

        // Test 2: add a new book through the service layer
        System.out.println("\n--- Adding a New Book ---");
        bookService.addBook("Effective Java - Joshua Bloch");

        // Test 3: confirm the new book appears in the list
        System.out.println("\n--- Updated Book List ---");
        for (String book : bookService.getAllBooks()) {
            System.out.println("  > " + book);
        }

        // Always close the context to release resources
        ((ClassPathXmlApplicationContext) context).close();
        System.out.println("\n=== Spring Context Closed ===");
    }
}
