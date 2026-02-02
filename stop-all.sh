#!/bin/bash

# Library Management System - Stop All Services

echo "=========================================="
echo "Stopping Library Management System"
echo "=========================================="
echo ""

# Colors
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m'

# Stop services using saved PIDs
if [ -d "pids" ]; then
    echo "Stopping services from PID files..."
    
    if [ -f "pids/angular-frontend.pid" ]; then
        PID=$(cat pids/angular-frontend.pid)
        echo -e "${YELLOW}Stopping Angular Frontend (PID: $PID)...${NC}"
        kill $PID 2>/dev/null && echo -e "${GREEN}✓ Stopped${NC}" || echo -e "${RED}✗ Not running${NC}"
    fi
    
    if [ -f "pids/catalog-service.pid" ]; then
        PID=$(cat pids/catalog-service.pid)
        echo -e "${YELLOW}Stopping Catalog Service (PID: $PID)...${NC}"
        kill $PID 2>/dev/null && echo -e "${GREEN}✓ Stopped${NC}" || echo -e "${RED}✗ Not running${NC}"
    fi
    
    if [ -f "pids/api-gateway.pid" ]; then
        PID=$(cat pids/api-gateway.pid)
        echo -e "${YELLOW}Stopping API Gateway (PID: $PID)...${NC}"
        kill $PID 2>/dev/null && echo -e "${GREEN}✓ Stopped${NC}" || echo -e "${RED}✗ Not running${NC}"
    fi
    
    if [ -f "pids/eureka-server.pid" ]; then
        PID=$(cat pids/eureka-server.pid)
        echo -e "${YELLOW}Stopping Eureka Server (PID: $PID)...${NC}"
        kill $PID 2>/dev/null && echo -e "${GREEN}✓ Stopped${NC}" || echo -e "${RED}✗ Not running${NC}"
    fi
    
    # Clean up PID files
    rm -rf pids/*.pid
fi

# Force kill processes on specific ports (backup method)
echo ""
echo "Checking and cleaning up ports..."

kill_port() {
    PORT=$1
    SERVICE=$2
    PID=$(lsof -ti:$PORT)
    if [ ! -z "$PID" ]; then
        echo -e "${YELLOW}Killing process on port $PORT ($SERVICE)...${NC}"
        kill -9 $PID
        echo -e "${GREEN}✓ Cleaned port $PORT${NC}"
    fi
}

kill_port 4200 "Angular"
kill_port 8081 "Catalog Service"
kill_port 8080 "API Gateway"
kill_port 8761 "Eureka Server"

echo ""
echo -e "${GREEN}All services stopped!${NC}"
echo "=========================================="
