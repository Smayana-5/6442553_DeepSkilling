package com.library.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

/**
 * Repository class for handling book data access operations
 */
@Repository
public class BookRepository {

    private List<String> books;

    // Constructor
    public BookRepository() {
        this.books = new ArrayList<>();
        System.out.println("BookRepository: Constructor called");
        // Initialize with some sample data
        books.add("Spring in Action");
        books.add("Effective Java");
        books.add("Clean Code");
    }

    /**
     * Method to perform a repository operation
     * This method will be used to test dependency injection and AOP
     */
    public void performRepositoryOperation() {
        System.out.println("BookRepository: Performing repository operation...");
        System.out.println("BookRepository: Current books count: " + books.size());
    }

    /**
     * Method to save a book
     */
    public void saveBook(String title, String author) {
        String bookEntry = title + " by " + author;
        books.add(bookEntry);
        System.out.println("BookRepository: Saved book - " + bookEntry);
    }

    /**
     * Method to find a book by title
     */
    public void findBook(String title) {
        System.out.println("BookRepository: Searching for book with title: " + title);
        
        boolean found = false;
        for (String book : books) {
            if (book.toLowerCase().contains(title.toLowerCase())) {
                System.out.println("BookRepository: Found book - " + book);
                found = true;
            }
        }
        
        if (!found) {
            System.out.println("BookRepository: No book found with title: " + title);
        }
    }

    /**
     * Method to get all books
     */
    public void getAllBooks() {
        System.out.println("BookRepository: Retrieving all books");
        System.out.println("BookRepository: Total books: " + books.size());
        
        for (int i = 0; i < books.size(); i++) {
            System.out.println("BookRepository: Book " + (i + 1) + ": " + books.get(i));
        }
    }

    /**
     * Method to delete a book
     */
    public void deleteBook(String title) {
        System.out.println("BookRepository: Attempting to delete book with title: " + title);
        
        boolean removed = books.removeIf(book -> book.toLowerCase().contains(title.toLowerCase()));
        
        if (removed) {
            System.out.println("BookRepository: Successfully deleted book with title: " + title);
        } else {
            System.out.println("BookRepository: No book found to delete with title: " + title);
        }
    }

    /**
     * Method to get books count
     */
    public int getBooksCount() {
        return books.size();
    }

    @Override
    public String toString() {
        return "BookRepository{" +
                "books=" + books.size() + " books" +
                '}';
    }
}