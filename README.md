# LifeLink – Blood Donation Management System

**Student Name:** IRASUBIZA Mariam  
**Student ID:** 26721  
**Course Project:** INSY 8322 – Web Technology and Internet  
**Project Name:** LifeLink

## 1. Project Description

LifeLink is a Blood Donation Management System developed using Java Spring Boot and PostgreSQL.

The system helps manage blood donors, recipients, donation types, donation slots, blood requests, and donation records.

## 2. Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- Postman
- Visual Studio Code

## 3. Main Features

- Manage donors, recipients, donation types, and donation slots.
- Create, view, update, and delete blood requests.
- Search recipients by blood group.
- Search blood requests by status and date range.
- Find donors by donation type.
- Find available donation slots and unused donation types.
- Manage blood request status transitions.
- Prevent invalid or duplicate blood requests.
- Assign donation types and donation slots to donors.
- Save and retrieve donation records.
- View blood request statistics by status.
- Cancel a donor's requests for a specific date, excluding donated requests.

## 4. Project Structure

The application uses a layered structure:

- `entity` – JPA entity classes.
- `repository` – database operations and queries.
- `service` – application business logic.
- `controller` – REST API endpoints.
- `enums` – enumeration classes.

## 5. Database Configuration

The application uses PostgreSQL.

Create a database named:

`lifelink_db`

Configure your PostgreSQL username and password in `src/main/resources/application.properties`.

Do not upload real database passwords or other secrets to GitHub.

## 6. How to Run the Project

1. Install Java and PostgreSQL.
2. Open the project in Visual Studio Code.
3. Make sure PostgreSQL is running.
4. Create the `lifelink_db` database.
5. Configure the database connection in `application.properties`.
6. Open the terminal inside the `lifelink` Spring Boot project folder.
7. Run:

```bash
.\mvnw.cmd spring-boot:run
```

The application runs at:

`http://localhost:8080`

## 7. API Testing

The REST API endpoints can be tested using Postman.

Base URL:

`http://localhost:8080/api`

## 8. Repository

GitHub: https://github.com/irasubizamariam/life-link-backend