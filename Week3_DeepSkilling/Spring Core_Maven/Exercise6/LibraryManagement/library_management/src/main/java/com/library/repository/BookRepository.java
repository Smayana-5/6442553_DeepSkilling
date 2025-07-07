package com.library.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {
    
    private List<String> books;
    
    public BookRepository() {
        this.books = new ArrayList<>();
        // Initialize with some sample books
        books.add("Spring Framework Guide");
        books.add("Java Programming");
        books.add("Maven Build Tool");
        System.out.println("BookRepository bean created successfully!");
    }
    
    public void addBook(String book) {
        books.add(book);
        System.out.println("Book added: " + book);
    }
    
    public List<String> getAllBooks() {
        return books;
    }
    
    public void removeBook(String book) {
        books.remove(book);
        System.out.println("Book removed: " + book);
    }
    
    public int getBookCount() {
        return books.size();
    }
    
    public void displayBooks() {
        System.out.println("Available books:");
        for (String book : books) {
            System.out.println("- " + book);
        }
    }
}