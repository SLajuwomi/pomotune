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
  - -p 5432:5432: Maps the container's port 5432 (default PostgreSQL port) to the host machine's port 5432, allowing you to connect to the database from your host.
  - -e POSTGRES_PASSWORD=mysecretpassword: Sets the password for the default postgres user. Remember to choose a strong password!
  - postgres: Specifies the image to use.

- docker exec -it postgres psql -U postgres
- CREATE DATABASE pomotune;
- \q
- docker stop postgres
- docker rm postgres
- docker run --name postgres -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 postgres

The datasource url in the application.properties file points to the database in the docker container. So remember to create the database in the docker container. - docker exec -it postgres psql -U postgres - CREATE DATABASE pomotune;

: HHH90000025: PostgreSQLDialect does not need to be specified explicitly using 'hibernate.dialect' (remove the property setting and it will be selected by default)

# Repository

- Repository is an interface that extends JpaRepository.
- JpaRepository is a Spring Data JPA interface that provides basic CRUD operations for the entity class that is passed to it.
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
