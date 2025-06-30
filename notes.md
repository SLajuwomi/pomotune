# Spring Boot's "Convention over Configuration"

- automatic configuration
- I need to tell Spring Boot
    - where my database is (URL, host, port)
    - how to connect to the database (username, password)
    - what to do with my entities (create tables, update schema, etc.)
    - This is done in the `application.properties` file

# Database Setup Strat

For development, there are two main options:

1. Use an in-memory database
2. Use a real database

## In-memory database

## Real database

- Use a real database like PostgreSQL, MySQL, etc.

# Create Postgres Database in Docker

- docker pull postgres
- docker run --name postgres -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 postgres

    - -d: Runs the container in detached mode (in the background).W
    - --name: Assigns a name to the container (my-postgres-container).
    - -p 5432:5432: Maps the container's port 5432 (default PostgreSQL port) to the host machine's port 5432, allowing
      you to connect to the database from your host.
    - -e POSTGRES_PASSWORD=mysecretpassword: Sets the password for the default postgres user. Remember to choose a
      strong password!
    - postgres: Specifies the image to use.

- docker exec -it postgres psql -U postgres
- CREATE DATABASE pomotune;
- \q
- docker stop postgres
- docker rm postgres
- docker run --name postgres -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 postgres

The datasource url in the application.properties file points to the database in the docker container. So remember to
create the database in the docker container. - docker exec -it postgres psql -U postgres - CREATE DATABASE pomotune;

: HHH90000025: PostgreSQLDialect does not need to be specified explicitly using 'hibernate.dialect' (remove the property
setting and it will be selected by default)

# Repository

- Repository is an interface that extends JpaRepository.
- JpaRepository is a Spring Data JPA interface that provides basic CRUD operations for the entity class that is passed
  to it.
- JpaRepository<User, Long> is a JpaRepository that is used to perform CRUD operations on the User entity.
- The User entity is the entity that is being persisted to the database.
- The Long is the type of the primary key of the User entity.

# findBy

- findBy in the repository is a query method that is used to find the entity by the given field.
- using findBy with a field name will generate a query to the database to find the entity by the given field.
- example:
    - findByEmail(String email) will generate a query to the database to find the entity by the given email.

# Autowired

- Autowired is a Spring annotation that is used to inject the dependency of the class into the current class.
- It is used to inject the dependency of the class into the current class.
- example:
    - @Autowired private UserRepository userRepository;
    - This will inject the UserRepository into the current class.
    - The UserRepository is the repository that is used to perform CRUD operations on the User entity.
    - The UserRepository is injected into the current class using the @Autowired annotation.
    - The UserRepository is injected into the current class using the @Autowired annotation.

# JUnit Assertions

- methods that help verify the expected behavior of your code during testing

# H2 vs PostgreSQL

- use H2 when developing locally and running several tests
- wse postgres when testing in live env

# Testing findBy methods

- You can test how many users were found and if their email is correct
- use indexes and store found users in a list

```java

@Test
@DisplayName("find user by email")
void testFindByEmail() {
    // Arrange
    User user = new User();
    user.setName("Test User");
    user.setEmail("test@example.com");
    user.setPassword("password");
    userRepository.save(user);

    // Act
    List<User> foundUsers = userRepository.findByEmail("test@example.com");

    // Assert
    assertEquals(1, foundUsers.size());
    assertEquals("test@example.com", foundUsers.get(0).getEmail());
    assertEquals("Test User", foundUsers.get(0).getName());
}

@Test
@DisplayName("find user by email returns empty list when no match")
void testFindByEmailReturnsEmptyList() {
    // Act
    List<User> foundUsers = userRepository.findByEmail("nonexistent@example.com");

    // Assert
    assertTrue(foundUsers.isEmpty());
}

```

# Service Layer

- you store the business logic of the application
- acts as an intermediary between your controllers (handles web requests) and repositories (handle database
  interactions)
- Benefits:
    - keep controllers and repositores clean and focused
    - can be reused by other parts of the application
    - transactions are managed here
    - complex validation is done here i.e. validating unique email address, password policies

You need to inject the repository into this layer. This can be done through instructor or dependency injection

Example:

```java
    private final UserRepository userRepository;

// Constructor Injection
public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
}
```

## Handling Errors

