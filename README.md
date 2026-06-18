# Furniture ERP Backend

A production-ready Furniture ERP Backend built using Spring Boot, Spring Security (JWT), Hibernate/JPA, and PostgreSQL.

The system manages furniture manufacturing operations including worker management, production tracking, payments, inventory, shipments, showroom dispatches, and business reports.

## Live Application

Frontend: https://furniture-erp-frontend.vercel.app

Backend API: https://furniture-erp-backend.onrender.com

## Features

### Authentication & Security

* JWT Authentication
* Role-Based Access Control (Admin / Viewer)
* Secure Password Encryption using BCrypt
* Spring Security Integration

### Worker Management

* Add and Manage Workers
* Carpenter & Polisher Support
* Worker Earnings Tracking
* Worker Statements & Summaries

### Production Management

* Work Entry Management
* Quantity Tracking
* Rate Type Management
* Production Reports
* Ready Stock Management

### Payment Management

* Worker Payments
* Payment History
* Outstanding Balance Tracking
* Payment Summary Reports

### Inventory & Product Management

* Category Management
* Design Management
* Rate Type Management
* Product Rate Configuration

### Shipment Management

* Showroom Management
* Shipment Creation
* Shipment History
* Showroom Shipment Reports

### Dashboard & Reporting

* Dashboard Summary
* Production Reports
* Worker Statements
* Worker Summary Reports
* Payment Summary Reports
* Shipment Reports

## Tech Stack

### Backend

* Java 21
* Spring Boot
* Spring Security
* JWT Authentication
* Hibernate / JPA
* Maven

### Database

* PostgreSQL
* Neon Cloud Database

### Deployment

* Render (Backend Hosting)
* GitHub

## Project Structure

src/main/java

├── controller

├── service

├── repository

├── entity

├── dto

├── config

├── security

└── enums

## API Modules

* Authentication
* Workers
* Categories
* Designs
* Rate Types
* Product Rates
* Work Entries
* Payments
* Ready Stock
* Showrooms
* Shipments
* Dashboard
* Reports

## Database

Cloud PostgreSQL database hosted on Neon.

## Future Enhancements

* Excel Export
* PDF Reports
* Audit Logs
* Advanced Analytics Dashboard
* Mobile Optimizations

## Author

Kuldeep Singh S
BE Information Science & Engineering
Java Full Stack Developer

Java Full Stack Developer
