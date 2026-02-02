package com.library.catalog.repository;

import com.library.catalog.entity.BookCopy;
import com.library.catalog.entity.CopyStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookCopyRepository extends JpaRepository<BookCopy, Long> {
    List<BookCopy> findByBookId(Long bookId);
    List<BookCopy> findByStatus(CopyStatus status);
    List<BookCopy> findByBookIdAndStatus(Long bookId, CopyStatus status);
    long countByBookIdAndStatus(Long bookId, CopyStatus status);
}
