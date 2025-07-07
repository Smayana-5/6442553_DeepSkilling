package com.library;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.library.service.BookService;

/**
 * Main application class to test Spring AOP logging functionality
 */
public class LibraryManagementApplication {
    
    public static void main(String[] args) {
        System.out.println("=== Library Management Application with AOP Logging ===");
        System.out.println();
        
        // Load Spring Application Context
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        
        // Get the BookService bean
        BookService bookService = context.getBean("bookService", BookService.class);
        
        System.out.println("1. Testing getAllBooks() method:");
        System.out.println("================================");
        List<String> books = bookService.getAllBooks();
        System.out.println("Books retrieved: " + books);
        System.out.println();
        
        System.out.println("2. Testing addBook() method:");
        System.out.println("============================");
        bookService.addBook("The Catcher in the Rye");
        System.out.println();
        
        System.out.println("3. Testing searchBook() method:");
        System.out.println("===============================");
        String foundBook = bookService.searchBook("1984");
        System.out.println("Search result: " + foundBook);
        System.out.println();
        
        System.out.println("4. Testing getTotalBooks() method:");
        System.out.println("==================================");
        int totalBooks = bookService.getTotalBooks();
        System.out.println("Total books: " + totalBooks);
        System.out.println();
        
        System.out.println("5. Testing processBooks() method:");
        System.out.println("=================================");
        bookService.processBooks();
        System.out.println();
        
        System.out.println("6. Testing exception handling:");
        System.out.println("==============================");
        try {
            bookService.addBook(""); // This should throw an exception
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
        System.out.println();
        
        System.out.println("7. Final verification - getAllBooks() again:");
        System.out.println("============================================");
        List<String> finalBooks = bookService.getAllBooks();
        System.out.println("Final book list: " + finalBooks);
        System.out.println("Final count: " + finalBooks.size());
        System.out.println();
        
        System.out.println("=== Application completed successfully! ===");
        System.out.println("Check the console output above for AOP logging messages.");
    }
}