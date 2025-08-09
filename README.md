# Rankr

Rankr is a web application that allows users to create groups and quizzes, with leaderboards for member responses. Built using Spring Boot, this application aims to provide a user-friendly interface for managing quizzes and tracking performance.

## Features

- User registration and authentication
- Group creation and management
- Quiz creation and management
- Leaderboard tracking for quizzes

## Technologies Used

- Java 17
- Spring Boot 2.7.11
- Spring Security
- PostgreSQL
- Hibernate
- Maven
- JPA

## Installation

To get started with the project, follow these steps:

### Step 1: Clone the Repository

```bash
git clone https://github.com/Uzayur/rankr-back.git
```
```bash
cd rankr-back
```

### Step 2: Configure your PostgreSQL database
Add the following properties to src/main/resources/application.properties with your PostgreSQL database details:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Step 3: Install the dependencies using Maven
For Linux or macOS:
```bash
mvn clean install
```
For Windows use:
```bash
.\mvnw.cmd clean install
```

### Step 4: Start the application
For Linux or macOS:
```bash
mvn spring-boot:run
```
For Windows use:
```bash
.\mvnw.cmd spring-boot:run
```
The application will be available at http://localhost:8080

## Frontend Repository

The frontend of the Rankr application can be found in the following repository:

- [Rankr Frontend Repository](https://github.com/Uzayur/rankr-front)

## License

This project is licensed under the **GNU General Public License v3.0** (GPL-3.0). See the [LICENSE](LICENSE) file for more details.