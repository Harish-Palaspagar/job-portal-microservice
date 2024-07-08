# Microservices Project

## Description

This project is a collection of Spring Boot microservices designed to manage company reviews. It uses Eureka for service discovery, RabbitMQ for message communication, Zipkin for distributed tracing, and a GitHub-based Config Server for centralized configuration. The main components of the project are:

- **API Gateway**: Serves as the entry point for all client requests.
- **Company Service**: Manages company-related operations.
- **Review Service**: Manages review-related operations, including fetching, saving, updating, and deleting reviews, as well as calculating the average rating for a company.

## Prerequisites

Before running the project, ensure you have the following installed:

- Java 11 or later
- Maven
- RabbitMQ
- Eureka Server
- Zipkin Server
- Spring Cloud Config Server (using GitHub)

## Setup

1. **Clone the repository**:

    ```sh
    git clone https://github.com/yourusername/microservices-project.git
    cd microservices-project
    ```

2. **Configure RabbitMQ**:

    Ensure RabbitMQ is running on your local machine or a server. Update the `application.properties` or `application.yml` files with the appropriate RabbitMQ configuration.

3. **Start Eureka Server**:

    Ensure the Eureka Server is running. Update the `application.properties` or `application.yml` files in each service to point to the Eureka Server.

4. **Start Zipkin Server**:

    Ensure the Zipkin Server is running. Update the `application.properties` or `application.yml` files to point to the Zipkin Server for tracing.

5. **Configure GitHub Config Server**:

    - Create a configuration repository on GitHub.
    - Add your configuration files (`application.yml`, `application-<profile>.yml`) to the repository.
    - Update the `bootstrap.yml` in each service to point to the GitHub Config Server. Example configuration:

    ```yaml
    spring:
      cloud:
        config:
          uri: http://localhost:8888
          name: <application-name>
          profile: <profile>
          label: master
      config:
        import: configserver:http://<your-github-config-server-url>
    ```

6. **Build and Run the Services**:

    Navigate to each service directory and run the following commands:

    ```sh
    mvn clean install
    mvn spring-boot:run
    ```

    Ensure to start the services in the following order:
    - Config Server
    - Eureka Server
    - API Gateway
    - Company Service
    - Review Service

## Usage

### API Gateway

The API Gateway routes requests to the appropriate services. Configure the gateway routes in `application.yml` of the API Gateway service.

### Company Service

The Company Service provides endpoints to manage company-related data.

### Review Service

The Review Service provides endpoints to manage review-related data, including fetching, saving, updating, and deleting reviews. It also calculates the average rating for a company.

## Configuration

### application.yml (API Gateway)

```yaml
spring:
  application:
    name: api-gateway
server:
  port: 8085
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
spring.cloud.gateway:
  routes:
    - id: company_service
      uri: lb://COMPANY-SERVICE
      predicates:
        - Path=/companies/**
    - id: review_service
      uri: lb://REVIEW-SERVICE
      predicates:
        - Path=/reviews/**
logging:
  level:
    org.springframework: INFO
    org.springframework.web: DEBUG
    com.example: DEBUG
spring:
  config:
    import: configserver:http://<your-github-config-server-url>
