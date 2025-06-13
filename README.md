# URL Shortener

A simple URL shortener built with Spring Boot and PostgreSQL that converts long URLs into short links with automatic redirects.

## Prerequisites
- Java 17+
- Maven 3.6+
- PostgreSQL database

## Setup

### Option 1: Local Setup
1. **Clone and build:**
   ```bash
   git clone <repo-url>
   cd urlShorter
   mvn clean install
   ```

2. **Configure database in `application.properties`:**
   ```properties
   spring.datasource.url=jdbc:postgresql://your-host:5432/your-db
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   ```

3. **Run:**
   ```bash
   mvn spring-boot:run
   ```

### Option 2: Docker
1. **Create Dockerfile:**
   ```dockerfile
   FROM openjdk:17-jdk-slim
   COPY target/urlShorter-0.0.1-SNAPSHOT.jar app.jar
   EXPOSE 8080
   ENTRYPOINT ["java", "-jar", "/app.jar"]
   ```

2. **Build and run:**
   ```bash
   mvn clean package
   docker build -t url-shortener .
   docker run -p 8080:8080 url-shortener
   ```

3. **Or use Docker Compose with PostgreSQL:**
   ```yaml
   version: '3.8'
   services:
     app:
       build: .
       ports:
         - "8080:8080"
       depends_on:
         - db
       environment:
         SPRING_DATASOURCE_URL: jdbc:postgresql://db:5432/urlshortener
         SPRING_DATASOURCE_USERNAME: postgres
         SPRING_DATASOURCE_PASSWORD: password
     db:
       image: postgres:15
       environment:
         POSTGRES_DB: urlshortener
         POSTGRES_USER: postgres
         POSTGRES_PASSWORD: password
       ports:
         - "5432:5432"
   ```

## API Usage

**Create short URL:**
```bash
curl -X POST http://localhost:8080/url-shortener/shorten \
  -H "Content-Type: text/plain" \
  -d "https://www.google.com"
```
Response: `a1b2c3d4`

**Use short URL:**  
Visit `http://localhost:8080/url-shortener/a1b2c3d4` → redirects to original URL

**Test redirect with curl:**
```bash
curl -L http://localhost:8080/url-shortener/a1b2c3d4
```

## Features
- Automatic redirects (HTTP 301)
- Duplicate prevention (same URL returns same short code)
- URL validation and normalization
- PostgreSQL persistence with JPA/Hibernate

## Tech Stack
- Spring Boot 3.5.0
- PostgreSQL
- Java 17
- Maven 