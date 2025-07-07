package com.library.service;

// rest of your code

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.library.repository.BookRepository;

/**
 * Main application class to test Spring configuration
 */
public class LibraryManagementApplication {
    
    public static void main(String[] args) {
        System.out.println("Starting Library Management Application...\n");
        
        try {
            // Load Spring context from XML configuration
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            System.out.println("Spring Application Context loaded successfully!\n");
            
            // Get beans from Spring container
            BookRepository bookRepository = (BookRepository) context.getBean("bookRepository");
            BookService bookService = (BookService) context.getBean("bookService");
            
            System.out.println("Beans retrieved from Spring container:");
            System.out.println("BookRepository: " + (bookRepository != null ? "✓ Initialized" : "✗ Failed"));
            System.out.println("BookService: " + (bookService != null ? "✓ Initialized" : "✗ Failed"));
            System.out.println();
            
            // Test the BookRepository directly
            System.out.println("=== Testing BookRepository ===");
            System.out.println("Available books in repository:");
            bookRepository.getAllBooks().forEach(book -> System.out.println("- " + book));
            System.out.println();
            
            // Test the BookService
            System.out.println("=== Testing BookService ===");
            bookService.displayLibraryStats();
            
            // Test adding a new book through service
            System.out.println("=== Adding New Book ===");
            bookService.addBook("Spring Boot in Action");
            
            // Test searching for books
            System.out.println("=== Testing Book Search ===");
            String searchTitle = "Java Programming";
            boolean found = bookService.searchBook(searchTitle);
            System.out.println("Search result for '" + searchTitle + "': " + (found ? "Found" : "Not Found"));
            
            // Display updated statistics
            bookService.displayLibraryStats();
            
            System.out.println("Library Management Application completed successfully!");
            
        } catch (Exception e) {
            System.err.println("Error occurred while starting the application:");
            e.printStackTrace();
        }
    }
}