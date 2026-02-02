package com.library.catalog.config;

import com.library.catalog.entity.*;
import com.library.catalog.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData(
            UserRepository userRepository,
            BookRepository bookRepository,
            BookCopyRepository bookCopyRepository,
            MemberRepository memberRepository,
            StaffRepository staffRepository) {
        return args -> {
            // Initialize Users
            if (userRepository.count() == 0) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("password123"));
                admin.setEmail("admin@library.com");
                admin.setRole("ADMIN");
                admin.setEnabled(true);
                userRepository.save(admin);

                User librarian = new User();
                librarian.setUsername("librarian");
                librarian.setPassword(passwordEncoder.encode("password123"));
                librarian.setEmail("librarian@library.com");
                librarian.setRole("LIBRARIAN");
                librarian.setEnabled(true);
                userRepository.save(librarian);

                User manager = new User();
                manager.setUsername("manager");
                manager.setPassword(passwordEncoder.encode("password123"));
                manager.setEmail("manager@library.com");
                manager.setRole("MANAGER");
                manager.setEnabled(true);
                userRepository.save(manager);

                User member = new User();
                member.setUsername("member");
                member.setPassword(passwordEncoder.encode("password123"));
                member.setEmail("member@library.com");
                member.setRole("MEMBER");
                member.setEnabled(true);
                userRepository.save(member);

                System.out.println("✅ Default users initialized");
            }

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
                memberRepository.save(new Member(null, "John Doe", "john.doe@email.com", "1234567890", passwordEncoder.encode("password123"), MembershipStatus.ACTIVE));
                memberRepository.save(new Member(null, "Jane Smith", "jane.smith@email.com", "0987654321", passwordEncoder.encode("password123"), MembershipStatus.ACTIVE));
                memberRepository.save(new Member(null, "Bob Johnson", "bob.johnson@email.com", "5555555555", passwordEncoder.encode("password123"), MembershipStatus.ACTIVE));
                memberRepository.save(new Member(null, "Alice Williams", "alice.williams@email.com", "4444444444", passwordEncoder.encode("password123"), MembershipStatus.INACTIVE));

                System.out.println("✅ Members initialized");
            }

            // Initialize Staff
            if (staffRepository.count() == 0) {
                staffRepository.save(new Staff(null, "Admin User", StaffRole.ADMIN, "staffadmin@library.com", passwordEncoder.encode("password123"), StaffStatus.ACTIVE));
                staffRepository.save(new Staff(null, "Sarah Librarian", StaffRole.LIBRARIAN, "sarah@library.com", passwordEncoder.encode("password123"), StaffStatus.ACTIVE));
                staffRepository.save(new Staff(null, "Mike Manager", StaffRole.MANAGER, "mike@library.com", passwordEncoder.encode("password123"), StaffStatus.ACTIVE));
                staffRepository.save(new Staff(null, "Tom Librarian", StaffRole.LIBRARIAN, "tom@library.com", passwordEncoder.encode("password123"), StaffStatus.ACTIVE));

                System.out.println("✅ Staff initialized");
            }

            System.out.println("✅ All data initialization complete! Default password for all users: password123");
        };
    }
}
