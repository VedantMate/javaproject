package com.library.catalog.repository;

import com.library.catalog.entity.BookIssue;
import com.library.catalog.entity.IssueStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookIssueRepository extends JpaRepository<BookIssue, Long> {
    List<BookIssue> findByMemberId(Long memberId);
    List<BookIssue> findByStatus(IssueStatus status);
    List<BookIssue> findByMemberIdAndStatus(Long memberId, IssueStatus status);
    List<BookIssue> findByDueDateBeforeAndStatus(LocalDate date, IssueStatus status);
    List<BookIssue> findByIssueDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT COUNT(i) FROM BookIssue i WHERE i.status = 'ISSUED'")
    long countIssuedBooks();
    
    @Query("SELECT SUM(i.fine) FROM BookIssue i WHERE i.returnDate BETWEEN :startDate AND :endDate")
    Double getTotalFinesBetweenDates(LocalDate startDate, LocalDate endDate);
}
