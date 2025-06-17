# Project Blueprint: PomoTune

## 1. 📝 Project Requirements (The "What")

- **Core Purpose:** A Pomodoro timer where users can play background music from YouTube of their own choice, rank up on a leaderboard, and get AI assistance for productivity. The main goal is to create an engaging productivity app while learning AI integration.

- **Main Features:**

  - Pomodoro timer functionality
  - Leaderboard/ranking system based on time studied and number of sessions
  - Music selection page with YouTube long videos/live streams (with thumbnails and search)
  - AI music suggestions when search fails to find requested music
  - AI-powered todo list generation and management helper
  - Motivational quotes system (after 25-minute work sessions and 5-minute breaks) - mix of API and AI-generated quotes
  - End-of-session AI feedback where users can share struggles and get tips/encouragement
  - User authentication, profiles, and comprehensive statistics

- **Tutor Note:** Focus tutoring on building these features in logical sequence: Start with basic Pomodoro timer and user authentication, then music integration, followed by AI features, and finally leaderboard/statistics. This progressive approach will help the beginner developer understand Spring Boot concepts incrementally.

## 2. 🏗️ Design & Technology (The "How")

- **Frontend Framework:** React with Tailwind CSS for styling
- **Backend Framework:** Spring Boot + Java 21
- **Database:** PostgreSQL (already configured in pom.xml)
- **Cloud Services:** AWS ecosystem (S3 for profile pictures, RDS for database, Elastic Beanstalk for backend, CloudFront + S3 for frontend)
- **Tutor Note:** This is a modern full-stack setup perfect for learning. Guide the user through Spring Boot fundamentals first, then React integration, and finally AWS deployment. The existing Spring Boot setup includes JPA, Security, and PostgreSQL - excellent foundation for this project.

## 3. 📊 Implementation Context

- **User's Declared Skill Level:** Beginner - specifically new to Spring Boot, but motivated to learn the entire stack
- **Tutor Note:** User is a beginner with Spring Boot but eager to learn comprehensive technologies including AI integration and AWS. Provide detailed explanations of Spring Boot concepts, REST API design, and database relationships. Break down complex topics into digestible steps and explain the "why" behind architectural decisions.

## 4. 🧪 Testing Strategy

- **Approach:** Start with manual testing, gradually introduce automated testing as comfort level increases
- **Framework(s):** JUnit (already included in Spring Boot), Mockito for mocking, Jest + React Testing Library for frontend testing (to be added later)
- **Tutor Note:** Perfect approach for a beginner. Start with manual testing to understand application behavior, then introduce unit tests for core business logic (timer functions, leaderboard calculations). Emphasize testing AI integration endpoints and authentication flows as these are critical components.

## 5. 🚀 Deployment Plan

- **Hosting Platform:** Full AWS ecosystem approach
  - **Backend:** AWS Elastic Beanstalk (easier than EC2 for beginners)
  - **Frontend:** AWS S3 + CloudFront for static hosting
  - **Database:** AWS RDS (managed PostgreSQL)
  - **File Storage:** AWS S3 (for user profile pictures)
- **Tutor Note:** Excellent choice for learning cloud deployment. Introduce AWS concepts gradually - start with S3 for file storage, then RDS for database, and finally Elastic Beanstalk for application deployment. This progression will build confidence with AWS services while maintaining focus on application development.

## 6. 🔒 Security Plan

- **Authentication/Authorization:** OAuth integration (Google/GitHub login preferred)
- **Additional Security Considerations:**
  - JWT tokens for API authentication
  - File upload security (type/size restrictions for profile pictures)
  - YouTube API key security
  - AI API key management
  - Rate limiting for AI services
- **Tutor Note:** OAuth is an excellent learning choice - more complex than basic auth but teaches industry-standard practices. Focus on Spring Security OAuth configuration, secure API key management, and proper file upload validation. This will provide comprehensive security education while building practical skills.
