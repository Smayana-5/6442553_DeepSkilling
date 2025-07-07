package com.library;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.library.service.BookService;
import com.library.repository.BookRepository;

/**
 * Main application class for LibraryManagement
 * This class demonstrates the Spring IoC container configuration
 */
public class LibraryManagementApplication {

    public static void main(String[] args) {
        System.out.println("=== Library Management Application Starting ===");
        
        try {
            // Load Spring Application Context from XML configuration
            ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
            
            System.out.println("Spring Application Context loaded successfully!");
            
            // Get beans from the Spring container
            BookService bookService = context.getBean("bookService", BookService.class);
            BookRepository bookRepository = context.getBean("bookRepository", BookRepository.class);
            
            // Test the beans
            System.out.println("\n=== Testing Spring Beans ===");
            System.out.println("BookService bean: " + bookService);
            System.out.println("BookRepository bean: " + bookRepository);
            
            // Test dependency injection
            if (bookService != null) {
                System.out.println("\n=== Testing Dependency Injection ===");
                bookService.performServiceOperation();
            }
            
            System.out.println("\n=== Application completed successfully! ===");
            
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}