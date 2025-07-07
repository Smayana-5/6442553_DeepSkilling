package com.library;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.library.entity.Book;
import com.library.service.BookService;

@SpringBootApplication
public class LibraryManagementApplication implements CommandLineRunner {
    
    @Autowired
    private BookService bookService;
    
    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Initialize sample data
        initializeSampleData();
        
        // Display application information
        System.out.println("\n=== Library Management System Started ===");
        System.out.println("Application is running on: http://localhost:8080");
        System.out.println("H2 Database Console: http://localhost:8080/h2-console");
        System.out.println("API Base URL: http://localhost:8080/api/books");
        System.out.println("\nSample API Endpoints:");
        System.out.println("GET    /api/books                    - Get all books");
        System.out.println("GET    /api/books/{id}               - Get book by ID");
        System.out.println("POST   /api/books                    - Create new book");
        System.out.println("PUT    /api/books/{id}               - Update book");
        System.out.println("DELETE /api/books/{id}               - Delete book");
        System.out.println("GET    /api/books/search?keyword=java - Search books");
        System.out.println("GET    /api/books/available           - Get available books");
        System.out.println("GET    /api/books/statistics          - Get book statistics");
        System.out.println("==========================================\n");
        
        // Display current statistics
        BookService.BookStatistics stats = bookService.getBookStatistics();
        System.out.println("Current Library Statistics:");
        System.out.println("Total Books: " + stats.getTotalBooks());
        System.out.println("Available Books: " + stats.getAvailableBooks());
        System.out.println("Unavailable Books: " + stats.getUnavailableBooks());
        System.out.println("==========================================\n");
    }
    
    private void initializeSampleData() {
        // Check if data already exists
        if (bookService.getAllBooks().isEmpty()) {
            System.out.println("Initializing sample data...");
            
            // Create sample books
            Book book1 = new Book("Effective Java", "Joshua Bloch", "978-0134685991", LocalDate.of(2017, 12, 27));
            book1.setDescription("The definitive guide to Java programming language best practices");
            
            Book book2 = new Book("Spring in Action", "Craig Walls", "978-1617294945", LocalDate.of(2018, 10, 1));
            book2.setDescription("Comprehensive guide to Spring Framework development");
            
            Book book3 = new Book("Clean Code", "Robert C. Martin", "978-0132350884", LocalDate.of(2008, 8, 1));
            book3.setDescription("A handbook of agile software craftsmanship");
            
            Book book4 = new Book("Design Patterns", "Gang of Four", "978-0201633612", LocalDate.of(1994, 10, 31));
            book4.setDescription("Elements of reusable object-oriented software");
            book4.setAvailable(false); // Mark as unavailable for demonstration
            
            Book book5 = new Book("Java Concurrency in Practice", "Brian Goetz", "978-0321349606", LocalDate.of(2006, 5, 9));
            book5.setDescription("Essential techniques for building concurrent applications");
            
            try {
                bookService.createBook(book1);
                bookService.createBook(book2);
                bookService.createBook(book3);
                bookService.createBook(book4);
                bookService.createBook(book5);
                
                System.out.println("Sample data initialized successfully!");
            } catch (Exception e) {
                System.err.println("Error initializing sample data: " + e.getMessage());
            }
        }
    }
}