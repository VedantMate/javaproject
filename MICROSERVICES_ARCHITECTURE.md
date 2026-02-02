# Library Management System - Microservices Architecture

## Architecture Overview

This library management system has been reorganized into **3 distinct microservices** with proper separation of concerns:

### 1. **Catalog Service** (Port: 8081)
**Responsibility**: Manages the core catalog data
- Books management (CRUD operations)
- Book copies management
- Member registration and management
- Staff/Admin management

**Key Features**:
- Book inventory management
- Member profiles
- Staff administration
- Exposes internal APIs for other services via Feign clients

### 2. **Circulation Service** (Port: 8083)
**Responsibility**: Handles book circulation and transactions
- Book issue/checkout operations
- Book return operations
- Fine calculation
- Overdue tracking
- Issue history

**Key Features**:
- Automated fine calculation (₹5 per day)
- 14-day loan period
- Communicates with Catalog Service via Feign to:
  - Validate member status
  - Check book copy availability
  - Update book copy status (AVAILABLE ↔ ISSUED)

### 3. **Reporting Service** (Port: 8084)
**Responsibility**: Provides analytics and reports for administrators
- Inventory reports (books, copies by status)
- Circulation reports (issues by date range)
- Fine reports (total fines, overdue books)
- Dashboard metrics

**Key Features**:
- Aggregates data from Catalog and Circulation services
- No database - stateless reporting layer
- Real-time analytics via Feign client calls

---

## Service Communication Architecture

```
┌─────────────────────┐
│  Reporting Service  │ (Port 8084)
│   (Analytics)       │
└──────────┬──────────┘
           │ Feign Client
           ├───────────────────┐
           │                   │
           ▼                   ▼
┌──────────────────┐  ┌────────────────────┐
│ Catalog Service  │  │ Circulation Service│
│  (Books, Members)│  │ (Issue/Return)     │
│   Port: 8081     │◄─┤   Port: 8083       │
└──────────────────┘  └────────────────────┘
                           Feign Client
```

### Inter-Service Communication (Feign Clients)

#### Circulation → Catalog
- `GET /api/members/{id}` - Validate member for book issue
- `GET /api/book-copies/{id}` - Check book copy availability
- `PUT /api/book-copies/{id}/update-status` - Update copy status

#### Reporting → Catalog
- `GET /api/books/count` - Total books count
- `GET /api/book-copies/count` - Total copies count
- `GET /api/book-copies/count-by-status` - Copies by status
- `GET /api/members/count` - Total members count

#### Reporting → Circulation
- `GET /api/circulation/issues/active` - Active issues
- `GET /api/circulation/issues/overdue` - Overdue books
- `GET /api/circulation/total-fines` - Total fines

---

## Technology Stack

- **Framework**: Spring Boot 3.2.0
- **Java Version**: 17
- **Service Discovery**: Netflix Eureka
- **Inter-Service Communication**: Spring Cloud OpenFeign
- **Database**: H2 (in-memory) for Catalog & Circulation
- **API Documentation**: Swagger/OpenAPI 3
- **Frontend**: Angular 17

---

## Service Ports

| Service | Port | Swagger URL |
|---------|------|-------------|
| Eureka Server | 8761 | http://localhost:8761 |
| API Gateway | 8080 | - |
| Catalog Service | 8081 | http://localhost:8081/swagger-ui.html |
| Circulation Service | 8083 | http://localhost:8083/swagger-ui.html |
| Reporting Service | 8084 | http://localhost:8084/swagger-ui.html |
| Angular Frontend | 4200 | http://localhost:4200 |

---

## Quick Start

### Prerequisites
- Java 17+
- Maven 3.6+
- Node.js 18+
- npm

### Start All Services
```bash
chmod +x start-all.sh
./start-all.sh
```

This will start services in order:
1. Eureka Server (30s wait)
2. API Gateway (15s wait)
3. Catalog Service (20s wait)
4. Circulation Service (20s wait)
5. Reporting Service (20s wait)
6. Angular Frontend (20s wait)

### Stop All Services
```bash
chmod +x stop-all.sh
./stop-all.sh
```

---

## API Documentation

### Catalog Service APIs

#### Books Management
- `GET /api/admin/books` - Get all books
- `POST /api/admin/books` - Create new book
- `PUT /api/admin/books/{id}` - Update book
- `DELETE /api/admin/books/{id}` - Delete book
- `GET /api/admin/books/search` - Search books

#### Book Copies Management
- `GET /api/admin/books/copies` - Get all copies
- `POST /api/admin/books/{bookId}/copies` - Add new copy
- `PUT /api/admin/books/copies/{id}` - Update copy
- `DELETE /api/admin/books/copies/{id}` - Delete copy

#### Members Management
- `GET /api/staff/members` - Get all members
- `POST /api/staff/members` - Register new member
- `PUT /api/staff/members/{id}` - Update member
- `DELETE /api/staff/members/{id}` - Delete member

