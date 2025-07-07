package com.library.service;

import java.util.List;

import com.library.repository.BookRepository;

public class BookService {
    private BookRepository bookRepository;
    private String serviceName;
    
    // Default constructor
    public BookService() {
        System.out.println("BookService created with default constructor");
    }
    
    // Constructor for constructor injection
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("BookService created with constructor injection");
    }
    
    // Constructor with multiple parameters
    public BookService(BookRepository bookRepository, String serviceName) {
        this.bookRepository = bookRepository;
        this.serviceName = serviceName;
        System.out.println("BookService created with constructor injection - Repository and ServiceName");
    }
    
    // Setter method for setter injection
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("BookRepository injected via setter method");
    }
    
    // Setter for service name
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
        System.out.println("ServiceName set to: " + serviceName);
    }
    
    // Getter for service name
    public String getServiceName() {
        return serviceName;
    }
    
    // Business methods
    public void addBook(String book) {
        if (bookRepository != null) {
            bookRepository.addBook(book);
        } else {
            System.out.println("BookRepository is not initialized!");
        }
    }
    
    public List<String> getAllBooks() {
        if (bookRepository != null) {
            return bookRepository.getAllBooks();
        } else {
            System.out.println("BookRepository is not initialized!");
            return null;
        }
    }
    
    public boolean searchBook(String book) {
        if (bookRepository != null) {
            return bookRepository.findBook(book);
        } else {
            System.out.println("BookRepository is not initialized!");
            return false;
        }
    }
    
    public void displayService() {
        System.out.println("BookService [" + (serviceName != null ? serviceName : "Default") + "] is ready");
        if (bookRepository != null) {
            bookRepository.displayRepository();
        } else {
            System.out.println("BookRepository is not initialized!");
        }
    }
}