# Kuat Akberen Student API

**Student:** Kuat Akberen | **Status:** Tested and Documented
**Repository:** kuat-akberen-student-api
**Docker image:** kuat-akberen-student-api:v1.0-kuat-akberen
**Jenkins job:** Kuat_Akberen_Student_API

## Description

A Spring Boot CRUD REST API for managing students, containerized with Docker and PostgreSQL
and built by a Jenkins pipeline. Terminal output for every part is in [docs/evidence](docs/evidence).

## API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | / or /api/info | Student name, project and version |
| GET | /api/students | Retrieve all students |
| GET | /api/students/{id} | Retrieve student by ID (404 JSON if missing) |
| POST | /api/students | Create a student (400 if invalid, 409 if email is taken) |
| PUT | /api/students/{id} | Update a student |
| DELETE | /api/students/{id} | Delete a student |
| GET | /api/students/count | Get total student count |

## CRUD change made by Kuat Akberen

Create and update now validate the request body with `@Valid`, and all errors come back as JSON
from one `@RestControllerAdvice`: 400 for invalid data, 404 for a missing student, 409 for a duplicate email.

## Running with Docker Compose

```bash
cp .env.example .env        # then set your own POSTGRES_PASSWORD
docker compose up -d --build
curl http://localhost:8080/api/info
```

The application reaches the database at `db:5432`. Inside a container `localhost` is that container
itself, while Docker's DNS on the shared network resolves the service name `db` to the PostgreSQL container.

## Running Tests

```bash
./gradlew test
```

Tests use an in-memory H2 database, so they do not need PostgreSQL.

## Explanations

**`>` and `>>`:** `>` truncates the file and writes from the start; `>>` appends to the end and keeps the old content.

**Why `.env` files and passwords are not committed:** Git keeps every version forever, so a pushed secret stays
in the history even after it is deleted, and anyone with repository access can read it. A leaked password must be
treated as compromised and rotated. Only `.env.example`, without real values, is committed.

**Continuous Integration and Continuous Deployment:** CI builds and tests every commit automatically so problems
are found early. Continuous Deployment goes further and automatically releases every commit that passes to production.
This project implements CI only: the pipeline ends at a verified Docker image and deploys nothing.

## Technologies

- Java 17 / Spring Boot 3.2
- PostgreSQL 15
- Docker & Docker Compose
- Gradle 8.5 (wrapper)
- Jenkins CI/CD
