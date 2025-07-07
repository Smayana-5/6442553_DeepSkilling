package com.library;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.library.service.BookService;

public class LibraryManagementApplication {
    
    public static void main(String[] args) {
        System.out.println("=== Starting Library Management Application ===");
        
        // Load Spring application context
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        System.out.println("\n=== Testing Constructor Injection ===");
        testConstructorInjection(context);
        
        System.out.println("\n=== Testing Constructor Injection (Multiple Parameters) ===");
        testConstructorInjectionMultiple(context);
        
        System.out.println("\n=== Testing Setter Injection ===");
        testSetterInjection(context);
        
        System.out.println("\n=== Testing Mixed Injection ===");
        testMixedInjection(context);
        
        System.out.println("\n=== Application completed successfully ===");
    }
    
    private static void testConstructorInjection(ApplicationContext context) {
        BookService bookService = (BookService) context.getBean("bookServiceConstructor");
        
        System.out.println("Service Name: " + bookService.getServiceName());
        bookService.displayService();
        
        bookService.addBook("Design Patterns");
        bookService.addBook("Clean Code");
        
        System.out.println("Searching for 'Clean Code': " + bookService.searchBook("Clean Code"));
        System.out.println("All books: " + bookService.getAllBooks());
    }
    
    private static void testConstructorInjectionMultiple(ApplicationContext context) {
        BookService bookService = (BookService) context.getBean("bookServiceConstructorMultiple");
        
        System.out.println("Service Name: " + bookService.getServiceName());
        bookService.displayService();
        
        bookService.addBook("Spring Boot in Action");
        System.out.println("All books: " + bookService.getAllBooks());
    }
    
    private static void testSetterInjection(ApplicationContext context) {
        BookService bookService = (BookService) context.getBean("bookServiceSetter");
        
        System.out.println("Service Name: " + bookService.getServiceName());
        bookService.displayService();
        
        bookService.addBook("Microservices Patterns");
        System.out.println("Searching for 'Java Programming': " + bookService.searchBook("Java Programming"));
    }
    
    private static void testMixedInjection(ApplicationContext context) {
        BookService bookService = (BookService) context.getBean("bookServiceMixed");
        
        System.out.println("Service Name: " + bookService.getServiceName());
        bookService.displayService();
        
        bookService.addBook("Effective Java");
        System.out.println("Total books: " + bookService.getAllBooks().size());
    }
}