# Spring Reactive Notes

[![GitHub](https://img.shields.io/badge/GitHub-Repo-blue)](https://github.com/imamfahrudin/spring-reactive-notes)
[![Java](https://img.shields.io/badge/Java-21-orange)](https://openjdk.java.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.9.0-blue)](https://maven.apache.org/)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue)](https://www.docker.com/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A demo project for Spring Boot Reactive CRUD operations using WebFlux, R2DBC, and H2 database.

## Features

- Fully reactive architecture with Spring WebFlux
- Non-blocking database operations with R2DBC and H2
- RESTful API for note management
- OpenAPI/Swagger documentation
- Comprehensive testing strategy (unit tests, integration tests)
- Docker containerization

## Technologies

- **Java 21**
- **Spring Boot 3.2**
- **Spring WebFlux** for reactive web
- **R2DBC** for reactive database access
- **H2 Database** (in-memory)
- **JUnit 5** for unit and integration testing
- **Mockito** for mocking in unit tests
- **Hamcrest** and **AssertJ** for expressive test assertions
- **Reactor Test** for reactive stream testing
- **WebTestClient** for reactive integration testing
- **Lombok** for reducing boilerplate code
- **Maven** for build management
- **Docker** for containerization

## API Endpoints

- `GET /notes` - List all notes
- `GET /notes/{id}` - Get a single note by ID
- `POST /notes` - Create a new note
- `PUT /notes/{id}` - Update an existing note
- `DELETE /notes/{id}` - Delete a note by ID

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6+
- Docker (optional, for containerized run)

### Running Locally

1. Clone the repository:
   ```bash
   git clone https://github.com/imamfahrudin/spring-reactive-notes.git
   ```
2. Navigate to the project directory:
   ```bash
   cd spring-reactive-notes
   ```
3. Run with Maven:
   ```bash
   mvn spring-boot:run
   ```
4. Access the application at http://localhost:8080
5. H2 Console: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:testdb`)
6. Swagger UI: http://localhost:8080/swagger-ui.html

### Running with Docker

1. Build and run with Docker Compose:
   ```bash
   docker-compose up --build
   ```
2. Access the application at http://localhost:8080

### Building

```bash
mvn clean package
```

### Testing

Run the test suite with Maven:

```bash
mvn test
```

The project includes comprehensive testing across all layers:

- **Unit Tests**: Service layer testing with mocked dependencies using Mockito and StepVerifier
- **Integration Tests**: Repository and Controller layer testing with real database and WebTestClient

Testing technologies:
- **JUnit 5** for test framework
- **Mockito** for mocking dependencies in unit tests
- **Hamcrest** and **AssertJ** for readable assertions
- **StepVerifier** for testing reactive streams
- **WebTestClient** for testing reactive REST endpoints

Test coverage includes all CRUD operations, error scenarios, and reactive stream behavior.

## Project Structure

```
src/
├── main/
│   ├── java/com/imamfahrudin/notes/
│   │   ├── SpringReactiveNotesApplication.java
│   │   ├── config/
│   │   │   ├── DatabaseConfig.java
│   │   │   └── OpenApiConfig.java
│   │   ├── controller/
│   │   │   └── NoteController.java
│   │   ├── model/
│   │   │   └── Note.java
│   │   ├── repository/
│   │   │   └── NoteRepository.java
│   │   └── service/
│   │       └── NoteService.java
│   └── resources/
│       ├── application.yml
│       └── schema.sql
└── test/
    └── java/com/imamfahrudin/notes/
        ├── controller/
        │   └── NoteControllerIntegrationTest.java
        ├── repository/
        │   └── NoteRepositoryTest.java
        └── service/
            └── NoteServiceTest.java
```

## Contributing

### Commit Message Conventions

This project follows conventional commit format. All commit messages must start with `<type>:` where type is one of:

- `feat`: A new feature
- `fix`: A bug fix
- `docs`: Documentation only changes
- `style`: Changes that do not affect the meaning of the code (white-space, formatting, etc.)
- `refactor`: A code change that neither fixes a bug nor adds a feature
- `test`: Adding missing tests or correcting existing tests
- `chore`: Changes to the build process or auxiliary tools

**Examples:**
- `feat: add user authentication`
- `fix: resolve null pointer exception`
- `test: add unit tests for note service`
- `test: add integration tests for note repository`
- `docs: update README with testing instructions`

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

Copyright (c) 2025 Mukhammad Imam Fahrudin