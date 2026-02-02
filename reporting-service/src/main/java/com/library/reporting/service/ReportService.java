package com.library.reporting.service;

import com.library.reporting.client.CatalogServiceClient;
import com.library.reporting.client.CirculationServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    
    @Autowired
    private CatalogServiceClient catalogServiceClient;
    
    @Autowired
    private CirculationServiceClient circulationServiceClient;
    
    public Map<String, Object> getInventoryReport() {
        Map<String, Object> report = new HashMap<>();
        
        try {
            Long totalBooks = catalogServiceClient.getTotalBooks();
            Long totalCopies = catalogServiceClient.getTotalCopies();
            Map<String, Long> copiesByStatus = catalogServiceClient.getBookCopiesByStatus();
            
            report.put("totalBooks", totalBooks);
            report.put("totalCopies", totalCopies);
            report.put("availableCopies", copiesByStatus.getOrDefault("AVAILABLE", 0L));
            report.put("issuedCopies", copiesByStatus.getOrDefault("ISSUED", 0L));
            report.put("damagedCopies", copiesByStatus.getOrDefault("DAMAGED", 0L));
            report.put("lostCopies", copiesByStatus.getOrDefault("LOST", 0L));
        } catch (Exception e) {
            report.put("error", "Failed to fetch inventory data: " + e.getMessage());
        }
        
        return report;
    }
    
    public Map<String, Object> getIssueReport(LocalDate startDate, LocalDate endDate) {
        Map<String, Object> report = new HashMap<>();
        
        try {
            List<Object> allIssues = circulationServiceClient.getAllIssues();
            
            // Filter issues by date range (would need proper implementation)
            long totalIssues = allIssues.size();
            
            report.put("totalIssues", totalIssues);
            report.put("startDate", startDate);
            report.put("endDate", endDate);
            report.put("issues", allIssues);
        } catch (Exception e) {
            report.put("error", "Failed to fetch issue data: " + e.getMessage());
        }
        
        return report;
    }
    
    public Map<String, Object> getFineReport(LocalDate startDate, LocalDate endDate) {
        Map<String, Object> report = new HashMap<>();
        
        try {
            Double totalFines = circulationServiceClient.getTotalFinesBetweenDates(startDate, endDate);
            List<Object> overdueBooks = circulationServiceClient.getOverdueIssues();
            
            report.put("totalFines", totalFines != null ? totalFines : 0.0);
            report.put("overdueCount", overdueBooks.size());
            report.put("overdueBooks", overdueBooks);
            report.put("startDate", startDate);
            report.put("endDate", endDate);
        } catch (Exception e) {
            report.put("error", "Failed to fetch fine data: " + e.getMessage());
        }
        
        return report;
    }
    
    public Map<String, Object> getDashboardData() {
        Map<String, Object> dashboard = new HashMap<>();
        
        try {
            Long totalMembers = catalogServiceClient.getTotalMembers();
            List<Object> activeIssues = circulationServiceClient.getActiveIssues();
            List<Object> overdueBooks = circulationServiceClient.getOverdueIssues();
            Map<String, Long> copiesByStatus = catalogServiceClient.getBookCopiesByStatus();
            
            dashboard.put("totalMembers", totalMembers);
            dashboard.put("activeIssues", activeIssues.size());
            dashboard.put("availableCopies", copiesByStatus.getOrDefault("AVAILABLE", 0L));
            dashboard.put("overdueCount", overdueBooks.size());
        } catch (Exception e) {
            dashboard.put("error", "Failed to fetch dashboard data: " + e.getMessage());
        }
        
        return dashboard;
    }
    
    public Map<String, Object> getComprehensiveReport() {
        Map<String, Object> report = new HashMap<>();
        
        report.put("inventory", getInventoryReport());
        report.put("dashboard", getDashboardData());
        report.put("timestamp", LocalDate.now());
        
        return report;
    }
}
