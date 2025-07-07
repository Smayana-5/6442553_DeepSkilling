package com.library.service;

import java.util.List;

import com.library.repository.BookRepository;

/**
 * Service class for managing Book operations
 */
public class BookService {
    
    private BookRepository bookRepository;
    
    public BookService() {
        System.out.println("BookService initialized");
    }
    
    /**
     * Set the BookRepository dependency (used by Spring XML configuration)
     * @param bookRepository The repository instance
     */
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("BookService: BookRepository dependency injected via setter");
    }
    

    
    /**
     * Get all books from the repository
     * @return List of all books
     */
    public List<String> getAllBooks() {
        System.out.println("BookService: Getting all books");
        if (bookRepository != null) {
            return bookRepository.getAllBooks();
        } else {
            System.out.println("BookService: BookRepository is null!");
            return null;
        }
    }
    
    /**
     * Add a new book
     * @param bookTitle The title of the book to add
     */
    public void addBook(String bookTitle) {
        System.out.println("BookService: Adding book - " + bookTitle);
        if (bookRepository != null) {
            bookRepository.addBook(bookTitle);
        } else {
            System.out.println("BookService: BookRepository is null!");
        }
    }
    
    /**
     * Search for a book by title
     * @param title The title to search for
     * @return true if book exists, false otherwise
     */
    public boolean searchBook(String title) {
        System.out.println("BookService: Searching for book - " + title);
        if (bookRepository != null) {
            return bookRepository.findBookByTitle(title);
        } else {
            System.out.println("BookService: BookRepository is null!");
            return false;
        }
    }
    
    /**
     * Get the total number of books
     * @return Total count of books
     */
    public int getTotalBooks() {
        System.out.println("BookService: Getting total books count");
        if (bookRepository != null) {
            return bookRepository.getTotalBooks();
        } else {
            System.out.println("BookService: BookRepository is null!");
            return 0;
        }
    }
    
    /**
     * Display library statistics
     */
    public void displayLibraryStats() {
        System.out.println("\n=== Library Statistics ===");
        System.out.println("Total Books: " + getTotalBooks());
        System.out.println("Available Books:");
        List<String> books = getAllBooks();
        if (books != null) {
            for (int i = 0; i < books.size(); i++) {
                System.out.println((i + 1) + ". " + books.get(i));
            }
        }
        System.out.println("========================\n");
    }
}