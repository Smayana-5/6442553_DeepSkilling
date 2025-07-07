package com.library.repository;

import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {
    
    public void save(String title, String author) {
        System.out.println("Repository: Saving book - " + title + " by " + author);
    }
    
    public void findByTitle(String title) {
        System.out.println("Repository: Finding book by title - " + title);
    }
    
    public void findAll() {
        System.out.println("Repository: Getting all books from database");
    }
    
    public void delete(String title) {
        System.out.println("Repository: Deleting book - " + title);
    }
}