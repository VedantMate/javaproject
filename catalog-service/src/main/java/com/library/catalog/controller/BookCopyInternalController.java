package com.library.catalog.controller;

import com.library.catalog.entity.BookCopy;
import com.library.catalog.entity.CopyStatus;
import com.library.catalog.service.BookCopyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/book-copies")
@Tag(name = "Book Copy Internal API", description = "Internal APIs for book copy operations")
@CrossOrigin(origins = "*")
public class BookCopyInternalController {
    
    @Autowired
    private BookCopyService bookCopyService;
    
    @GetMapping("/{id}")
    @Operation(summary = "Get book copy by ID (for internal service use)")
    public ResponseEntity<BookCopy> getBookCopyById(@PathVariable Long id) {
        return bookCopyService.getCopyById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/count")
    @Operation(summary = "Get total count of book copies")
    public ResponseEntity<Long> getTotalCopiesCount() {
        return ResponseEntity.ok(bookCopyService.getTotalCount());
    }
    
    @GetMapping("/count-by-status")
    @Operation(summary = "Get count of copies by status")
    public ResponseEntity<java.util.Map<String, Long>> getCountByStatus() {
        return ResponseEntity.ok(bookCopyService.getCountByStatus());
    }
    
    @PutMapping("/{id}/update-status")
    @Operation(summary = "Update book copy status (for internal use)")
    public ResponseEntity<Void> updateBookCopyStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            bookCopyService.updateStatus(id, CopyStatus.valueOf(status));
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
