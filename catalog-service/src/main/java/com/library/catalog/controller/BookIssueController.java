package com.library.catalog.controller;

import com.library.catalog.entity.BookIssue;
import com.library.catalog.service.BookIssueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/staff/issues")
@Tag(name = "Book Issue/Return (Staff)", description = "Librarian APIs for issuing and returning books")
@CrossOrigin(origins = "*")
public class BookIssueController {
    
    @Autowired
    private BookIssueService bookIssueService;
    
    @GetMapping
    @Operation(summary = "Get all book issues")
    public ResponseEntity<List<BookIssue>> getAllIssues() {
        return ResponseEntity.ok(bookIssueService.getAllIssues());
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Get issue by ID")
    public ResponseEntity<BookIssue> getIssueById(@PathVariable Long id) {
        return bookIssueService.getIssueById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/member/{memberId}")
    @Operation(summary = "Get all issues for a member")
    public ResponseEntity<List<BookIssue>> getIssuesByMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(bookIssueService.getIssuesByMember(memberId));
    }
    
    @GetMapping("/active")
    @Operation(summary = "Get all active issues")
    public ResponseEntity<List<BookIssue>> getActiveIssues() {
        return ResponseEntity.ok(bookIssueService.getActiveIssues());
    }
    
    @PostMapping
    @Operation(summary = "Issue a book to a member")
    public ResponseEntity<?> issueBook(@RequestBody Map<String, Long> request) {
        try {
            Long memberId = request.get("memberId");
            Long bookCopyId = request.get("bookCopyId");
            
            BookIssue issue = bookIssueService.issueBook(memberId, bookCopyId);
            return ResponseEntity.status(HttpStatus.CREATED).body(issue);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PutMapping("/{id}/return")
    @Operation(summary = "Return a book")
    public ResponseEntity<?> returnBook(@PathVariable Long id) {
        try {
            BookIssue issue = bookIssueService.returnBook(id);
            return ResponseEntity.ok(issue);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @GetMapping("/overdue")
    @Operation(summary = "Get all overdue books")
    public ResponseEntity<List<BookIssue>> getOverdueBooks() {
        return ResponseEntity.ok(bookIssueService.getOverdueBooks());
    }
    
    @GetMapping("/{id}/fine")
    @Operation(summary = "Calculate fine for an issue")
    public ResponseEntity<Map<String, Double>> calculateFine(@PathVariable Long id) {
        try {
            Double fine = bookIssueService.calculateFine(id);
            Map<String, Double> response = new HashMap<>();
            response.put("fine", fine);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
