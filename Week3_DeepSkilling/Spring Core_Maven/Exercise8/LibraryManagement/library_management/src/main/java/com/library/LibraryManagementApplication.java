package com.library;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.library.service.BookService;

public class LibraryManagementApplication {
    
    public static void main(String[] args) {
        // Load the Spring context
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        // Get the BookService bean
        BookService bookService = context.getBean(BookService.class);
        
        System.out.println("=== Library Management Application Started ===\n");
        
        // Test the AOP functionality by calling service methods
        System.out.println("1. Testing addBook method:");
        bookService.addBook("Spring in Action", "Craig Walls");
        
        System.out.println("\n2. Testing findBook method:");
        bookService.findBook("Spring in Action");
        
        System.out.println("\n3. Testing getAllBooks method:");
        bookService.getAllBooks();
        
        System.out.println("\n4. Testing deleteBook method:");
        bookService.deleteBook("Spring in Action");
        
        System.out.println("\n=== Application Finished ===");
        
        // Close the context
        ((ClassPathXmlApplicationContext) context).close();
    }
}