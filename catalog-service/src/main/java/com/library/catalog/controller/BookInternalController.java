package com.library.catalog.controller;

import com.library.catalog.entity.Book;
import com.library.catalog.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Book Internal API", description = "Internal APIs for book operations")
@CrossOrigin(origins = "*")
public class BookInternalController {
    
    @Autowired
    private BookService bookService;
    
    @GetMapping
    @Operation(summary = "Get all books (for internal service use)")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get book by ID (for internal service use)")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/count")
    @Operation(summary = "Get total count of books")
    public ResponseEntity<Long> getTotalBooksCount() {
        return ResponseEntity.ok(bookService.getTotalCount());
    }
}
