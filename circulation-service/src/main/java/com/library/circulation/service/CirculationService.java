package com.library.circulation.service;

import com.library.circulation.client.CatalogServiceClient;
import com.library.circulation.dto.BookCopyDTO;
import com.library.circulation.dto.MemberDTO;
import com.library.circulation.entity.*;
import com.library.circulation.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class CirculationService {
    
    @Autowired
    private BookIssueRepository bookIssueRepository;
    
    @Autowired
    private CatalogServiceClient catalogServiceClient;
    
    private static final int LOAN_PERIOD_DAYS = 14;
    private static final double FINE_PER_DAY = 5.0;
    
    public List<BookIssue> getAllIssues() {
        return bookIssueRepository.findAll();
    }
    
    public Optional<BookIssue> getIssueById(Long id) {
        return bookIssueRepository.findById(id);
    }
    
    public List<BookIssue> getIssuesByMember(Long memberId) {
        return bookIssueRepository.findByMemberId(memberId);
    }
    
    public List<BookIssue> getActiveIssues() {
        return bookIssueRepository.findByStatus(IssueStatus.ISSUED);
    }
    
    @Transactional
    public BookIssue issueBook(Long memberId, Long bookCopyId) {
        // Validate member using Feign client
        MemberDTO member = catalogServiceClient.getMemberById(memberId);
        if (member == null) {
            throw new RuntimeException("Member not found with id: " + memberId);
        }
        
        if (!"ACTIVE".equals(member.getMembershipStatus())) {
            throw new RuntimeException("Member is not active");
        }
        
        // Validate book copy using Feign client
        BookCopyDTO bookCopy = catalogServiceClient.getBookCopyById(bookCopyId);
        if (bookCopy == null) {
            throw new RuntimeException("Book copy not found with id: " + bookCopyId);
        }
        
        if (!"AVAILABLE".equals(bookCopy.getStatus())) {
            throw new RuntimeException("Book copy is not available");
        }
        
        // Create issue record
        BookIssue issue = new BookIssue();
        issue.setMemberId(memberId);
        issue.setBookCopyId(bookCopyId);
        issue.setBookId(bookCopy.getBookId());
        issue.setIssueDate(LocalDate.now());
        issue.setDueDate(LocalDate.now().plusDays(LOAN_PERIOD_DAYS));
        issue.setStatus(IssueStatus.ISSUED);
        issue.setFine(0.0);
        
        // Update book copy status via Feign client
        catalogServiceClient.updateBookCopyStatus(bookCopyId, "ISSUED");
        
        return bookIssueRepository.save(issue);
    }
    
    @Transactional
    public BookIssue returnBook(Long issueId) {
        BookIssue issue = bookIssueRepository.findById(issueId)
            .orElseThrow(() -> new RuntimeException("Issue record not found with id: " + issueId));
        
        if (issue.getStatus() == IssueStatus.RETURNED) {
            throw new RuntimeException("Book already returned");
        }
        
        // Calculate fine if overdue
        LocalDate returnDate = LocalDate.now();
        issue.setReturnDate(returnDate);
        
        if (returnDate.isAfter(issue.getDueDate())) {
            long daysOverdue = ChronoUnit.DAYS.between(issue.getDueDate(), returnDate);
            double fine = daysOverdue * FINE_PER_DAY;
            issue.setFine(fine);
        }
        
        issue.setStatus(IssueStatus.RETURNED);
        
        // Update book copy status via Feign client
        catalogServiceClient.updateBookCopyStatus(issue.getBookCopyId(), "AVAILABLE");
        
        return bookIssueRepository.save(issue);
    }
    
    public List<BookIssue> getOverdueBooks() {
        return bookIssueRepository.findByDueDateBeforeAndStatus(LocalDate.now(), IssueStatus.ISSUED);
    }
    
    public Double calculateFine(Long issueId) {
        BookIssue issue = bookIssueRepository.findById(issueId)
            .orElseThrow(() -> new RuntimeException("Issue record not found"));
        
        if (issue.getStatus() == IssueStatus.RETURNED) {
            return issue.getFine();
        }
        
        LocalDate today = LocalDate.now();
        if (today.isAfter(issue.getDueDate())) {
            long daysOverdue = ChronoUnit.DAYS.between(issue.getDueDate(), today);
            return daysOverdue * FINE_PER_DAY;
        }
        
        return 0.0;
    }
    
    public List<BookIssue> getIssuesByDateRange(LocalDate startDate, LocalDate endDate) {
        return bookIssueRepository.findByIssueDateBetween(startDate, endDate);
    }
    
    public long countActiveIssues() {
        return bookIssueRepository.countIssuedBooks();
    }
    
    public Double getTotalFinesBetweenDates(LocalDate startDate, LocalDate endDate) {
        Double total = bookIssueRepository.getTotalFinesBetweenDates(startDate, endDate);
        return total != null ? total : 0.0;
    }
}
