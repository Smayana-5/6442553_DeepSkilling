package com.library.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * Repository class for managing Book data
 */
@Repository
public class BookRepository {
    
    private List<String> books;
    
    public BookRepository() {
        this.books = new ArrayList<>();
        // Initialize with some sample books
        books.add("Spring Framework Guide");
        books.add("Java Programming");
        books.add("Maven Build Tool");
        System.out.println("BookRepository initialized with sample books");
    }
    
    /**
     * Get all books
     * @return List of book titles
     */
    public List<String> getAllBooks() {
        System.out.println("BookRepository: Retrieving all books");
        return new ArrayList<>(books);
    }
    
    /**
     * Add a new book
     * @param bookTitle The title of the book to add
     */
    public void addBook(String bookTitle) {
        books.add(bookTitle);
        System.out.println("BookRepository: Added book - " + bookTitle);
    }
    
    /**
     * Find a book by title
     * @param title The title to search for
     * @return true if book exists, false otherwise
     */
    public boolean findBookByTitle(String title) {
        boolean found = books.contains(title);
        System.out.println("BookRepository: Searching for book '" + title + "' - Found: " + found);
        return found;
    }
    
    /**
     * Get the total number of books
     * @return Total count of books
     */
    public int getTotalBooks() {
        int count = books.size();
        System.out.println("BookRepository: Total books count - " + count);
        return count;
    }
}