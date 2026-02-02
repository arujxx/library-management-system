# Library Management System

## Description
Console-based Java application for managing a library.

## Roles
- ADMIN – manage books and categories
- USER – borrow and return books

## Security
Role-based access control implemented using Role enum and AccessChecker.

## Validation
- Empty field validation
- Email format validation
- Book availability validation

## Architecture
- Repository pattern
- Service layer
- Singleton (DatabaseConnection)
- Factory (UserFactory)
- SQL JOINs
