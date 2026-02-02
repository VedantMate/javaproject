#!/bin/bash

# Library Management System - Start All Services
# This script starts all microservices and the frontend

echo "=========================================="
echo "Library Management System - Startup"
echo "=========================================="
echo ""

# Get the absolute path of the script directory
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# Colors for output
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Create necessary directories
mkdir -p "$SCRIPT_DIR/logs"
mkdir -p "$SCRIPT_DIR/pids"

# Function to check if a port is in use
check_port() {
    if lsof -Pi :$1 -sTCP:LISTEN -t >/dev/null 2>&1 ; then
        echo -e "${RED}Error: Port $1 is already in use!${NC}"
        echo "Please stop the process using: lsof -ti:$1 | xargs kill -9"
        return 1
    fi
    return 0
}

# Check if all required ports are available
echo "Checking if ports are available..."
check_port 8761 || exit 1
check_port 8080 || exit 1
check_port 8081 || exit 1
check_port 4200 || exit 1

echo -e "${GREEN}All ports are available!${NC}"
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo -e "${RED}Maven is not installed. Please install Maven first.${NC}"
    exit 1
fi

# Check if Node.js is installed
if ! command -v node &> /dev/null; then
    echo -e "${RED}Node.js is not installed. Please install Node.js first.${NC}"
    exit 1
fi

echo -e "${YELLOW}Step 1/4: Starting Eureka Server (Port 8761)...${NC}"
cd "$SCRIPT_DIR/eureka-server"
mvn clean install -DskipTests > /dev/null 2>&1
echo "Building Eureka Server..."
mvn spring-boot:run > "$SCRIPT_DIR/logs/eureka-server.log" 2>&1 &
EUREKA_PID=$!
echo -e "${GREEN}Eureka Server started with PID: $EUREKA_PID${NC}"
echo $EUREKA_PID > "$SCRIPT_DIR/pids/eureka-server.pid"

# Wait for Eureka to be ready
echo "Waiting for Eureka Server to be ready (30 seconds)..."
sleep 30

echo ""
echo -e "${YELLOW}Step 2/4: Starting API Gateway (Port 8080)...${NC}"
cd api-gateway
mvn"$SCRIPT_DIR/api-gateway"
mvn clean install -DskipTests > /dev/null 2>&1
echo "Building API Gateway..."
mvn spring-boot:run > "$SCRIPT_DIR/logs/api-gateway.log" 2>&1 &
GATEWAY_PID=$!
echo -e "${GREEN}API Gateway started with PID: $GATEWAY_PID${NC}"
echo $GATEWAY_PID > "$SCRIPT_DIR/pids/api-gateway.pid"
# Wait for API Gateway to be ready
echo "Waiting for API Gateway to register (15 seconds)..."
sleep 15

echo ""
echo -e "${YELLOW}Step 3/4: Starting Catalog Service (Port 8081)...${NC}"
cd catalog-service
mvn"$SCRIPT_DIR/catalog-service"
mvn clean install -DskipTests > /dev/null 2>&1
echo "Building Catalog Service..."
mvn spring-boot:run > "$SCRIPT_DIR/logs/catalog-service.log" 2>&1 &
CATALOG_PID=$!
echo -e "${GREEN}Catalog Service started with PID: $CATALOG_PID${NC}"
echo $CATALOG_PID > "$SCRIPT_DIR/pids/catalog-service.pid"
# Wait for Catalog Service to be ready
echo "Waiting for Catalog Service to register (20 seconds)..."
sleep 20

echo ""
echo -e "${YELLOW}Step 4/4: Starting Angular Frontend (Port 4200)...${NC}"
cd library-frontend
"$SCRIPT_DIR/library-frontend"

# Install npm dependencies if node_modules doesn't exist
if [ ! -d "node_modules" ]; then
    echo "Installing npm dependencies..."
    npm install > /dev/null 2>&1
fi

npm start > "$SCRIPT_DIR/logs/angular-frontend.log" 2>&1 &
FRONTEND_PID=$!
echo -e "${GREEN}Angular Frontend started with PID: $FRONTEND_PID${NC}"
echo $FRONTEND_PID > "$SCRIPT_DIR/pids/angular-frontend.pid"
echo ""
echo "=========================================="
echo -e "${GREEN}All services started successfully!${NC}"
echo "=========================================="
echo ""
echo "Service URLs:"
echo "  • Eureka Dashboard:    http://localhost:8761"
echo "  • API Gateway:         http://localhost:8080"
echo "  • Catalog Service:     http://localhost:8081"
echo "  • Swagger UI:          http://localhost:8081/swagger-ui.html"
echo "  • H2 Console:          http://localhost:8081/h2-console"
echo "  • Frontend App:        http://localhost:4200"
echo ""
echo "PIDs saved in 'pids' directory"
echo "Logs saved in 'logs' directory"
echo ""
echo "To stop all services, run: ./stop-all.sh"
echo ""
echo "Waiting for Angular to compile (20 seconds)..."
sleep 20
echo ""
echo -e "${GREEN}System is ready! Open http://localhost:4200 in your browser${NC}"