#### Internal APIs (for Feign)
- `GET /api/books/{id}` - Get book by ID
- `GET /api/book-copies/{id}` - Get book copy by ID
- `GET /api/members/{id}` - Get member by ID
- `PUT /api/book-copies/{id}/update-status` - Update copy status

### Circulation Service APIs

#### Issue/Return Operations
- `POST /api/circulation/issue` - Issue a book
  ```json
  {
    "memberId": 1,
    "bookCopyId": 1
  }
  ```
- `PUT /api/circulation/return/{id}` - Return a book
- `GET /api/circulation/issues` - Get all issues
- `GET /api/circulation/issues/active` - Get active issues
- `GET /api/circulation/issues/overdue` - Get overdue books
- `GET /api/circulation/issues/member/{memberId}` - Get member's issues

#### Fine Calculation
- `GET /api/circulation/fine/{issueId}` - Calculate fine for an issue
- `GET /api/circulation/total-fines?startDate={date}&endDate={date}` - Total fines

### Reporting Service APIs

#### Reports
- `GET /api/reports/inventory` - Inventory report
  - Total books, total copies, copies by status
- `GET /api/reports/issues?startDate={date}&endDate={date}` - Issue report
- `GET /api/reports/fines?startDate={date}&endDate={date}` - Fine report
- `GET /api/reports/dashboard` - Dashboard metrics
- `GET /api/reports/comprehensive` - Full comprehensive report

---

## Database Schema

### Catalog Service Database

#### Books Table
- id (PK)
- title
- author
- isbn (unique)
- category
- status (ACTIVE/INACTIVE)

#### Book Copies Table
- id (PK)
- book_id (FK)
- copy_number
- rack_no
- status (AVAILABLE/ISSUED/DAMAGED/LOST)

#### Members Table
- id (PK)
- name
- email (unique)
- phone
- membership_date
- membership_status (ACTIVE/SUSPENDED/EXPIRED)

#### Staff Table
- id (PK)
- name
- email (unique)
- role (LIBRARIAN/MANAGER/ADMIN)
- status (ACTIVE/INACTIVE)

### Circulation Service Database

#### Book Issues Table
- id (PK)
- member_id
- book_copy_id
- book_id
- issue_date
- due_date
- return_date
- fine
- status (ISSUED/RETURNED/OVERDUE)

---

## Business Rules

### Book Issue
1. Member must be ACTIVE
2. Book copy must be AVAILABLE
3. Loan period: 14 days
4. Copy status changes: AVAILABLE → ISSUED

### Book Return
1. Calculate fine if overdue (₹5 per day)
2. Update return date
3. Copy status changes: ISSUED → AVAILABLE
4. Status changes: ISSUED → RETURNED

### Fine Calculation
- Fine per day: ₹5
- Calculated from: Due Date + 1 day
- Only for returned books or overdue active issues

---

## Development

### Build Individual Service
```bash
cd catalog-service
mvn clean install
mvn spring-boot:run
```

### View Logs
```bash
tail -f logs/catalog-service.log
tail -f logs/circulation-service.log
tail -f logs/reporting-service.log
```

### Access H2 Console
- Catalog Service: http://localhost:8081/h2-console
  - JDBC URL: `jdbc:h2:mem:librarydb`
  - Username: `sa`
  - Password: (empty)

- Circulation Service: http://localhost:8083/h2-console
  - JDBC URL: `jdbc:h2:mem:circulationdb`
  - Username: `sa`
  - Password: (empty)

---

## Benefits of This Architecture

### ✅ Separation of Concerns
- Each service has a single, well-defined responsibility
- Easier to understand and maintain

### ✅ Scalability
- Services can be scaled independently
- Circulation service can handle high transaction volume
- Reporting service can scale for analytics load

### ✅ Independent Development
- Teams can work on different services simultaneously
- Different deployment cycles

### ✅ Fault Isolation
- If reporting fails, circulation still works
- Service failures don't cascade

### ✅ Technology Flexibility
- Each service can use different databases/technologies
- Currently: H2 for Catalog & Circulation, stateless for Reporting

---

## Future Enhancements

1. **Authentication & Authorization**
   - Add Spring Security
   - JWT token-based authentication
   - Role-based access control

2. **Database Migration**
   - Replace H2 with PostgreSQL/MySQL for production
   - Separate databases per service

3. **Message Queue**
   - Add RabbitMQ/Kafka for async communication
   - Event-driven architecture for notifications

4. **Caching**
   - Redis for frequently accessed data
   - Improve reporting performance

5. **Monitoring**
   - Spring Boot Actuator
   - Prometheus + Grafana
   - Distributed tracing with Zipkin

---

## Troubleshooting

### Port Already in Use
```bash
# Find and kill process on port
lsof -ti:8081 | xargs kill -9
```

### Service Not Registering with Eureka
- Check Eureka is running: http://localhost:8761
- Wait 30-60 seconds for registration
- Check service logs for connection errors

### Feign Client Errors
- Ensure target service is registered in Eureka
- Verify service name in `@FeignClient` matches Eureka registration
- Check network connectivity between services

---

## Contributors

Library Management Team

## License

MIT License
