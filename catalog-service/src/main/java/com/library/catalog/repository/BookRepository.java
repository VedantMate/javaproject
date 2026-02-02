package com.library.catalog.repository;

import com.library.catalog.entity.Book;
import com.library.catalog.entity.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorContainingIgnoreCase(String author);
    List<Book> findByCategory(String category);
    List<Book> findByStatus(BookStatus status);
    boolean existsByIsbn(String isbn);
}