- learned how to add an exception handler
- need to check terminal to find name of exception eg. DataIntegrityViolationException.class
- then add new @ExceptionHandler with name of exception
    - @ExceptionHandler(DataIntegrityViolationException.class)
- create class with return type of ResponseEntity
    - ResponseEntity is a class in Spring Framework that represents the entire HTTP response, including the status code,
      headers, and body. It's part of Spring's web module and provides a way to have full control over the HTTP response
      you send back to clients.
- make JSON objet and return the status, and messages you want
- return final ResponseEntity with status code and JSON body

```java

// Handle duplicate data
@ExceptionHandler(DataIntegrityViolationException.class)
public ResponseEntity<Map<String, Object>> handleDataInegrityViolation(DataIntegrityViolationException ex) {
  Map<String, Object> response = new HashMap<>();
  response.put("status", "error");
  response.put("message", ex.getMessage());

  String errorMessage = ex.getMessage();

  if (errorMessage.contains("unique constrain") && errorMessage.contains("email")) {
    response.put("message", "A user with this email address already exists");
  } else if (errorMessage.contains("duplicate key")) {
    response.put("message", "This information is already in use");
  } else {
    response.put("message", "Database constraint violation occurred");
  }


  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
}

```




## Foreign Keys in SpringBoot

- @JoinColumn(name = "user_id")
- define @ManyToOne, @ManyToMany...

### Audit Trail
- automatically track when entities are created in the database
  - useful for debugging, compliance requirements, analytics
- create LocalDateTime createdAt
- set @Column annotation
- @PrePersist
  - JPA lifecycle callback annotation
  - runs right before the entity is saved to db automatically
  - onlt triggers on INSERT, not UPDATE

```java
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
```

- can also be done with Hibernate annotations for simpler implementation
```java
@CreationTimestamp
@Column(name = "created_at", updatable = false)
private LocalDateTime createdAt;

@UpdateTimestamp
@Column(name = "updated_at")
private LocalDateTime updatedAt;
```

### StudySessionRepository
- all the findBy stuff is the Spring Data JPA Repositoty that is automatically generating SQL queries
- you can create other methods like
  - countBy
  - And
  - StartTimeBetween
  - etc


### Providing dependencies to a class - Dependency injection patterns
- Constructor injection is recommended over field injection (@Autowired on fields)
- Constructor injection - Spring automatically injects dependencies
- Field injection
  - @Autowired - injects via reflection after object creation, not recommended
  - Problems:
    - Can't mark fields as final
    - Hard to test (need Spring context)
    - Reflection overhead
    - Can create objects in invalid state
- There is also Setter injection but it is rarely used
```java
@Service
public class StudySessionService {
    
    private StudySessionRepository studySessionRepository;
    private UserService userService;
    
    @Autowired
    public void setStudySessionRepository(StudySessionRepository repo) {
        this.studySessionRepository = repo;
    }
    
    @Autowired  
    public void setUserService(UserService service) {
        this.userService = service;
    }
}
```
- You can use Lombok to decrease boilerplate
```java
@Service
@RequiredArgsConstructor // Lombok generates constructor for final fields
public class StudySessionService {
    
    private final StudySessionRepository studySessionRepository;
    private final UserService userService;
    private final NotificationService notificationService;
    
    // Constructor automatically generated by Lombok!
    
    public StudySession createSession(Long userId) {
        User user = userService.findById(userId);
        // ... implementation
    }
}
```
- Constructor injection makes testing easier
```java
@ExtendWith(MockitoExtension.class)
class StudySessionServiceTest {
    
    @Mock
    private StudySessionRepository studySessionRepository;
    
    @Mock
    private UserService userService;
    
    @Mock
    private NotificationService notificationService;
    
    private StudySessionService studySessionService;
    
    @BeforeEach
    void setUp() {
        // Easy to create service with mocked dependencies
        studySessionService = new StudySessionService(
            studySessionRepository, 
            userService, 
            notificationService
        );
    }
    
    @Test
    void shouldCreateSession() {
        // Test implementation...
        // No Spring context needed!
    }
}
```
- Benefits of Constructor Injection
  - Immutability - dependencies can be final
  - Fail Fast - missing dependencies cause startup failure, not runtime errors
  - Testability - easy to create objects with mock dependencies
  - Thread safety - final fields are thread-safe
  - Clear dependencies - constructor signature shows all required dependencies
  - No reflection - direct object creation (faster than field injection)