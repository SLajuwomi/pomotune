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
