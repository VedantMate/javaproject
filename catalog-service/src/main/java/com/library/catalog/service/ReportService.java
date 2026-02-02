package com.library.catalog.service;

import com.library.catalog.repository.*;
import com.library.catalog.entity.CopyStatus;
import com.library.catalog.entity.IssueStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service
public class ReportService {
    
    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private BookCopyRepository bookCopyRepository;
    
    @Autowired
    private BookIssueRepository bookIssueRepository;
    
    @Autowired
    private MemberRepository memberRepository;
    
    public Map<String, Object> getInventoryReport() {
        Map<String, Object> report = new HashMap<>();
        
        long totalBooks = bookRepository.count();
        long totalCopies = bookCopyRepository.count();
        long availableCopies = bookCopyRepository.findByStatus(CopyStatus.AVAILABLE).size();
        long issuedCopies = bookCopyRepository.findByStatus(CopyStatus.ISSUED).size();
        long damagedCopies = bookCopyRepository.findByStatus(CopyStatus.DAMAGED).size();
        long lostCopies = bookCopyRepository.findByStatus(CopyStatus.LOST).size();
        
        report.put("totalBooks", totalBooks);
        report.put("totalCopies", totalCopies);
        report.put("availableCopies", availableCopies);
        report.put("issuedCopies", issuedCopies);
        report.put("damagedCopies", damagedCopies);
        report.put("lostCopies", lostCopies);
        
        return report;
    }
    
    public Map<String, Object> getIssueReport(LocalDate startDate, LocalDate endDate) {
        Map<String, Object> report = new HashMap<>();
        
        var issues = bookIssueRepository.findByIssueDateBetween(startDate, endDate);
        long totalIssues = issues.size();
        long returnedBooks = issues.stream()
            .filter(issue -> issue.getStatus() == IssueStatus.RETURNED)
            .count();
        
        report.put("totalIssues", totalIssues);
        report.put("returnedBooks", returnedBooks);
        report.put("activeIssues", totalIssues - returnedBooks);
        report.put("issues", issues);
        
        return report;
    }
    
    public Map<String, Object> getFineReport(LocalDate startDate, LocalDate endDate) {
        Map<String, Object> report = new HashMap<>();
        
        Double totalFines = bookIssueRepository.getTotalFinesBetweenDates(startDate, endDate);
        if (totalFines == null) {
            totalFines = 0.0;
        }
        
        var overdueBooks = bookIssueRepository.findByDueDateBeforeAndStatus(LocalDate.now(), IssueStatus.ISSUED);
        
        report.put("totalFines", totalFines);
        report.put("overdueCount", overdueBooks.size());
        report.put("overdueBooks", overdueBooks);
        
        return report;
    }
    
    public Map<String, Object> getDashboardData() {
        Map<String, Object> dashboard = new HashMap<>();
        
        long totalMembers = memberRepository.count();
        long activeIssues = bookIssueRepository.countIssuedBooks();
        long availableCopies = bookCopyRepository.findByStatus(CopyStatus.AVAILABLE).size();
        var overdueBooks = bookIssueRepository.findByDueDateBeforeAndStatus(LocalDate.now(), IssueStatus.ISSUED);
        
        dashboard.put("totalMembers", totalMembers);
        dashboard.put("activeIssues", activeIssues);
        dashboard.put("availableCopies", availableCopies);
        dashboard.put("overdueCount", overdueBooks.size());
        
        return dashboard;
    }
}
