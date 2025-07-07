package com.library.repository;

import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    private List<String> books;
    
    public BookRepository() {
        this.books = new ArrayList<>();
        // Initialize with some sample books
        books.add("Spring Framework Guide");
        books.add("Java Programming");
        books.add("Maven Build Tool");
        System.out.println("BookRepository created with default constructor");
    }
    
    public void addBook(String book) {
        books.add(book);
        System.out.println("Book added: " + book);
    }
    
    public List<String> getAllBooks() {
        return new ArrayList<>(books);
    }
    
    public boolean findBook(String book) {
        return books.contains(book);
    }
    
    public void displayRepository() {
        System.out.println("BookRepository contains " + books.size() + " books:");
        for (String book : books) {
            System.out.println("  - " + book);
        }
    }
}