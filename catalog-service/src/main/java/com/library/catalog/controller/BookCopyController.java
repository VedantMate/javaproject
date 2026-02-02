package com.library.catalog.controller;

import com.library.catalog.entity.BookCopy;
import com.library.catalog.entity.CopyStatus;
import com.library.catalog.service.BookCopyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/books")
@Tag(name = "Book Copy Management (Admin)", description = "Admin APIs for managing book copies")
@CrossOrigin(origins = "*")
public class BookCopyController {
    
    @Autowired
    private BookCopyService bookCopyService;
    
    @GetMapping("/copies")
    @Operation(summary = "Get all book copies")
    public ResponseEntity<List<BookCopy>> getAllCopies() {
        return ResponseEntity.ok(bookCopyService.getAllCopies());
    }
    
    @GetMapping("/{bookId}/copies")
    @Operation(summary = "Get all copies of a specific book")
    public ResponseEntity<List<BookCopy>> getCopiesByBookId(@PathVariable Long bookId) {
        return ResponseEntity.ok(bookCopyService.getCopiesByBookId(bookId));
    }
    
    @GetMapping("/copies/{id}")
    @Operation(summary = "Get book copy by ID")
    public ResponseEntity<BookCopy> getCopyById(@PathVariable Long id) {
        return bookCopyService.getCopyById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/{bookId}/copies")
    @Operation(summary = "Add a new copy for a book")
    public ResponseEntity<BookCopy> createCopy(@PathVariable Long bookId, @RequestBody BookCopy bookCopy) {
        try {
            bookCopy.setBookId(bookId);
            BookCopy createdCopy = bookCopyService.createCopy(bookCopy);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCopy);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/copies/{id}")
    @Operation(summary = "Update a book copy")
    public ResponseEntity<BookCopy> updateCopy(@PathVariable Long id, @RequestBody BookCopy bookCopy) {
        try {
            BookCopy updatedCopy = bookCopyService.updateCopy(id, bookCopy);
            return ResponseEntity.ok(updatedCopy);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/copies/{id}")
    @Operation(summary = "Delete a book copy")
    public ResponseEntity<Void> deleteCopy(@PathVariable Long id) {
        try {
            bookCopyService.deleteCopy(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/copies/status/{status}")
    @Operation(summary = "Filter copies by status")
    public ResponseEntity<List<BookCopy>> getCopiesByStatus(@PathVariable CopyStatus status) {
        return ResponseEntity.ok(bookCopyService.getCopiesByStatus(status));
    }
}
