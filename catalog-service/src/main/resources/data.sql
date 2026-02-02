-- Insert sample books
INSERT INTO books (id, title, author, isbn, category, status) VALUES
(1, 'Clean Code', 'Robert C. Martin', '978-0132350884', 'Programming', 'ACTIVE'),
(2, 'The Pragmatic Programmer', 'Andrew Hunt', '978-0201616224', 'Programming', 'ACTIVE'),
(3, 'Design Patterns', 'Gang of Four', '978-0201633612', 'Software Engineering', 'ACTIVE'),
(4, 'Introduction to Algorithms', 'Thomas H. Cormen', '978-0262033848', 'Computer Science', 'ACTIVE'),
(5, 'The Clean Coder', 'Robert C. Martin', '978-0137081073', 'Programming', 'ACTIVE');

-- Insert sample book copies
INSERT INTO book_copies (id, book_id, rack_no, status) VALUES
(1, 1, 'A1-001', 'AVAILABLE'),
(2, 1, 'A1-002', 'AVAILABLE'),
(3, 1, 'A1-003', 'ISSUED'),
(4, 2, 'A2-001', 'AVAILABLE'),
(5, 2, 'A2-002', 'AVAILABLE'),
(6, 3, 'B1-001', 'AVAILABLE'),
(7, 3, 'B1-002', 'DAMAGED'),
(8, 4, 'C1-001', 'AVAILABLE'),
(9, 4, 'C1-002', 'AVAILABLE'),
(10, 5, 'A3-001', 'AVAILABLE');

-- Insert sample members
INSERT INTO members (id, name, email, phone, membership_status) VALUES
(1, 'John Doe', 'john.doe@email.com', '1234567890', 'ACTIVE'),
(2, 'Jane Smith', 'jane.smith@email.com', '0987654321', 'ACTIVE'),
(3, 'Bob Johnson', 'bob.johnson@email.com', '5555555555', 'ACTIVE'),
(4, 'Alice Williams', 'alice.williams@email.com', '4444444444', 'INACTIVE');

-- Insert sample staff
INSERT INTO staff (id, name, role, email, status) VALUES
(1, 'Admin User', 'ADMIN', 'admin@library.com', 'ACTIVE'),
(2, 'Sarah Librarian', 'LIBRARIAN', 'sarah@library.com', 'ACTIVE'),
(3, 'Mike Manager', 'MANAGER', 'mike@library.com', 'ACTIVE'),
(4, 'Tom Librarian', 'LIBRARIAN', 'tom@library.com', 'ACTIVE');

-- Insert sample book issues
INSERT INTO book_issues (id, member_id, book_copy_id, book_id, issue_date, due_date, return_date, fine, status) VALUES
(1, 1, 3, 1, '2026-01-15', '2026-01-29', NULL, 0.0, 'ISSUED'),
(2, 2, 4, 2, '2026-01-10', '2026-01-24', '2026-01-22', 0.0, 'RETURNED'),
(3, 3, 6, 3, '2026-01-05', '2026-01-19', '2026-01-25', 30.0, 'RETURNED');
