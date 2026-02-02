#!/bin/bash

# Library Management System - Test Script
# This script tests all API endpoints to verify the system is working

echo "=========================================="
echo "Library Management System - API Tests"
echo "=========================================="
echo ""

# Colors
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m'

API_GATEWAY="http://localhost:8080"
CATALOG_SERVICE="http://localhost:8081"

# Test counter
PASSED=0
FAILED=0

# Function to test an endpoint
test_endpoint() {
    local METHOD=$1
    local URL=$2
    local DESCRIPTION=$3
    local DATA=$4
    
    echo -n "Testing: $DESCRIPTION... "
    
    if [ -z "$DATA" ]; then
        RESPONSE=$(curl -s -w "\n%{http_code}" -X $METHOD "$URL" -H "Content-Type: application/json")
    else
        RESPONSE=$(curl -s -w "\n%{http_code}" -X $METHOD "$URL" -H "Content-Type: application/json" -d "$DATA")
    fi
    
    HTTP_CODE=$(echo "$RESPONSE" | tail -n1)
    
    if [ $HTTP_CODE -ge 200 ] && [ $HTTP_CODE -lt 300 ]; then
        echo -e "${GREEN}✓ PASSED (HTTP $HTTP_CODE)${NC}"
        ((PASSED++))
    else
        echo -e "${RED}✗ FAILED (HTTP $HTTP_CODE)${NC}"
        ((FAILED++))
    fi
}

echo "Waiting for services to be ready..."
sleep 5

echo ""
echo "=== Testing Eureka Server ==="
test_endpoint "GET" "http://localhost:8761" "Eureka Dashboard"

echo ""
echo "=== Testing Book Management (Admin) ==="
test_endpoint "GET" "$API_GATEWAY/api/admin/books" "Get all books"
test_endpoint "GET" "$API_GATEWAY/api/admin/books/1" "Get book by ID"
test_endpoint "GET" "$API_GATEWAY/api/admin/books/search/title?title=Clean" "Search books by title"
test_endpoint "GET" "$API_GATEWAY/api/admin/books/search/author?author=Martin" "Search books by author"

echo ""
echo "=== Testing Book Copy Management (Admin) ==="
test_endpoint "GET" "$API_GATEWAY/api/admin/books/1/copies" "Get copies for book 1"
test_endpoint "GET" "$API_GATEWAY/api/admin/books/copies" "Get all book copies"
test_endpoint "GET" "$API_GATEWAY/api/admin/books/copies/status/AVAILABLE" "Get available copies"

echo ""
echo "=== Testing Member Management (Staff) ==="
test_endpoint "GET" "$API_GATEWAY/api/staff/members" "Get all members"
test_endpoint "GET" "$API_GATEWAY/api/staff/members/1" "Get member by ID"
test_endpoint "GET" "$API_GATEWAY/api/staff/members/active" "Get active members"

echo ""
echo "=== Testing Staff Management (Admin) ==="
test_endpoint "GET" "$API_GATEWAY/api/admin/staff" "Get all staff"
test_endpoint "GET" "$API_GATEWAY/api/admin/staff/active" "Get active staff"

echo ""
echo "=== Testing Issue Management (Staff) ==="
test_endpoint "GET" "$API_GATEWAY/api/staff/issues" "Get all issues"
test_endpoint "GET" "$API_GATEWAY/api/staff/issues/active" "Get active issues"
test_endpoint "GET" "$API_GATEWAY/api/staff/issues/overdue" "Get overdue books"
test_endpoint "GET" "$API_GATEWAY/api/staff/issues/member/1" "Get issues for member 1"

echo ""
echo "=== Testing Reports (Manager) ==="
test_endpoint "GET" "$API_GATEWAY/api/manager/reports/dashboard" "Get dashboard data"
test_endpoint "GET" "$API_GATEWAY/api/manager/reports/inventory" "Get inventory report"
test_endpoint "GET" "$API_GATEWAY/api/manager/reports/issues?startDate=2026-01-01&endDate=2026-02-02" "Get issue report"
test_endpoint "GET" "$API_GATEWAY/api/manager/reports/fines?startDate=2026-01-01&endDate=2026-02-02" "Get fine report"

echo ""
echo "=== Testing Create Operations ==="

# Test creating a book
NEW_BOOK='{
  "title": "Test Book",
  "author": "Test Author",
  "isbn": "978-0000000000",
  "category": "Test Category",
  "status": "ACTIVE"
}'
test_endpoint "POST" "$API_GATEWAY/api/admin/books" "Create new book" "$NEW_BOOK"

# Test creating a member
NEW_MEMBER='{
  "name": "Test Member",
  "email": "test@example.com",
  "phone": "9999999999",
  "membershipStatus": "ACTIVE"
}'
test_endpoint "POST" "$API_GATEWAY/api/staff/members" "Create new member" "$NEW_MEMBER"

echo ""
echo "=== Direct Service Tests ==="
test_endpoint "GET" "$CATALOG_SERVICE/swagger-ui.html" "Swagger UI"
test_endpoint "GET" "$CATALOG_SERVICE/h2-console" "H2 Console"

echo ""
echo "=========================================="
echo "Test Results Summary"
echo "=========================================="
echo -e "${GREEN}Passed: $PASSED${NC}"
echo -e "${RED}Failed: $FAILED${NC}"
echo "Total:  $((PASSED + FAILED))"
echo ""

if [ $FAILED -eq 0 ]; then
    echo -e "${GREEN}All tests passed! ✓${NC}"
    exit 0
else
    echo -e "${RED}Some tests failed. Please check the logs.${NC}"
    exit 1
fi
