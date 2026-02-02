package com.library.catalog.service;

import com.library.catalog.entity.BookCopy;
import com.library.catalog.entity.CopyStatus;
import com.library.catalog.repository.BookCopyRepository;
import com.library.catalog.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookCopyService {
    
    @Autowired
    private BookCopyRepository bookCopyRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
    public List<BookCopy> getAllCopies() {
        return bookCopyRepository.findAll();
    }
    
    public Optional<BookCopy> getCopyById(Long id) {
        return bookCopyRepository.findById(id);
    }
    
    public List<BookCopy> getCopiesByBookId(Long bookId) {
        return bookCopyRepository.findByBookId(bookId);
    }
    
    public List<BookCopy> getCopiesByStatus(CopyStatus status) {
        return bookCopyRepository.findByStatus(status);
    }
    
    public BookCopy createCopy(BookCopy bookCopy) {
        // Verify book exists
        bookRepository.findById(bookCopy.getBookId())
            .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookCopy.getBookId()));
        
        if (bookCopy.getStatus() == null) {
            bookCopy.setStatus(CopyStatus.AVAILABLE);
        }
        
        return bookCopyRepository.save(bookCopy);
    }
    
    public BookCopy updateCopy(Long id, BookCopy copyDetails) {
        BookCopy copy = bookCopyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book copy not found with id: " + id));
        
        copy.setRackNo(copyDetails.getRackNo());
        if (copyDetails.getStatus() != null) {
            copy.setStatus(copyDetails.getStatus());
        }
        
        return bookCopyRepository.save(copy);
    }
    
    public void deleteCopy(Long id) {
        BookCopy copy = bookCopyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book copy not found with id: " + id));
        
        if (copy.getStatus() == CopyStatus.ISSUED) {
            throw new RuntimeException("Cannot delete an issued book copy");
        }
        
        bookCopyRepository.delete(copy);
    }
    
    public List<BookCopy> getAvailableCopiesByBookId(Long bookId) {
        return bookCopyRepository.findByBookIdAndStatus(bookId, CopyStatus.AVAILABLE);
    }
    
    public Long getTotalCount() {
        return bookCopyRepository.count();
    }
    
    public java.util.Map<String, Long> getCountByStatus() {
        java.util.Map<String, Long> counts = new java.util.HashMap<>();
        for (CopyStatus status : CopyStatus.values()) {
            counts.put(status.name(), (long) bookCopyRepository.findByStatus(status).size());
        }
        return counts;
    }
    
    public void updateStatus(Long id, CopyStatus status) {
        BookCopy copy = bookCopyRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book copy not found with id: " + id));
        copy.setStatus(status);
        bookCopyRepository.save(copy);
    }
}
