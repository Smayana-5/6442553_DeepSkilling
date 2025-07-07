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
        books.add("Spring Framework in Action");
        books.add("Java: The Complete Reference");
        books.add("Effective Java");
        books.add("Spring Boot in Practice");
    }
    
    /**
     * Get all books from the repository
     * @return List of book titles
     */
    public List<String> findAllBooks() {
        System.out.println("BookRepository: Fetching all books from repository");
        return new ArrayList<>(books);
    }
    
    /**
     * Add a new book to the repository
     * @param bookTitle The title of the book to add
     */
    public void addBook(String bookTitle) {
        System.out.println("BookRepository: Adding book - " + bookTitle);
        books.add(bookTitle);
    }
    
    /**
     * Remove a book from the repository
     * @param bookTitle The title of the book to remove
     * @return true if book was removed, false if not found
     */
    public boolean removeBook(String bookTitle) {
        System.out.println("BookRepository: Removing book - " + bookTitle);
        return books.remove(bookTitle);
    }
    
    /**
     * Find a book by title
     * @param bookTitle The title to search for
     * @return true if book exists, false otherwise
     */
    public boolean findBookByTitle(String bookTitle) {
        System.out.println("BookRepository: Searching for book - " + bookTitle);
        return books.contains(bookTitle);
    }
    
    /**
     * Get the total number of books
     * @return The count of books in the repository
     */
    public int getBookCount() {
        System.out.println("BookRepository: Getting book count");
        return books.size();
    }
}