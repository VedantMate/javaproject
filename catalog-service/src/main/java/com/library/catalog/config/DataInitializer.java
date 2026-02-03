package com.library.catalog.config;

import com.library.catalog.entity.*;
import com.library.catalog.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(
            BookRepository bookRepository,
            BookCopyRepository bookCopyRepository,
            MemberRepository memberRepository,
            StaffRepository staffRepository) {
        return args -> {
            // Initialize Books
            if (bookRepository.count() == 0) {
                Book book1 = new Book(null, "Clean Code", "Robert C. Martin", "978-0132350884", "Programming", BookStatus.ACTIVE);
                Book book2 = new Book(null, "The Pragmatic Programmer", "Andrew Hunt", "978-0201616224", "Programming", BookStatus.ACTIVE);
                Book book3 = new Book(null, "Design Patterns", "Gang of Four", "978-0201633612", "Software Engineering", BookStatus.ACTIVE);
                Book book4 = new Book(null, "Introduction to Algorithms", "Thomas H. Cormen", "978-0262033848", "Computer Science", BookStatus.ACTIVE);
                Book book5 = new Book(null, "The Clean Coder", "Robert C. Martin", "978-0137081073", "Programming", BookStatus.ACTIVE);
                
                book1 = bookRepository.save(book1);
                book2 = bookRepository.save(book2);
                book3 = bookRepository.save(book3);
                book4 = bookRepository.save(book4);
                book5 = bookRepository.save(book5);

                // Initialize Book Copies
                bookCopyRepository.save(new BookCopy(null, book1.getId(), "A1-001", CopyStatus.AVAILABLE));
                bookCopyRepository.save(new BookCopy(null, book1.getId(), "A1-002", CopyStatus.AVAILABLE));
                bookCopyRepository.save(new BookCopy(null, book1.getId(), "A1-003", CopyStatus.ISSUED));
                bookCopyRepository.save(new BookCopy(null, book2.getId(), "A2-001", CopyStatus.AVAILABLE));
                bookCopyRepository.save(new BookCopy(null, book2.getId(), "A2-002", CopyStatus.AVAILABLE));
                bookCopyRepository.save(new BookCopy(null, book3.getId(), "B1-001", CopyStatus.AVAILABLE));
                bookCopyRepository.save(new BookCopy(null, book3.getId(), "B1-002", CopyStatus.DAMAGED));
                bookCopyRepository.save(new BookCopy(null, book4.getId(), "C1-001", CopyStatus.AVAILABLE));
                bookCopyRepository.save(new BookCopy(null, book4.getId(), "C1-002", CopyStatus.AVAILABLE));
                bookCopyRepository.save(new BookCopy(null, book5.getId(), "A3-001", CopyStatus.AVAILABLE));

                System.out.println("✅ Books and book copies initialized");
            }

            // Initialize Members
            if (memberRepository.count() == 0) {
                memberRepository.save(new Member(null, "John Doe", "john.doe@email.com", "1234567890", "password123", MembershipStatus.ACTIVE));
                memberRepository.save(new Member(null, "Jane Smith", "jane.smith@email.com", "0987654321", "password123", MembershipStatus.ACTIVE));
                memberRepository.save(new Member(null, "Bob Johnson", "bob.johnson@email.com", "5555555555", "password123", MembershipStatus.ACTIVE));
                memberRepository.save(new Member(null, "Alice Williams", "alice.williams@email.com", "4444444444", "password123", MembershipStatus.INACTIVE));

                System.out.println("✅ Members initialized");
            }

            // Initialize Staff
            if (staffRepository.count() == 0) {
                staffRepository.save(new Staff(null, "Admin User", StaffRole.ADMIN, "staffadmin@library.com", "password123", StaffStatus.ACTIVE));
                staffRepository.save(new Staff(null, "Sarah Librarian", StaffRole.LIBRARIAN, "sarah@library.com", "password123", StaffStatus.ACTIVE));
                staffRepository.save(new Staff(null, "Mike Manager", StaffRole.MANAGER, "mike@library.com", "password123", StaffStatus.ACTIVE));
                staffRepository.save(new Staff(null, "Tom Librarian", StaffRole.LIBRARIAN, "tom@library.com", "password123", StaffStatus.ACTIVE));

                System.out.println("✅ Staff initialized");
            }

            System.out.println("✅ All data initialization complete!");
        };
    }
}
