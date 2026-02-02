# Library Management System - Testing Guide

## Quick Start

### 1. Make Scripts Executable
```bash
chmod +x start-all.sh
chmod +x stop-all.sh
chmod +x quick-start.sh
chmod +x test-api.sh
```

### 2. Start All Services
```bash
./start-all.sh
```

This will:
- Start Eureka Server (8761)
- Start API Gateway (8080)
- Start Catalog Service (8081)
- Start Angular Frontend (4200)
- Wait for all services to be ready

### 3. Alternative: Quick Start (macOS only)
```bash
./quick-start.sh
```
Opens each service in a separate terminal window for easier monitoring.

### 4. Stop All Services
```bash
./stop-all.sh
```

---

## Testing Instructions

### Automated API Testing

Run the automated test script:
```bash
./test-api.sh
```

This tests all major endpoints and provides a summary report.

---

## Manual Testing Guide

### A. Testing via Swagger UI

1. **Open Swagger UI**
   ```
   http://localhost:8081/swagger-ui.html
   ```

2. **Test Admin Functions (Book Management)**
   - Click on "Book Management (Admin)"
   - Try "GET /api/admin/books" - should return 5 books
   - Try "POST /api/admin/books" with:
     ```json
     {
       "title": "My Test Book",
       "author": "Test Author",
       "isbn": "978-1234567890",
       "category": "Testing",
       "status": "ACTIVE"
     }
     ```
   - Try "GET /api/admin/books/search/title?title=Clean"

3. **Test Book Copy Management**
   - Try "GET /api/admin/books/1/copies" - should return copies of book 1
   - Try "POST /api/admin/books/1/copies" with:
     ```json
     {
       "rackNo": "TEST-001",
       "status": "AVAILABLE"
     }
     ```

4. **Test Member Management**
   - Try "GET /api/staff/members" - should return 4 members
   - Try "POST /api/staff/members" with:
     ```json
     {
       "name": "Test Member",
       "email": "testmember@example.com",
       "phone": "1234567890",
       "membershipStatus": "ACTIVE"
     }
     ```

5. **Test Issue/Return Operations**
   - Try "GET /api/staff/issues/active"
   - Try "POST /api/staff/issues" with:
     ```json
     {
       "memberId": 1,
       "bookCopyId": 1
     }
     ```
   - Note the returned issue ID
   - Try "PUT /api/staff/issues/{id}/return"

6. **Test Reports**
   - Try "GET /api/manager/reports/dashboard"
   - Try "GET /api/manager/reports/inventory"
   - Try "GET /api/manager/reports/issues?startDate=2026-01-01&endDate=2026-02-02"

### B. Testing via Frontend UI

1. **Open Frontend**
   ```
   http://localhost:4200
   ```

2. **Test Dashboard**
   - Should see 4 stat cards with metrics
   - Click on quick action buttons

3. **Test Book Management**
   - Click "Books" in navigation
   - Click "Add New Book"
   - Fill form and submit
   - Search for books by title/author
   - Click "Copies" on any book
   - Add a new copy
   - Try updating copy status
   - Try deleting a book (should fail if copies exist)

4. **Test Member Management**
   - Click "Members" in navigation
   - Click "Register New Member"
   - Fill form with:
     - Name: John Test
     - Email: johntest@example.com
     - Phone: 5551234567
     - Status: Active
   - Search for members
   - Edit a member
   - Try changing membership status

5. **Test Staff Management**
   - Click "Staff" in navigation
   - Click "Add New Staff"
   - Create a librarian account
   - View all staff members

6. **Test Book Issue/Return**
   - Click "Issues" in navigation
   - Click "Issue New Book"
   - Select a member and an available book copy
   - Issue the book
   - Go back to issues list
   - Click "Return" on an issued book
   - Check if fine is calculated for overdue books

7. **Test Reports**
   - Click "Reports" in navigation
   - View inventory report
   - Generate issue report with date range
   - Generate fine report

### C. Testing via cURL Commands

**Get All Books:**
```bash
curl http://localhost:8080/api/admin/books
```

**Create a Book:**
```bash
curl -X POST http://localhost:8080/api/admin/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "New Book",
    "author": "New Author",
    "isbn": "978-9999999999",
    "category": "Fiction",
    "status": "ACTIVE"
  }'
```

**Search Books:**
```bash
curl "http://localhost:8080/api/admin/books/search/title?title=Clean"
```

**Get Dashboard Data:**
```bash
curl http://localhost:8080/api/manager/reports/dashboard
```

**Issue a Book:**
```bash
curl -X POST http://localhost:8080/api/staff/issues \
  -H "Content-Type: application/json" \
  -d '{
    "memberId": 2,
    "bookCopyId": 2
  }'
```

**Return a Book:**
```bash
curl -X PUT http://localhost:8080/api/staff/issues/1/return \
  -H "Content-Type: application/json"
```

