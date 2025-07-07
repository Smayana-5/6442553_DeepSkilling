package com.library.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.library.entity.Book;
import com.library.repository.BookRepository;

@Service
public class BookService {
    
    private final BookRepository bookRepository;
    
    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    // Create a new book
    public Book createBook(Book book) {
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new IllegalArgumentException("Book with ISBN " + book.getIsbn() + " already exists");
        }
        return bookRepository.save(book);
    }
    
    // Get all books
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    // Get book by ID
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }
    
    // Get book by ISBN
    public Optional<Book> getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }
    
    // Update book
    public Book updateBook(Long id, Book bookDetails) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + id));
        
        // Check if ISBN is being changed to an existing one
        if (!book.getIsbn().equals(bookDetails.getIsbn()) && 
            bookRepository.existsByIsbn(bookDetails.getIsbn())) {
            throw new IllegalArgumentException("Book with ISBN " + bookDetails.getIsbn() + " already exists");
        }
        
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setIsbn(bookDetails.getIsbn());
        book.setPublicationDate(bookDetails.getPublicationDate());
        book.setAvailable(bookDetails.getAvailable());
        book.setDescription(bookDetails.getDescription());
        
        return bookRepository.save(book);
    }
    
    // Delete book
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new IllegalArgumentException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }
    
    // Search books by title
    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
    
    // Search books by author
    public List<Book> searchBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }
    
    // Search books by keyword (title or author)
    public List<Book> searchBooks(String keyword) {
        return bookRepository.findByTitleOrAuthorContaining(keyword);
    }
    
    // Get available books
    public List<Book> getAvailableBooks() {
        return bookRepository.findByAvailableTrue();
    }
    
    // Get unavailable books
    public List<Book> getUnavailableBooks() {
        return bookRepository.findByAvailableFalse();
    }
    
    // Toggle book availability
    public Book toggleBookAvailability(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Book not found with id: " + id));
        
        book.setAvailable(!book.getAvailable());
        return bookRepository.save(book);
    }
    
    // Get book statistics
    public BookStatistics getBookStatistics() {
        long totalBooks = bookRepository.countTotalBooks();
        long availableBooks = bookRepository.countByAvailableTrue();
        long unavailableBooks = totalBooks - availableBooks;
        
        return new BookStatistics(totalBooks, availableBooks, unavailableBooks);
    }
    
    // Inner class for statistics
    public static class BookStatistics {
        private final long totalBooks;
        private final long availableBooks;
        private final long unavailableBooks;
        
        public BookStatistics(long totalBooks, long availableBooks, long unavailableBooks) {
            this.totalBooks = totalBooks;
            this.availableBooks = availableBooks;
            this.unavailableBooks = unavailableBooks;
        }
        
        public long getTotalBooks() { return totalBooks; }
        public long getAvailableBooks() { return availableBooks; }
        public long getUnavailableBooks() { return unavailableBooks; }
    }
}