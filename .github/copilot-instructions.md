# Spring Reactive Notes - AI Coding Agent Instructions

## Project Overview
Spring Boot 3.2 reactive CRUD application using **WebFlux + R2DBC + H2** for fully non-blocking database operations. Java 21, Maven-based build.

## Architecture Pattern
**3-Layer Reactive Architecture**: Controller → Service → Repository (all return `Mono<T>` or `Flux<T>`)

- **Controllers** (`controller/`): REST endpoints using `@RestController`, return reactive types directly
- **Services** (`service/`): Business logic layer using constructor injection via `@RequiredArgsConstructor`
- **Repositories** (`repository/`): Extend `ReactiveCrudRepository<Note, Long>` - no custom implementations
- **Models** (`model/`): Entity classes with `@Table` annotation, use Lombok (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`)

## Key Technology Decisions

### Reactive Stack (Non-Blocking I/O)
- **WebFlux** instead of Spring MVC - all endpoints return `Mono<T>` (single item) or `Flux<T>` (stream)
- **R2DBC** instead of JPA - reactive database driver for H2 (`r2dbc:h2:mem:///testdb`)
- Never use blocking operations (JDBC, JPA, `block()`, `toFuture().get()`) - defeats reactive purpose

### Database Initialization
`DatabaseConfig` uses `ConnectionFactoryInitializer` to execute `schema.sql` on startup (R2DBC approach, not standard Spring Boot auto-config)

## Code Conventions

### Entity ID Handling Pattern
**Critical**: In `createNote()`, explicitly set `note.setId(null)` before saving to force INSERT:
```java
@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public Mono<Note> createNote(@RequestBody Note note) {
    note.setId(null); // Prevents UPDATE operation
    return noteService.save(note);
}
```

### Dependency Injection
Use **constructor injection** via Lombok's `@RequiredArgsConstructor` (not `@Autowired`)

### Nullability
Use `@NonNull` from `org.springframework.lang` on method parameters that shouldn't be null

### Documentation
**Mandatory**: All public classes, methods, and interfaces must have Javadoc comments
- When creating new code: Always add Javadoc with `@param`, `@return`, and description
- When modifying existing code: Update Javadoc to reflect changes
- Style: Use complete sentences, explain the "why" not just the "what"
- Example from `NoteService.findById()`:
```java
/**
 * Finds a note by its ID.
 *
 * @param id the ID of the note
 * @return a Mono of the note if found
 */
```

## Development Workflows

### Running the Application
```powershell
mvn spring-boot:run
```
- App runs on port 8080
- H2 Console: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:testdb`)
- Swagger UI: http://localhost:8080/swagger-ui.html

### Building
```powershell
mvn clean package
```

### Testing
No test framework configured yet. When adding tests:
- Use `reactor-test` (already in `pom.xml`)
- Use `StepVerifier` to test reactive streams
- Consider `WebTestClient` for controller integration tests

## API Endpoints
- `GET /notes` - List all notes
- `GET /notes/{id}` - Get single note
- `POST /notes` - Create note (returns 201)
- `PUT /notes/{id}` - Update note
- `DELETE /notes/{id}` - Delete note (returns 204)

## Common Pitfalls
1. **Don't call `.block()`** on reactive types in production code - breaks non-blocking model
2. **R2DBC != JPA** - no lazy loading, no entity manager, simpler relationship mapping
3. **Schema must be in `schema.sql`** - R2DBC doesn't auto-generate tables like Hibernate
4. **Lombok must be excluded** from final JAR (see `pom.xml` maven plugin config)