### D. Testing via H2 Console

1. **Open H2 Console**
   ```
   http://localhost:8081/h2-console
   ```

2. **Login Credentials:**
   - JDBC URL: `jdbc:h2:mem:librarydb`
   - Username: `sa`
   - Password: (leave empty)

3. **Run SQL Queries:**
   ```sql
   -- View all books
   SELECT * FROM BOOKS;
   
   -- View all book copies
   SELECT * FROM BOOK_COPIES;
   
   -- View all members
   SELECT * FROM MEMBERS;
   
   -- View all issues
   SELECT * FROM BOOK_ISSUES;
   
   -- View active issues with details
   SELECT bi.*, m.name as member_name, b.title as book_title
   FROM BOOK_ISSUES bi
   JOIN MEMBERS m ON bi.member_id = m.id
   JOIN BOOKS b ON bi.book_id = b.id
   WHERE bi.status = 'ISSUED';
   
   -- Calculate total fines
   SELECT SUM(fine) as total_fines FROM BOOK_ISSUES;
   ```

---

## Test Scenarios

### Scenario 1: Complete Book Lifecycle
1. Create a new book via UI
2. Add 3 copies of the book
3. Issue one copy to a member
4. Mark one copy as damaged
5. Return the issued copy
6. Delete the damaged copy
7. Try to delete the book (should show all copies)

### Scenario 2: Member and Issue Workflow
1. Register a new member
2. Issue 2 books to the member
3. Return one book on time (no fine)
4. Wait or modify due date to test fine calculation
5. Return overdue book (should calculate fine)
6. View member's issue history

### Scenario 3: Admin Operations
1. Create multiple books in different categories
2. Add multiple copies per book
3. Create staff accounts (librarian, manager)
4. Search books by different criteria
5. Filter copies by status

### Scenario 4: Reporting
1. Issue several books
2. Return some books
3. Generate inventory report
4. Generate issue report for date range
5. Generate fine report
6. View dashboard metrics

---

## Expected Results

### Sample Data Verification
After startup, you should see:
- **5 Books** (Clean Code, Pragmatic Programmer, etc.)
- **10 Book Copies** with different statuses
- **4 Members** (3 active, 1 inactive)
- **4 Staff Members** (1 admin, 2 librarians, 1 manager)
- **3 Book Issues** (1 active, 2 returned)

### Dashboard Metrics
- Total Members: 4
- Active Issues: 1
- Available Copies: 6-7
- Overdue Count: 0-1 (depends on date)

---

## Troubleshooting

### Services Won't Start
```bash
# Check if ports are in use
lsof -i :8761
lsof -i :8080
lsof -i :8081
lsof -i :4200

# Kill processes on ports
lsof -ti:8761 | xargs kill -9
lsof -ti:8080 | xargs kill -9
lsof -ti:8081 | xargs kill -9
lsof -ti:4200 | xargs kill -9
```

### Check Service Status
```bash
# View logs
tail -f logs/eureka-server.log
tail -f logs/api-gateway.log
tail -f logs/catalog-service.log
tail -f logs/angular-frontend.log
```

### Verify Services are Running
```bash
# Check Eureka
curl http://localhost:8761

# Check API Gateway
curl http://localhost:8080/api/admin/books

# Check Catalog Service
curl http://localhost:8081/api/admin/books
```

### Frontend Not Loading
```bash
cd library-frontend
npm install
npm start
```

---

## Performance Testing

### Load Testing (Optional)
Use Apache Bench or similar tools:
```bash
# Test get all books endpoint
ab -n 1000 -c 10 http://localhost:8080/api/admin/books

# Test dashboard endpoint
ab -n 500 -c 5 http://localhost:8080/api/manager/reports/dashboard
```

---

## Clean Up

### Stop All Services
```bash
./stop-all.sh
```

### Remove Logs
```bash
rm -rf logs/*.log
```

### Remove PIDs
```bash
rm -rf pids/*.pid
```

### Complete Clean
```bash
./stop-all.sh
rm -rf logs pids
cd library-frontend && rm -rf node_modules
```

---

## Success Criteria

✅ All services start without errors  
✅ Eureka shows all registered services  
✅ Swagger UI is accessible  
✅ H2 Console is accessible  
✅ Frontend loads without errors  
✅ Sample data is loaded  
✅ All CRUD operations work  
✅ Book issue/return works  
✅ Fine calculation works  
✅ Reports generate correctly  
✅ Search functionality works  
✅ No console errors in browser  

---

## Additional Resources

- **Eureka Dashboard:** Monitor service registration
- **Swagger UI:** Test all REST endpoints
- **H2 Console:** Query database directly
- **Browser DevTools:** Check network requests and console errors
- **Log Files:** Detailed error information

Happy Testing! 🎉
