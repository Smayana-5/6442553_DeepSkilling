package com.library.service;

import java.util.List;

import com.library.repository.BookRepository;

/**
 * BookService class handles business logic for book operations
 */
public class BookService {
    
    private BookRepository bookRepository;
    
    /**
     * Default constructor
     */
    public BookService() {
        System.out.println("BookService: Constructor called");
    }
    
    /**
     * Setter method for BookRepository - Required for Spring DI
     * @param bookRepository The BookRepository instance to inject
     */
    public void setBookRepository(BookRepository bookRepository) {
        System.out.println("BookService: Setting BookRepository via setter injection");
        this.bookRepository = bookRepository;
    }
    
    /**
     * Get all books through the service layer
     * @return List of all books
     */
    public List<String> getAllBooks() {
        System.out.println("BookService: Processing request to get all books");
        if (bookRepository == null) {
            throw new IllegalStateException("BookRepository is not initialized");
        }
        return bookRepository.findAllBooks();
    }
    
    /**
     * Add a new book through the service layer
     * @param bookTitle The title of the book to add
     */
    public void addNewBook(String bookTitle) {
        System.out.println("BookService: Processing request to add new book - " + bookTitle);
        if (bookRepository == null) {
            throw new IllegalStateException("BookRepository is not initialized");
        }
        
        // Business logic: Check if book already exists
        if (bookRepository.findBookByTitle(bookTitle)) {
            System.out.println("BookService: Book already exists - " + bookTitle);
            return;
        }
        
        bookRepository.addBook(bookTitle);
        System.out.println("BookService: Successfully added book - " + bookTitle);
    }
    
    /**
     * Remove a book through the service layer
     * @param bookTitle The title of the book to remove
     * @return true if book was removed successfully
     */
    public boolean removeBook(String bookTitle) {
        System.out.println("BookService: Processing request to remove book - " + bookTitle);
        if (bookRepository == null) {
            throw new IllegalStateException("BookRepository is not initialized");
        }
        
        boolean removed = bookRepository.removeBook(bookTitle);
        if (removed) {
            System.out.println("BookService: Successfully removed book - " + bookTitle);
        } else {
            System.out.println("BookService: Book not found - " + bookTitle);
        }
        return removed;
    }
    
    /**
     * Search for a book through the service layer
     * @param bookTitle The title to search for
     * @return true if book exists
     */
    public boolean searchBook(String bookTitle) {
        System.out.println("BookService: Processing search request for book - " + bookTitle);
        if (bookRepository == null) {
            throw new IllegalStateException("BookRepository is not initialized");
        }
        
        boolean found = bookRepository.findBookByTitle(bookTitle);
        System.out.println("BookService: Search result for '" + bookTitle + "': " + (found ? "Found" : "Not Found"));
        return found;
    }
    
    /**
     * Get library statistics through the service layer
     * @return String containing library statistics
     */
    public String getLibraryStats() {
        System.out.println("BookService: Processing request for library statistics");
        if (bookRepository == null) {
            throw new IllegalStateException("BookRepository is not initialized");
        }
        
        int bookCount = bookRepository.getBookCount();
        String stats = "Library Statistics: Total Books = " + bookCount;
        System.out.println("BookService: " + stats);
        return stats;
    }
}