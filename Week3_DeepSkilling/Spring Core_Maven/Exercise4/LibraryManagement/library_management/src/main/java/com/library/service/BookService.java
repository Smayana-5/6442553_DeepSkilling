package com.library.service;

import org.springframework.stereotype.Service;

import com.library.repository.BookRepository;

/**
 * Service class for handling book-related business logic
 */
@Service
public class BookService {

    private BookRepository bookRepository;

    // Default constructor
    public BookService() {
        System.out.println("BookService: Default constructor called");
    }

    // Constructor for constructor injection
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("BookService: Constructor with BookRepository called");
    }

    // Setter method for dependency injection
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("BookService: BookRepository injected via setter");
    }

    // Getter method
    public BookRepository getBookRepository() {
        return bookRepository;
    }

    /**
     * Method to perform a service operation
     * This method will be used to test dependency injection and AOP
     */
    public void performServiceOperation() {
        System.out.println("BookService: Performing service operation...");
        
        if (bookRepository != null) {
            System.out.println("BookService: BookRepository is properly injected");
            bookRepository.performRepositoryOperation();
        } else {
            System.out.println("BookService: BookRepository is not injected!");
        }
    }

    /**
     * Method to add a book (placeholder implementation)
     */
    public void addBook(String title, String author) {
        System.out.println("BookService: Adding book - Title: " + title + ", Author: " + author);
        
        if (bookRepository != null) {
            bookRepository.saveBook(title, author);
        } else {
            System.out.println("BookService: Cannot add book - BookRepository is null");
        }
    }

    /**
     * Method to find a book (placeholder implementation)
     */
    public void findBook(String title) {
        System.out.println("BookService: Searching for book with title: " + title);
        
        if (bookRepository != null) {
            bookRepository.findBook(title);
        } else {
            System.out.println("BookService: Cannot find book - BookRepository is null");
        }
    }

    /**
     * Method to get all books (placeholder implementation)
     */
    public void getAllBooks() {
        System.out.println("BookService: Retrieving all books");
        
        if (bookRepository != null) {
            bookRepository.getAllBooks();
        } else {
            System.out.println("BookService: Cannot retrieve books - BookRepository is null");
        }
    }

    @Override
    public String toString() {
        return "BookService{" +
                "bookRepository=" + bookRepository +
                '}';
    }
}