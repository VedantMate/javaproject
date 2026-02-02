# 🚀 Quick Start Guide - Library Management System

## Prerequisites
- Java 17+
- Maven 3.6+
- Node.js 18+
- npm

## Step 1: Make Scripts Executable
```bash
cd /Users/sanchitvijay/working/javv
chmod +x setup-dirs.sh
chmod +x start-all.sh
chmod +x stop-all.sh
chmod +x quick-start.sh
chmod +x test-api.sh
```

## Step 2: Setup Directories
```bash
./setup-dirs.sh
```

## Step 3: Start All Services
```bash
./start-all.sh
```

This will:
- ✅ Start Eureka Server on port 8761
- ✅ Start API Gateway on port 8080
- ✅ Start Catalog Service on port 8081
- ✅ Start Angular Frontend on port 4200

Wait about 2-3 minutes for everything to be ready.

## Step 4: Access the Application

### Frontend Application
```
http://localhost:4200
```

### Swagger API Documentation
```
http://localhost:8081/swagger-ui.html
```

### H2 Database Console
```
http://localhost:8081/h2-console
JDBC URL: jdbc:h2:mem:librarydb
Username: sa
Password: (leave empty)
```

### Eureka Dashboard
```
http://localhost:8761
```

## Step 5: Test the APIs
```bash
./test-api.sh
```

## How to Stop All Services
```bash
./stop-all.sh
```

---

## Testing Instructions

### Quick Manual Test via Frontend

1. **Open Browser**: http://localhost:4200

2. **View Dashboard**: See total members, active issues, available copies

3. **Add a Book**:
   - Click "Books" → "Add New Book"
   - Fill: Title, Author, ISBN, Category
   - Submit

4. **Add Book Copies**:
   - Click "Copies" next to the book
   - Add rack number (e.g., A1-001)
   - Submit

5. **Register a Member**:
   - Click "Members" → "Register New Member"
   - Fill: Name, Email, Phone
   - Submit

6. **Issue a Book**:
   - Click "Issues" → "Issue New Book"
   - Select member and book
   - Select available copy
   - Submit

7. **Return a Book**:
   - Click "Issues"
   - Click "Return" on an issued book
   - Check if fine is calculated

8. **View Reports**:
   - Click "Reports"
   - View inventory, issue, and fine reports

### Quick API Test via cURL

```bash
# Get all books
curl http://localhost:8080/api/admin/books

# Get dashboard data
curl http://localhost:8080/api/manager/reports/dashboard

# Create a book
curl -X POST http://localhost:8080/api/admin/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Test Book",
    "author": "Test Author",
    "isbn": "978-1234567890",
    "category": "Testing",
    "status": "ACTIVE"
  }'
```

---

## Sample Data Included

After startup, the system has:
- **5 Books** (Clean Code, Pragmatic Programmer, Design Patterns, etc.)
- **10 Book Copies** (various statuses)
- **4 Members** (3 active, 1 inactive)
- **4 Staff** (1 Admin, 2 Librarians, 1 Manager)
- **3 Book Issues** (1 active, 2 returned)

---

## Troubleshooting

### Ports Already in Use
```bash
# Check ports
lsof -i :8761
lsof -i :8080
lsof -i :8081
lsof -i :4200

# Kill processes
lsof -ti:8761 | xargs kill -9
lsof -ti:8080 | xargs kill -9
lsof -ti:8081 | xargs kill -9
lsof -ti:4200 | xargs kill -9
```

### View Logs
```bash
tail -f logs/eureka-server.log
tail -f logs/api-gateway.log
tail -f logs/catalog-service.log
tail -f logs/angular-frontend.log
```

### Clean Restart
```bash
./stop-all.sh
rm -rf logs/*.log pids/*.pid
./start-all.sh
```

---

## Features Implemented

### ✅ Admin Functions
- Create/Update/Delete Books
- Manage Book Copies
- Create/Manage Staff Accounts
- Search books by title/author/category

### ✅ Librarian Functions
- Register/Update Members
- Issue Books
- Return Books
- Calculate Fines ($5/day after 14 days)
- Update Copy Status (Damaged/Lost)
- Search Members

### ✅ Manager Functions
- Dashboard with KPIs
- Inventory Report
- Issue Report (with date range)
- Fine Report
- Monitor Overdue Books

---

## All Endpoints

### Books
- GET    /api/admin/books
- POST   /api/admin/books
- PUT    /api/admin/books/{id}
- DELETE /api/admin/books/{id}
- GET    /api/admin/books/search/title?title={title}
- GET    /api/admin/books/search/author?author={author}

### Book Copies
- GET    /api/admin/books/{bookId}/copies
- POST   /api/admin/books/{bookId}/copies
- PUT    /api/admin/books/copies/{id}
- DELETE /api/admin/books/copies/{id}

### Members
- GET    /api/staff/members
- POST   /api/staff/members
- PUT    /api/staff/members/{id}
- DELETE /api/staff/members/{id}

### Staff
- GET    /api/admin/staff
- POST   /api/admin/staff
- PUT    /api/admin/staff/{id}
- DELETE /api/admin/staff/{id}

### Issues
- GET    /api/staff/issues
- POST   /api/staff/issues
- PUT    /api/staff/issues/{id}/return
- GET    /api/staff/issues/overdue

### Reports
- GET    /api/manager/reports/dashboard
- GET    /api/manager/reports/inventory
- GET    /api/manager/reports/issues?startDate={date}&endDate={date}
- GET    /api/manager/reports/fines?startDate={date}&endDate={date}

---

## Success! 🎉

If everything is working, you should see:
- ✅ All services registered in Eureka
- ✅ Frontend loads without errors
- ✅ Sample data visible in UI
- ✅ All CRUD operations working
- ✅ Issue/Return functionality working
- ✅ Reports generating correctly

For detailed testing instructions, see: TESTING.md
