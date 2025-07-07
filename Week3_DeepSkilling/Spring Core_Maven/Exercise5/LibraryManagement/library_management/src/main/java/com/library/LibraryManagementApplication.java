package com.library;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.library.service.BookService;

/**
 * Main application class to demonstrate Spring IoC Container configuration
 */
public class LibraryManagementApplication {
    
    public static void main(String[] args) {
        System.out.println("=== Starting Library Management Application ===");
        
        try {
            // Load Spring Application Context from XML configuration
            System.out.println("\n1. Loading Spring Application Context...");
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            System.out.println("✓ Spring Context loaded successfully!");
            
            // Get BookService bean from the context
            System.out.println("\n2. Retrieving BookService bean from Spring context...");
            BookService bookService = context.getBean("bookService", BookService.class);
            System.out.println("✓ BookService bean retrieved successfully!");
            
            // Test the configuration by calling service methods
            System.out.println("\n3. Testing the Spring IoC Container configuration:");
            System.out.println("---------------------------------------------------");
            
            // Test 1: Get all books
            System.out.println("\n Test 1: Getting all books");
            System.out.println("Available books:");
            bookService.getAllBooks().forEach(book -> System.out.println("  - " + book));
            
            // Test 2: Add a new book
            System.out.println("\n Test 2: Adding a new book");
            bookService.addNewBook("Spring Security in Action");
            
            // Test 3: Search for a book
            System.out.println("\n Test 3: Searching for a book");
            boolean found = bookService.searchBook("Spring Framework in Action");
            System.out.println("Search result: " + (found ? "✓ Found" : "✗ Not Found"));
            
            // Test 4: Get library statistics
            System.out.println("\n Test 4: Getting library statistics");
            String stats = bookService.getLibraryStats();
            System.out.println(stats);
            
            // Test 5: Try to add duplicate book
            System.out.println("\n Test 5: Attempting to add duplicate book");
            bookService.addNewBook("Spring Framework in Action");
            
            // Test 6: Remove a book
            System.out.println("\n Test 6: Removing a book");
            boolean removed = bookService.removeBook("Java: The Complete Reference");
            System.out.println("Remove result: " + (removed ? "✓ Removed" : "✗ Not Found"));
            
            // Test 7: Final book count
            System.out.println("\n Test 7: Final library state");
            System.out.println("Final book list:");
            bookService.getAllBooks().forEach(book -> System.out.println("  - " + book));
            System.out.println(bookService.getLibraryStats());
            
            System.out.println("\n=== Application completed successfully! ===");
            System.out.println("✓ Spring IoC Container configuration is working correctly");
            System.out.println("✓ Dependency Injection (BookRepository into BookService) is working");
            System.out.println("✓ Bean lifecycle management is handled by Spring");
            
        } catch (Exception e) {
            System.err.println(" Error occurred while running the application:");
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}