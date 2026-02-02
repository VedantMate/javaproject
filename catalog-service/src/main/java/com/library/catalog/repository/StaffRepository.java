package com.library.catalog.repository;

import com.library.catalog.entity.Staff;
import com.library.catalog.entity.StaffRole;
import com.library.catalog.entity.StaffStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByEmail(String email);
    List<Staff> findByRole(StaffRole role);
    List<Staff> findByStatus(StaffStatus status);
    boolean existsByEmail(String email);
}
