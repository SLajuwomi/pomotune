# PomoTune Development Context

## 📋 Project Overview

**PomoTune** is a Pomodoro timer application with YouTube music integration, AI assistance, and gamification features. The user is a **beginner to Spring Boot** but motivated to learn the full stack.

### Core Features Planned

- Pomodoro timer functionality
- YouTube music selection with AI suggestions
- User leaderboard/ranking system
- AI-powered todo list management
- Motivational quotes system
- End-of-session AI feedback
- User authentication and profiles

### Technology Stack

- **Backend:** Spring Boot 3.5.0 + Java 21
- **Frontend:** React with Tailwind CSS (preferred styling framework)
- **Database:** PostgreSQL
- **Cloud:** AWS ecosystem (user preference for all hosting/deployment)
- **Dependencies:** JPA, Security, Web, PostgreSQL, Lombok, DevTools

## 🎯 Current State - What's Been Accomplished

### ✅ Infrastructure Setup

1. **Spring Boot Project Structure**

   - Maven project with proper dependencies configured
   - Application runs successfully on port 8080
   - DevTools enabled for hot reload

2. **Database Configuration**

   - PostgreSQL running in Docker container (`pomotune-db`)
   - Container: `docker run -d --name pomotune-db -e POSTGRES_PASSWORD=Theworld77 -p 5433:5432 postgres`
   - Database `pomotune` created successfully
   - Connection verified: `spring.datasource.url=jdbc:postgresql://localhost:5433/pomotune`

3. **Application Properties Configured**
   ```properties
   spring.application.name=pomotune
   spring.datasource.url=jdbc:postgresql://localhost:5433/pomotune
   spring.datasource.username=postgres
   spring.datasource.password=Theworld77
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
   ```

### ✅ Domain Model

1. **User Entity** (`src/main/java/com/slajuwomi/pomotune/user/domain/User.java`)
   - Properly annotated with JPA annotations
   - Uses Lombok for boilerplate reduction
   - Fields: `id` (auto-generated), `name`, `email` (unique), `password`
   - Database table `users` created successfully in PostgreSQL

### ✅ Repository Layer

1. **UserRepository** (`src/main/java/com/slajuwomi/pomotune/repository/UserRepository.java`)
   - Extends `JpaRepository<User, Long>`
   - Custom method: `findByEmail(String email)`
   - Spring Data JPA automatically implements CRUD operations

### ✅ Testing Foundation

1. **UserRepositoryTest** (`src/test/java/com/slajuwomi/pomotune/repository/UserRepositoryTest.java`)
   - Uses `@DataJpaTest` annotation
   - Successfully tests User save operation
   - Test passes with H2 in-memory database (Spring Boot default for @DataJpaTest)
   - SQL logging working correctly
   - Proper assertion pattern established

## 📚 Key Learning Points Achieved

### Spring Boot Concepts Mastered

1. **Layered Architecture Understanding**

   - Domain Layer (entities)
   - Repository Layer (data access)
   - Service Layer (business logic) - _next step_
   - Controller Layer (REST APIs) - _next step_

2. **Spring Data JPA**

   - Entity mapping with annotations
   - Repository pattern implementation
   - Auto-generated ID strategy
   - Custom query methods by naming convention

3. **Spring Boot Configuration**

   - Database connection configuration
   - Property-based configuration in `application.properties`
   - Hibernate DDL auto-generation

4. **Testing Strategies**
   - Difference between `@DataJpaTest` and `@SpringBootTest`
   - H2 in-memory database for isolated testing
   - SQL logging for debugging
   - Proper test structure and assertions

### Development Practices Established

1. **Question Driven Development (QDD)** approach
2. **Research-first methodology** before implementation
3. **Step-by-step troubleshooting** for configuration issues
4. **Verification patterns** for database connectivity

## 🔧 Technical Details for Future Reference

### Docker Commands Used

- Pull PostgreSQL: `docker pull postgres`
- Run container: `docker run -d --name pomotune-db -e POSTGRES_PASSWORD=Theworld77 -p 5433:5432 postgres`
- Access database: `docker exec -it pomotune-db psql -U postgres -d pomotune`
- List tables: `\dt`

