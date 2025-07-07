package com.library.repository;

import com.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    // Find book by ISBN
    Optional<Book> findByIsbn(String isbn);
    
    // Find books by title (case-insensitive)
    List<Book> findByTitleContainingIgnoreCase(String title);
    
    // Find books by author (case-insensitive)
    List<Book> findByAuthorContainingIgnoreCase(String author);
    
    // Find available books
    List<Book> findByAvailableTrue();
    
    // Find unavailable books
    List<Book> findByAvailableFalse();
    
    // Custom query to find books by title or author
    @Query("SELECT b FROM Book b WHERE " +
           "LOWER(b.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
           "LOWER(b.author) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Book> findByTitleOrAuthorContaining(@Param("keyword") String keyword);
    
    // Check if book exists by ISBN
    boolean existsByIsbn(String isbn);
    
    // Count available books
    long countByAvailableTrue();
    
    // Count total books
    @Query("SELECT COUNT(b) FROM Book b")
    long countTotalBooks();
}