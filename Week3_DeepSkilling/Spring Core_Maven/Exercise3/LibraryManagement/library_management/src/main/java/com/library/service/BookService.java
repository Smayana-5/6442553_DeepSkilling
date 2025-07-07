package com.library.service;

import java.util.List;

import com.library.repository.BookRepository;

/**
 * Service class for managing Book operations
 */
public class BookService {
    
    private BookRepository bookRepository;
    
    // Setter method for dependency injection
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    /**
     * Get all books through the service layer
     * @return List of books
     */
    public List<String> getAllBooks() {
        System.out.println("BookService: Getting all books");
        return bookRepository.getAllBooks();
    }
    
    /**
     * Add a new book through the service layer
     * @param book Book title to add
     */
    public void addBook(String book) {
        System.out.println("BookService: Adding book - " + book);
        if (book == null || book.trim().isEmpty()) {
            throw new IllegalArgumentException("Book title cannot be null or empty");
        }
        bookRepository.addBook(book);
    }
    
    /**
     * Search for a book by title
     * @param title Book title to search
     * @return Book title if found, null otherwise
     */
    public String searchBook(String title) {
        System.out.println("BookService: Searching for book - " + title);
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Search title cannot be null or empty");
        }
        return bookRepository.findBookByTitle(title);
    }
    
    /**
     * Get the total number of books
     * @return Number of books
     */
    public int getTotalBooks() {
        System.out.println("BookService: Getting total book count");
        return bookRepository.getBookCount();
    }
    
    /**
     * Process books - a complex operation for demonstration
     */
    public void processBooks() {
        System.out.println("BookService: Processing books...");
        // Simulate complex processing
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        List<String> books = getAllBooks();
        System.out.println("BookService: Processed " + books.size() + " books");
    }
}