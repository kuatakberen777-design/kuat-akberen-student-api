# Kuat Akberen Student API

**Student:** Kuat Akberen | **Status:** Production Ready
**Repository:** kuat-akberen-student-api
**Version:** v1.0-kuat-akberen

## Description

A Spring Boot CRUD REST API for managing students, containerized with Docker and PostgreSQL.
This project includes full documentation and deployment configuration.

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | /api/students | Retrieve all students |
| GET | /api/students/{id} | Retrieve student by ID |
| POST | /api/students | Create a new student |
| PUT | /api/students/{id} | Update student information |
| DELETE | /api/students/{id} | Delete a student |
| GET | /api/students/count | Get total student count |

## Architecture

- **Backend:** Spring Boot 3.2 (Java 17)
- **Database:** PostgreSQL 15
- **Containerization:** Docker & Docker Compose
- **CI/CD:** Jenkins Pipeline

## Running with Docker Compose

```bash
docker-compose up --build
```

## Running Tests

```bash
./gradlew test
```

## Technologies

- Java 17 / Spring Boot 3.2
- PostgreSQL 15
- Docker & Docker Compose
- Gradle
- Jenkins CI/CD
