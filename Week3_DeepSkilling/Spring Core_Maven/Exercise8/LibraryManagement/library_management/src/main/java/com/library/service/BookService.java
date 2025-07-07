package com.library.service;

import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;
    
    public void addBook(String title, String author) {
        System.out.println("Adding book: " + title + " by " + author);
        bookRepository.save(title, author);
    }
    
    public void findBook(String title) {
        System.out.println("Finding book: " + title);
        bookRepository.findByTitle(title);
    }
    
    public void getAllBooks() {
        System.out.println("Getting all books");
        bookRepository.findAll();
    }
    
    public void deleteBook(String title) {
        System.out.println("Deleting book: " + title);
        bookRepository.delete(title);
    }
}