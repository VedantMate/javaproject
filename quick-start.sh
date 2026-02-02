#!/bin/bash

# Library Management System - Quick Start (Development Mode)
# This script runs services in the current terminal for easier monitoring

echo "=========================================="
echo "Library Management System - Quick Start"
echo "=========================================="
echo ""

# Create necessary directories
mkdir -p logs
mkdir -p pids

# Start Eureka Server in new terminal
echo "Starting Eureka Server..."
osascript -e 'tell app "Terminal" to do script "cd '$(pwd)'/eureka-server && mvn spring-boot:run"'

sleep 25

# Start API Gateway in new terminal
echo "Starting API Gateway..."
osascript -e 'tell app "Terminal" to do script "cd '$(pwd)'/api-gateway && mvn spring-boot:run"'

sleep 15

# Start Catalog Service in new terminal
echo "Starting Catalog Service..."
osascript -e 'tell app "Terminal" to do script "cd '$(pwd)'/catalog-service && mvn spring-boot:run"'

sleep 20

# Start Angular in new terminal
echo "Starting Angular Frontend..."
osascript -e 'tell app "Terminal" to do script "cd '$(pwd)'/library-frontend && npm start"'

echo ""
echo "All services are starting in separate terminal windows..."
echo "Wait about 1-2 minutes for everything to be ready."
echo ""
echo "Access the application at: http://localhost:4200"
echo ""
echo "To stop all services, close all terminal windows or run: ./stop-all.sh"
