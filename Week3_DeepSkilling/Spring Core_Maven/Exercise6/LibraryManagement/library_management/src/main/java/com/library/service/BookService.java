package com.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.repository.BookRepository;

@Service
public class BookService {
    
    private BookRepository bookRepository;
    
    public BookService() {
        System.out.println("BookService bean created successfully!");
    }
    
    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("BookRepository injected into BookService via setter injection!");
    }
    
    public void addBook(String book) {
        if (book != null && !book.trim().isEmpty()) {
            bookRepository.addBook(book);
            System.out.println("BookService: Book processing completed for: " + book);
        } else {
            System.out.println("BookService: Invalid book title provided");
        }
    }
    
    public List<String> getAllBooks() {
        System.out.println("BookService: Retrieving all books");
        return bookRepository.getAllBooks();
    }
    
    public void removeBook(String book) {
        if (book != null && !book.trim().isEmpty()) {
            bookRepository.removeBook(book);
            System.out.println("BookService: Book removal completed for: " + book);
        } else {
            System.out.println("BookService: Invalid book title provided for removal");
        }
    }
    
    public int getBookCount() {
        int count = bookRepository.getBookCount();
        System.out.println("BookService: Total books in library: " + count);
        return count;
    }
    
    public void displayAllBooks() {
        System.out.println("BookService: Displaying all books");
        bookRepository.displayBooks();
    }
    
    public void performLibraryOperations() {
        System.out.println("\n=== Library Management Operations ===");
        
        // Display initial books
        displayAllBooks();
        
        // Add new books
        addBook("Design Patterns");
        addBook("Spring Boot in Action");
        
        // Display updated list
        displayAllBooks();
        
        // Show book count
        getBookCount();
        
        // Remove a book
        removeBook("Java Programming");
        
        // Final display
        displayAllBooks();
        getBookCount();
        
        System.out.println("=== Operations completed ===\n");
    }
}