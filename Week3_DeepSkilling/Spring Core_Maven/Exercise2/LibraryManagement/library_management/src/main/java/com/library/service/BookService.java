package com.library.service;

import com.library.repository.BookRepository;
import java.util.List;

/**
 * BookService class handles business logic for book operations
 * This class demonstrates dependency injection through setter method
 */
public class BookService {
    
    private BookRepository bookRepository;
    
    /**
     * Default constructor
     */
    public BookService() {
        System.out.println("BookService instance created");
    }
    
    /**
     * Setter method for BookRepository dependency injection
     * This method will be called by Spring to inject the BookRepository dependency
     * @param bookRepository The BookRepository instance to inject
     */
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("BookService: BookRepository dependency injected successfully");
    }
    
    /**
     * Get all books through the repository
     * @return List of all book titles
     */
    public List<String> getAllBooks() {
        System.out.println("BookService: Getting all books");
        if (bookRepository == null) {
            throw new IllegalStateException("BookRepository not injected");
        }
        return bookRepository.getAllBooks();
    }
    
    /**
     * Add a new book through the repository
     * @param bookTitle The title of the book to add
     */
    public void addBook(String bookTitle) {
        System.out.println("BookService: Adding book - " + bookTitle);
        if (bookRepository == null) {
            throw new IllegalStateException("BookRepository not injected");
        }
        
        // Business logic validation
        if (bookTitle == null || bookTitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty");
        }
        
        bookRepository.addBook(bookTitle);
        System.out.println("BookService: Book added successfully");
    }
    
    /**
     * Search for a book by title
     * @param title The title to search for
     * @return The book title if found, null otherwise
     */
    public String searchBook(String title) {
        System.out.println("BookService: Searching for book - " + title);
        if (bookRepository == null) {
            throw new IllegalStateException("BookRepository not injected");
        }
        
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Search title cannot be null or empty");
        }
        
        return bookRepository.findBookByTitle(title);
    }
    
    /**
     * Get the total number of books
     * @return Total book count
     */
    public int getTotalBooks() {
        System.out.println("BookService: Getting total book count");
        if (bookRepository == null) {
            throw new IllegalStateException("BookRepository not injected");
        }
        return bookRepository.getBookCount();
    }
    
    /**
     * Display library statistics
     */
    public void displayLibraryStats() {
        System.out.println("=== Library Statistics ===");
        System.out.println("Total Books: " + getTotalBooks());
        System.out.println("Available Books: " + getAllBooks());
        System.out.println("==========================");
    }
}