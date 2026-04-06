# Finance Dashboard Backend

## Tech Stack
- Spring Boot
- Spring Security (JWT)
- MySQL
- JPA

## Features
- JWT Authentication
- Role-Based Access (Admin, Analyst, Viewer)
- Financial Records CRUD
- Pagination & Filtering
- Dashboard APIs

## APIs
- /api/auth/login
- /api/auth/register
- /api/records
- /api/dashboard/summary
- /api/dashboard/category-summary

## Roles
- ADMIN → Full access
- ANALYST → Read + insights
- VIEWER → Only dashboard view