### Maven Commands Used

- Run specific test: `./mvnw -Dtest=com.slajuwomi.pomotune.service.UserRepositoryTest --no-transfer-progress process-test-classes surefire:test`

### Important File Locations

- Main application: `src/main/java/com/slajuwomi/pomotune/PomotuneApplication.java`
- User entity: `src/main/java/com/slajuwomi/pomotune/user/domain/User.java`
- User repository: `src/main/java/com/slajuwomi/pomotune/repository/UserRepository.java`
- Repository test: `src/test/java/com/slajuwomi/pomotune/repository/UserRepositoryTest.java`
- Configuration: `src/main/resources/application.properties`

## 🚀 Next Logical Steps (In Priority Order)

### Immediate Next Steps (Foundation Building)

1. **Enhance Repository Testing**

   - Add test for `findByEmail` method
   - Add test for unique email constraint violation
   - Add test verifying auto-generated ID functionality
   - Consider adding PostgreSQL integration test for comparison

2. **Create Service Layer**

   - `UserService` class with business logic
   - Methods: `createUser`, `findUserByEmail`, `getAllUsers`
   - Handle business rules (e.g., email validation, password encryption)
   - Add `@Service` annotation
   - Inject `UserRepository`

3. **Build First REST Controller**
   - `UserController` with basic CRUD endpoints
   - `POST /api/users` - create user
   - `GET /api/users/{id}` - get user by ID
   - `GET /api/users` - list all users
   - Add `@RestController` and `@RequestMapping` annotations

### Medium-term Goals (Core Features)

4. **Add Input Validation**

   - Add validation annotations to User entity
   - Handle validation errors in controller
   - Create proper error responses

5. **Implement Security Configuration**

   - Configure Spring Security (currently using default)
   - Disable security for development OR implement basic auth
   - Plan for OAuth integration later

6. **Create Pomodoro Timer Entities**
   - `PomodoroSession` entity
   - `TimerSettings` entity
   - Repository and service layers for timer functionality

### Future Features (After Core CRUD)

7. **Frontend Integration**

   - Set up React application
   - Configure CORS for API access
   - Implement user registration/login UI

8. **Advanced Features**
   - YouTube API integration
   - AI service integration
   - Leaderboard functionality
   - AWS deployment preparation

## ⚠️ Important Notes and Decisions Made

### User Preferences Established

- **Styling:** Tailwind CSS for React frontend
- **Hosting:** AWS ecosystem for all deployment
- **Approach:** Simple, clean, efficient code with minimal lines
- **Learning Style:** Research-driven with guided implementation

### Technical Decisions Made

- **Database:** PostgreSQL in Docker for development (production-like setup)
- **Testing:** H2 for repository tests (fast, isolated) + PostgreSQL for integration tests when needed
- **ID Strategy:** Auto-generated using `IDENTITY` strategy
- **Package Structure:** Domain-driven with separate packages for different concerns

### Configuration Choices

- **Hibernate DDL:** `update` for development (preserves data between restarts)
- **SQL Logging:** Enabled for debugging and learning
- **Port Mapping:** Host 5433 → Container 5432 (to avoid conflicts with local PostgreSQL)

## 🔍 Debugging Reference

### Common Issues Solved

1. **Docker Port Mapping:** Corrected from `-p 5433:5433` to `-p 5433:5432`
2. **Database Creation:** Must create `pomotune` database manually in PostgreSQL
3. **Test Database:** `@DataJpaTest` uses H2 by default, not configured PostgreSQL
4. **ID Generation:** Don't manually set ID when using `@GeneratedValue`

### Success Indicators

- Application starts without errors
- Database connection pool initializes successfully
- Hibernate shows SQL statements in logs
- Tests pass with proper assertions
- Docker container accessible via `docker exec`

## 📖 Learning Resources Used

- Spring Boot Data JPA documentation
- PostgreSQL Docker setup tutorials
- Spring Boot testing best practices
- Maven Surefire plugin for test execution

---

**Status:** Ready for Service Layer development
**Next Mentor Focus:** Business logic implementation and REST API creation
**Student Confidence Level:** High on repository layer, ready for next concepts
