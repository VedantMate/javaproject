-- Insert sample book issues
INSERT INTO book_issues (id, member_id, book_copy_id, book_id, issue_date, due_date, return_date, fine, status) VALUES
(1, 1, 3, 1, '2026-01-15', '2026-01-29', NULL, 0.0, 'ISSUED'),
(2, 2, 4, 2, '2026-01-10', '2026-01-24', '2026-01-22', 0.0, 'RETURNED'),
(3, 3, 6, 3, '2026-01-05', '2026-01-19', '2026-01-25', 30.0, 'RETURNED');
