package com.library.repository;

import java.util.ArrayList;
import java.util.List;

/**
 * BookRepository class handles data access operations for books
 */
public class BookRepository {
    
    private List<String> books;
    
    public BookRepository() {
        this.books = new ArrayList<>();
        // Initialize with some sample books
        books.add("Spring in Action");
        books.add("Clean Code");
        books.add("Effective Java");
        books.add("Design Patterns");
        System.out.println("BookRepository initialized with sample books");
    }
    
    /**
     * Get all books from the repository
     * @return List of book titles
     */
    public List<String> getAllBooks() {
        System.out.println("BookRepository: Fetching all books");
        return new ArrayList<>(books);
    }
    
    /**
     * Add a new book to the repository
     * @param bookTitle The title of the book to add
     */
    public void addBook(String bookTitle) {
        books.add(bookTitle);
        System.out.println("BookRepository: Added book - " + bookTitle);
    }
    
    /**
     * Find a book by title
     * @param title The title to search for
     * @return The book title if found, null otherwise
     */
    public String findBookByTitle(String title) {
        System.out.println("BookRepository: Searching for book - " + title);
        return books.stream()
                .filter(book -> book.toLowerCase().contains(title.toLowerCase()))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Get the total number of books
     * @return Total book count
     */
    public int getBookCount() {
        System.out.println("BookRepository: Getting book count");
        return books.size();
    }
}