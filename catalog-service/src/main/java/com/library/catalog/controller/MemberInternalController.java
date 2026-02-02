package com.library.catalog.controller;

import com.library.catalog.entity.Member;
import com.library.catalog.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@Tag(name = "Member Internal API", description = "Internal APIs for member operations")
@CrossOrigin(origins = "*")
public class MemberInternalController {
    
    @Autowired
    private MemberService memberService;
    
    @GetMapping("/{id}")
    @Operation(summary = "Get member by ID (for internal service use)")
    public ResponseEntity<Member> getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    @GetMapping("/count")
    @Operation(summary = "Get total count of members")
    public ResponseEntity<Long> getTotalMembersCount() {
        return ResponseEntity.ok(memberService.getTotalCount());
    }
}
