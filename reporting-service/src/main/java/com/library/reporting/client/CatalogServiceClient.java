package com.library.reporting.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@FeignClient(name = "catalog-service")
public interface CatalogServiceClient {
    
    @GetMapping("/api/books/count")
    Long getTotalBooks();
    
    @GetMapping("/api/book-copies/count")
    Long getTotalCopies();
    
    @GetMapping("/api/book-copies/count-by-status")
    Map<String, Long> getBookCopiesByStatus();
    
    @GetMapping("/api/members/count")
    Long getTotalMembers();
    
    @GetMapping("/api/books")
    List<Object> getAllBooks();
}
