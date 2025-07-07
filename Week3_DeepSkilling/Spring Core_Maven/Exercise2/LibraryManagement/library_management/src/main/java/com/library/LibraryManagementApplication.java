package com.library;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.library.service.BookService;

/**
 * Main application class to test Spring IoC and Dependency Injection
 */
public class LibraryManagementApplication {
    
    public static void main(String[] args) {
        System.out.println("=== Starting Library Management Application ===");
        
        // Load Spring Application Context from XML configuration
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println("Spring Application Context loaded successfully");
        
        // Get BookService bean from Spring container
        BookService bookService = (BookService) context.getBean("bookService");
        System.out.println("BookService bean retrieved from Spring container");
        
        System.out.println("\n=== Testing Dependency Injection ===");
        
        // Test the dependency injection by using BookService methods
        try {
            // Display initial library statistics
            bookService.displayLibraryStats();
            
            // Test adding a new book
            System.out.println("\n=== Adding New Books ===");
            bookService.addBook("Spring Boot in Action");
            bookService.addBook("Microservices Patterns");
            
            // Display updated statistics
            System.out.println("\n=== Updated Library Statistics ===");
            bookService.displayLibraryStats();
            
            // Test searching for books
            System.out.println("\n=== Searching for Books ===");
            String foundBook = bookService.searchBook("Spring");
            if (foundBook != null) {
                System.out.println("Found book: " + foundBook);
            } else {
                System.out.println("Book not found");
            }
            
            // Test searching for non-existent book
            String notFoundBook = bookService.searchBook("Python");
            if (notFoundBook != null) {
                System.out.println("Found book: " + notFoundBook);
            } else {
                System.out.println("Book 'Python' not found in library");
            }
            
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\n=== Dependency Injection Test Completed Successfully ===");
        System.out.println("BookService successfully used BookRepository through Spring DI");
        
        // Close the context (good practice)
        ((ClassPathXmlApplicationContext) context).close();
        System.out.println("Application Context closed");
    }
}