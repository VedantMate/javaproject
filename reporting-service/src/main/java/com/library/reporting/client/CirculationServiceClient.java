package com.library.reporting.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "circulation-service")
public interface CirculationServiceClient {
    
    @GetMapping("/api/circulation/issues/active")
    List<Object> getActiveIssues();
    
    @GetMapping("/api/circulation/issues/overdue")
    List<Object> getOverdueIssues();
    
    @GetMapping("/api/circulation/issues")
    List<Object> getAllIssues();
    
    @GetMapping("/api/circulation/issues/count-active")
    Long countActiveIssues();
    
    @GetMapping("/api/circulation/total-fines")
    Double getTotalFinesBetweenDates(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate);
}
