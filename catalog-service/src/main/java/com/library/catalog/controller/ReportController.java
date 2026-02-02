package com.library.catalog.controller;

import com.library.catalog.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/manager/reports")
@Tag(name = "Reports & Monitoring (Manager)", description = "Manager APIs for reports and monitoring")
@CrossOrigin(origins = "*")
public class ReportController {
    
    @Autowired
    private ReportService reportService;
    
    @GetMapping("/inventory")
    @Operation(summary = "Get inventory report")
    public ResponseEntity<Map<String, Object>> getInventoryReport() {
        return ResponseEntity.ok(reportService.getInventoryReport());
    }
    
    @GetMapping("/issues")
    @Operation(summary = "Get issue report for date range")
    public ResponseEntity<Map<String, Object>> getIssueReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(reportService.getIssueReport(startDate, endDate));
    }
    
    @GetMapping("/fines")
    @Operation(summary = "Get fine report for date range")
    public ResponseEntity<Map<String, Object>> getFineReport(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(reportService.getFineReport(startDate, endDate));
    }
    
    @GetMapping("/dashboard")
    @Operation(summary = "Get dashboard data")
    public ResponseEntity<Map<String, Object>> getDashboardData() {
        return ResponseEntity.ok(reportService.getDashboardData());
    }
}
