# Library Management System

A comprehensive Library Management System built with Spring Boot microservices architecture and Angular frontend.

## Architecture

- **Eureka Server** (Port 8761): Service discovery and registration
- **API Gateway** (Port 8080): Routes requests to microservices
- **Catalog Service** (Port 8081): Core business logic for library operations
- **Angular Frontend** (Port 4200): User interface

## Tech Stack

### Backend
- Spring Boot 3.2.0
- Maven
- H2 Database (In-memory)
- Spring Cloud (Eureka, Gateway)
- OpenFeign
- Swagger/OpenAPI
- Lombok

### Frontend
- Angular 17
- TypeScript
- RxJS
- Angular Forms

## Features

### Admin Functions
- Add, update, delete books
- Manage book copies and inventory
- Create and manage staff accounts
- View all library operations

### Librarian Functions
- Register new members
- Issue books to members
- Return books and calculate fines
- Update book copy status (damaged, lost)
- Search books and members

### Manager Functions
- View dashboard with key metrics
- Generate inventory reports
- Generate issue/return reports
- Generate fine reports
- Monitor overdue books

## Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Node.js 18+ and npm
- Git

### Backend Setup

1. **Start Eureka Server**
   ```bash
   cd eureka-server
   mvn clean install
   mvn spring-boot:run
   ```
   Access at: http://localhost:8761

2. **Start API Gateway**
   ```bash
   cd api-gateway
   mvn clean install
   mvn spring-boot:run
   ```

3. **Start Catalog Service**
   ```bash
   cd catalog-service
   mvn clean install
   mvn spring-boot:run
   ```

### Frontend Setup

1. **Install dependencies**
   ```bash
   cd library-frontend
   npm install
   ```

2. **Start Angular development server**
   ```bash
   npm start
   ```
   Access at: http://localhost:4200

## API Documentation

Once the Catalog Service is running, access Swagger UI at:
http://localhost:8081/swagger-ui.html

### Key API Endpoints

#### Admin - Book Management
- `GET /api/admin/books` - Get all books
- `POST /api/admin/books` - Create a new book
- `PUT /api/admin/books/{id}` - Update a book
- `DELETE /api/admin/books/{id}` - Delete a book
- `GET /api/admin/books/search/title?title={title}` - Search by title
- `GET /api/admin/books/search/author?author={author}` - Search by author

#### Admin - Book Copy Management
- `GET /api/admin/books/{bookId}/copies` - Get all copies of a book
- `POST /api/admin/books/{bookId}/copies` - Add a new copy
- `PUT /api/admin/books/copies/{id}` - Update a copy
- `DELETE /api/admin/books/copies/{id}` - Delete a copy

#### Admin - Staff Management
- `GET /api/admin/staff` - Get all staff
- `POST /api/admin/staff` - Create staff account
- `PUT /api/admin/staff/{id}` - Update staff
- `DELETE /api/admin/staff/{id}` - Delete staff

#### Staff - Member Management
- `GET /api/staff/members` - Get all members
- `POST /api/staff/members` - Register a member
- `PUT /api/staff/members/{id}` - Update member
- `DELETE /api/staff/members/{id}` - Delete member

#### Staff - Issue/Return
- `GET /api/staff/issues` - Get all issues
- `POST /api/staff/issues` - Issue a book
- `PUT /api/staff/issues/{id}/return` - Return a book
- `GET /api/staff/issues/overdue` - Get overdue books
- `GET /api/staff/issues/{id}/fine` - Calculate fine

#### Manager - Reports
- `GET /api/manager/reports/dashboard` - Dashboard data
- `GET /api/manager/reports/inventory` - Inventory report
- `GET /api/manager/reports/issues?startDate={date}&endDate={date}` - Issue report
- `GET /api/manager/reports/fines?startDate={date}&endDate={date}` - Fine report

## H2 Database Console

Access H2 console at: http://localhost:8081/h2-console

**Connection Details:**
- JDBC URL: `jdbc:h2:mem:librarydb`
- Username: `sa`
- Password: (leave empty)

## Sample Data

The application comes pre-loaded with sample data:
- 5 Books
- 10 Book Copies
- 4 Members
- 4 Staff Members
- 3 Book Issues

## Business Rules

### Fine Calculation
- Loan period: 14 days
- Fine: $5 per day for overdue books
- Fine is calculated automatically on return

### Book Issue Rules
- Member must be ACTIVE
- Book copy must be AVAILABLE
- Cannot delete books with issued copies
- Cannot delete issued book copies

### Status Enums

**Book Copy Status:**
- AVAILABLE
- ISSUED
- DAMAGED
- LOST

**Member Status:**
- ACTIVE
- INACTIVE

**Staff Roles:**
- ADMIN
- LIBRARIAN
- MANAGER

**Issue Status:**
- ISSUED
- RETURNED
- OVERDUE

## Project Structure

```
javv/
├── eureka-server/          # Service Discovery
├── api-gateway/            # API Gateway
├── catalog-service/        # Main Business Logic
│   ├── entity/            # JPA Entities
│   ├── repository/        # Data Access Layer
│   ├── service/           # Business Logic
│   ├── controller/        # REST Controllers
│   └── config/            # Configuration
└── library-frontend/       # Angular UI
    ├── components/        # UI Components
    ├── services/          # HTTP Services
    └── app-routing.module.ts
```

## Notes

- No authentication/JWT is implemented as per requirements
- CORS is enabled for local development
- H2 database data is reset on application restart
- All dates use ISO format (YYYY-MM-DD)

## Troubleshooting

### Port Already in Use
If you get port conflicts, you can modify the ports in `application.yml` files:
- Eureka Server: `server.port` in eureka-server/src/main/resources/application.yml
- API Gateway: `server.port` in api-gateway/src/main/resources/application.yml
- Catalog Service: `server.port` in catalog-service/src/main/resources/application.yml

### Services Not Registering with Eureka
1. Ensure Eureka Server is running first
2. Wait 30-60 seconds for services to register
3. Check Eureka dashboard at http://localhost:8761

### Frontend API Connection Issues
1. Ensure all backend services are running
2. Check CORS configuration in controllers
3. Verify API Gateway is routing correctly

## License

This project is created for educational purposes.
