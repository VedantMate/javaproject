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
