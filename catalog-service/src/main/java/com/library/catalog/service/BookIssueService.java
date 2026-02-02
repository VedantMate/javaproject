package com.library.catalog.service;

import com.library.catalog.entity.*;
import com.library.catalog.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class BookIssueService {
    
    @Autowired
    private BookIssueRepository bookIssueRepository;
    
    @Autowired
    private BookCopyRepository bookCopyRepository;
    
    @Autowired
    private MemberRepository memberRepository;
    
    @Autowired
    private BookRepository bookRepository;
    
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
        // Validate member
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new RuntimeException("Member not found with id: " + memberId));
        
        if (member.getMembershipStatus() != MembershipStatus.ACTIVE) {
            throw new RuntimeException("Member is not active");
        }
        
        // Validate book copy
        BookCopy bookCopy = bookCopyRepository.findById(bookCopyId)
            .orElseThrow(() -> new RuntimeException("Book copy not found with id: " + bookCopyId));
        
        if (bookCopy.getStatus() != CopyStatus.AVAILABLE) {
            throw new RuntimeException("Book copy is not available");
        }
        
        // Validate book exists
        bookRepository.findById(bookCopy.getBookId())
            .orElseThrow(() -> new RuntimeException("Book not found with id: " + bookCopy.getBookId()));
        
        // Create issue record
        BookIssue issue = new BookIssue();
        issue.setMemberId(memberId);
        issue.setBookCopyId(bookCopyId);
        issue.setBookId(bookCopy.getBookId());
        issue.setIssueDate(LocalDate.now());
        issue.setDueDate(LocalDate.now().plusDays(LOAN_PERIOD_DAYS));
        issue.setStatus(IssueStatus.ISSUED);
        issue.setFine(0.0);
        
        // Update book copy status
        bookCopy.setStatus(CopyStatus.ISSUED);
        bookCopyRepository.save(bookCopy);
        
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
        
        // Update book copy status
        BookCopy bookCopy = bookCopyRepository.findById(issue.getBookCopyId())
            .orElseThrow(() -> new RuntimeException("Book copy not found"));
        bookCopy.setStatus(CopyStatus.AVAILABLE);
        bookCopyRepository.save(bookCopy);
        
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
}
