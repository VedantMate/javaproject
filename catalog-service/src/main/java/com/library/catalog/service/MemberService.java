package com.library.catalog.service;

import com.library.catalog.entity.Member;
import com.library.catalog.entity.MembershipStatus;
import com.library.catalog.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    
    @Autowired
    private MemberRepository memberRepository;
    
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
    
    public Optional<Member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }
    
    public Member createMember(Member member) {
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new RuntimeException("Member with email " + member.getEmail() + " already exists");
        }
        
        if (member.getMembershipStatus() == null) {
            member.setMembershipStatus(MembershipStatus.ACTIVE);
        }
        
        return memberRepository.save(member);
    }
    
    public Member updateMember(Long id, Member memberDetails) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));
        
        member.setName(memberDetails.getName());
        member.setPhone(memberDetails.getPhone());
        if (memberDetails.getMembershipStatus() != null) {
            member.setMembershipStatus(memberDetails.getMembershipStatus());
        }
        
        return memberRepository.save(member);
    }
    
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Member not found with id: " + id));
        memberRepository.delete(member);
    }
    
    public List<Member> getActiveMembers() {
        return memberRepository.findByMembershipStatus(MembershipStatus.ACTIVE);
    }
    
    public List<Member> searchMembersByName(String name) {
        return memberRepository.findByNameContainingIgnoreCase(name);
    }
}
