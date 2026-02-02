package com.library.member.service;

import com.library.member.entity.Staff;
import com.library.member.entity.StaffStatus;
import com.library.member.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StaffService {
    
    @Autowired
    private StaffRepository staffRepository;
    
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }
    
    public Optional<Staff> getStaffById(Long id) {
        return staffRepository.findById(id);
    }
    
    public Staff createStaff(Staff staff) {
        if (staffRepository.existsByEmail(staff.getEmail())) {
            throw new RuntimeException("Staff with email " + staff.getEmail() + " already exists");
        }
        
        if (staff.getStatus() == null) {
            staff.setStatus(StaffStatus.ACTIVE);
        }
        
        return staffRepository.save(staff);
    }
    
    public Staff updateStaff(Long id, Staff staffDetails) {
        Staff staff = staffRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Staff not found with id: " + id));
        
        staff.setName(staffDetails.getName());
        staff.setRole(staffDetails.getRole());
        if (staffDetails.getStatus() != null) {
            staff.setStatus(staffDetails.getStatus());
        }
        
        return staffRepository.save(staff);
    }
    
    public void deleteStaff(Long id) {
        Staff staff = staffRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Staff not found with id: " + id));
        staffRepository.delete(staff);
    }
    
    public List<Staff> getActiveStaff() {
        return staffRepository.findByStatus(StaffStatus.ACTIVE);
    }
}
