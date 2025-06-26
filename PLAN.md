# PomoTune Project Development Plan

## Overall Goal
Build a full-stack PomoTune application using:
- **Backend**: Spring Boot
- **Frontend**: React
- **Database**: PostgreSQL
- **AI Integration**: External AI service

---

## Phase 1: Core Backend - Data Models & Basic CRUD

### User Management (User Entity)

**Objective**: Define the core user structure and enable basic user registration and retrieval.

**Steps**:
- [x] Create User Entity (with id, name, email, password, JPA/Lombok annotations)
- [x] Create UserRepository (Spring Data JPA interface for basic CRUD)
- [x] Implement UserService (logic for user operations, including password hashing with Spring Security's PasswordEncoder)
- [x] Develop UserController (REST API endpoints for user registration and fetching user details)

**Testing**:
- [ ] Test user registration and retrieval via Postman/cURL

### Study Session Tracking (StudySession Entity)

**Objective**: Store and retrieve detailed information about each Pomodoro study session.

**Steps**:
- [ ] Create StudySession Entity (with id, userId, cyclesCompleted, pomodoroType, startTime, endTime, description, JPA/Lombok annotations)
- [ ] Create StudySessionRepository
- [ ] Implement StudySessionService (logic for saving sessions, linking to user)
- [ ] Develop StudySessionController (REST API endpoints for creating and fetching study sessions for a user)

**Testing**:
- [ ] Test saving and retrieving study sessions

### To-Do List Management (TodoItem Entity)

**Objective**: Enable users to create, view, update, and mark tasks as complete.

**Steps**:
- [ ] Create TodoItem Entity (with id, userId, description, completed, createdAt, dueDate, JPA/Lombok annotations)
- [ ] Create TodoItemRepository
- [ ] Implement TodoItemService
- [ ] Develop TodoItemController (REST API endpoints for CRUD operations on to-do items for a user)

**Testing**:
- [ ] Test to-do item creation, retrieval, update, and completion

---

## Phase 2: Core Backend - Advanced Features & Security

### Authentication & Authorization

**Objective**: Secure the application, allowing users to log in and access their own data securely.

**Steps**:
- [ ] Configure Spring Security for JWT (JSON Web Token) authentication
- [ ] Implement user login endpoint, generating JWTs upon successful authentication
- [ ] Secure API endpoints so only authenticated and authorized users can access their data

**Testing**:
- [ ] Test user login and access to secured endpoints

### Leaderboard Logic

**Objective**: Calculate and present a global weekly study time leaderboard.

**Steps**:
- [ ] Implement LeaderboardService (method to calculate weekly study time for all users by querying StudySession data)
- [ ] Develop LeaderboardController (REST API endpoint to expose the leaderboard data)

**Testing**:
- [ ] Verify leaderboard calculations and data exposure

---

## Phase 3: AI Integration

### AI Service Integration

**Objective**: Send study session data to an external AI service and receive motivational feedback.

**Steps**:
- [ ] Choose an AI API (e.g., OpenAI, AWS Bedrock)
- [ ] Implement an AiService in Spring Boot responsible for:
    - Constructing prompts using StudySession data
    - Making HTTP calls to the AI API (using WebClient)
    - Parsing AI responses
- [ ] Modify StudySessionService to call the AiService after a session is saved
- [ ] Add aiFeedbackText field to StudySession entity to store AI response

**Testing**:
- [ ] Verify successful AI API calls and storage of feedback

---

## Phase 4: Frontend Development (React)

### Basic UI Structure

**Objective**: Set up the React project and connect it to the Spring Boot backend.

**Steps**:
- [ ] Initialize React project (e.g., using Vite)
- [ ] Implement basic routing (login, dashboard, to-do list, leaderboard)

**Testing**:
- [ ] Verify React application loads and routes work

### User Authentication UI

**Objective**: Enable users to register and log in via the React frontend.

**Steps**:
- [ ] Create login and registration forms
- [ ] Implement API calls to Spring Boot authentication endpoints
- [ ] Store JWT securely in the browser (e.g., localStorage or http-only cookies)

### Pomodoro Timer & Study Session UI

**Objective**: Implement the interactive Pomodoro timer and display session data.

**Steps**:
- [ ] Build the timer UI (start, pause, reset, progress bar)
- [ ] Send completed session data to Spring Boot
- [ ] Display AI feedback after session completion
- [ ] **Optional**: Implement background music selection from pre-defined lists or YouTube embeds (This might involve using iframes or a more complex streaming solution which can be a stretch goal)

### To-Do List UI

**Objective**: Allow users to manage their tasks in the frontend.

**Steps**:
- [ ] Create UI for displaying, adding, editing, and marking tasks as complete
- [ ] Integrate with TodoItem API endpoints

### Leaderboard UI

**Objective**: Display the weekly global leaderboard.

**Steps**:
- [ ] Create UI for displaying ranked users and their study times
- [ ] Fetch leaderboard data from the Spring Boot API

---

## Phase 5: Deployment & Refinement

### Deployment to AWS

**Objective**: Deploy both the Spring Boot backend and React frontend to AWS.

**Steps**:
- [ ] Deploy Spring Boot to AWS Elastic Beanstalk or EC2
- [ ] Deploy React frontend to AWS S3 + CloudFront
- [ ] Configure PostgreSQL on AWS RDS

### Refinement & Enhancements

**Objective**: Improve user experience, add more features, and optimize performance.

**Steps**:
- [ ] Implement background music playing logic (e.g., integrate YouTube API for playlists)
- [ ] Add user profile pages
- [ ] Implement notifications (e.g., for session completion, weekly reports)
- [ ] Improve UI/UX, styling
- [ ] Error handling and logging

---

## Technology Stack Summary

| Component | Technology |
|-----------|------------|
| Backend | Spring Boot |
| Frontend | React |
| Database | PostgreSQL |
| Authentication | JWT |
| AI Integration | OpenAI/AWS Bedrock |
| Deployment | AWS (EC2/Elastic Beanstalk, S3, RDS) |
| Build Tools | Maven/Gradle (Backend), Vite (Frontend) |