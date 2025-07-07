package com.library.repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for managing Book data
 */
public class BookRepository {
    
    private List<String> books;
    
    public BookRepository() {
        this.books = new ArrayList<>();
        // Initialize with some dummy data
        books.add("The Great Gatsby");
        books.add("To Kill a Mockingbird");
        books.add("1984");
        books.add("Pride and Prejudice");
    }
    
    /**
     * Get all books from the repository
     * @return List of books
     */
    public List<String> getAllBooks() {
        // Simulate some processing time
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return new ArrayList<>(books);
    }
    
    /**
     * Add a book to the repository
     * @param book Book title to add
     */
    public void addBook(String book) {
        // Simulate some processing time
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        books.add(book);
    }
    
    /**
     * Find a book by title
     * @param title Book title to search
     * @return Book title if found, null otherwise
     */
    public String findBookByTitle(String title) {
        // Simulate some processing time
        try {
            Thread.sleep(75);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return books.stream()
                .filter(book -> book.toLowerCase().contains(title.toLowerCase()))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Get the total count of books
     * @return Number of books
     */
    public int getBookCount() {
        // Simulate some processing time
        try {
            Thread.sleep(25);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return books.size();
    }
}