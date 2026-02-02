package com.library.circulation.controller;

import com.library.circulation.entity.BookIssue;
import com.library.circulation.service.CirculationService;
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
@RequestMapping("/api/circulation")
@Tag(name = "Circulation Management", description = "APIs for book circulation, issue and return")
@CrossOrigin(origins = "*")
public class CirculationController {
    
    @Autowired
    private CirculationService circulationService;
    
    @GetMapping("/issues")
    @Operation(summary = "Get all book issues")
    public ResponseEntity<List<BookIssue>> getAllIssues() {
        return ResponseEntity.ok(circulationService.getAllIssues());
    }
    
    @GetMapping("/issues/{id}")
    @Operation(summary = "Get issue by ID")
    public ResponseEntity<BookIssue> getIssueById(@PathVariable Long id) {
        return circulationService.getIssueById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/issues/member/{memberId}")
    @Operation(summary = "Get all issues for a member")
    public ResponseEntity<List<BookIssue>> getIssuesByMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(circulationService.getIssuesByMember(memberId));
    }
    
    @GetMapping("/issues/active")
    @Operation(summary = "Get all active issues")
    public ResponseEntity<List<BookIssue>> getActiveIssues() {
        return ResponseEntity.ok(circulationService.getActiveIssues());
    }
    
    @PostMapping("/issue")
    @Operation(summary = "Issue a book to a member")
    public ResponseEntity<?> issueBook(@RequestBody Map<String, Long> request) {
        try {
            Long memberId = request.get("memberId");
            Long bookCopyId = request.get("bookCopyId");
            
            BookIssue issue = circulationService.issueBook(memberId, bookCopyId);
            return ResponseEntity.status(HttpStatus.CREATED).body(issue);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @PutMapping("/return/{id}")
    @Operation(summary = "Return a book")
    public ResponseEntity<?> returnBook(@PathVariable Long id) {
        try {
            BookIssue issue = circulationService.returnBook(id);
            return ResponseEntity.ok(issue);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }
    
    @GetMapping("/issues/overdue")
    @Operation(summary = "Get all overdue books")
    public ResponseEntity<List<BookIssue>> getOverdueBooks() {
        return ResponseEntity.ok(circulationService.getOverdueBooks());
    }
    
    @GetMapping("/fine/{id}")
    @Operation(summary = "Calculate fine for an issue")
    public ResponseEntity<Map<String, Double>> calculateFine(@PathVariable Long id) {
        try {
            Double fine = circulationService.calculateFine(id);
            Map<String, Double> response = new HashMap<>();
            response.put("fine", fine);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/issues/count-active")
    @Operation(summary = "Get count of active issues")
    public ResponseEntity<Long> countActiveIssues() {
        return ResponseEntity.ok(circulationService.countActiveIssues());
    }
    
    @GetMapping("/total-fines")
    @Operation(summary = "Get total fines between dates")
    public ResponseEntity<Double> getTotalFines(
            @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate startDate,
            @RequestParam @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE) java.time.LocalDate endDate) {
        return ResponseEntity.ok(circulationService.getTotalFinesBetweenDates(startDate, endDate));
    }
}
