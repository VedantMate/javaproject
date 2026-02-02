package com.library.circulation.client;

import com.library.circulation.dto.MemberDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalog-service")
public interface CatalogServiceClient {
    
    @GetMapping("/api/members/{id}")
    MemberDTO getMemberById(@PathVariable("id") Long id);
    
    @GetMapping("/api/book-copies/{id}")
    com.library.circulation.dto.BookCopyDTO getBookCopyById(@PathVariable("id") Long id);
    
    @GetMapping("/api/book-copies/{id}/update-status")
    void updateBookCopyStatus(@PathVariable("id") Long id, @org.springframework.web.bind.annotation.RequestParam String status);
}
