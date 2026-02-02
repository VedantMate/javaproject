package com.library.catalog.service;

import com.library.catalog.entity.Book;
import com.library.catalog.entity.BookStatus;
import com.library.catalog.repository.BookRepository;
import com.library.catalog.repository.BookCopyRepository;
import com.library.catalog.entity.CopyStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private BookCopyRepository bookCopyRepository;
    
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }
    
    public Book createBook(Book book) {
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new RuntimeException("Book with ISBN " + book.getIsbn() + " already exists");
        }
        if (book.getStatus() == null) {
            book.setStatus(BookStatus.ACTIVE);
        }
        return bookRepository.save(book);
    }
    
    public Book updateBook(Long id, Book bookDetails) {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setCategory(bookDetails.getCategory());
        if (bookDetails.getStatus() != null) {
            book.setStatus(bookDetails.getStatus());
        }
        
        return bookRepository.save(book);
    }
    
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        
        // Check if any copies are issued
        long issuedCount = bookCopyRepository.countByBookIdAndStatus(id, CopyStatus.ISSUED);
        if (issuedCount > 0) {
            throw new RuntimeException("Cannot delete book with issued copies");
        }
        
        bookRepository.delete(book);
    }
    
    public List<Book> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }
    
    public List<Book> searchBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }
    
    public List<Book> searchBooksByCategory(String category) {
        return bookRepository.findByCategory(category);
    }
}
