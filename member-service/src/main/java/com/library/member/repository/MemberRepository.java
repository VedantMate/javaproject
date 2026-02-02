package com.library.member.repository;

import com.library.member.entity.Member;
import com.library.member.entity.MembershipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    List<Member> findByMembershipStatus(MembershipStatus status);
    List<Member> findByNameContainingIgnoreCase(String name);
    boolean existsByEmail(String email);
}
