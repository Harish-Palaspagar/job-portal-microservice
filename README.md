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
    https://github.com/Harish-Palaspagar/job-portal-microservices
    ```

2. **Configure RabbitMQ**:

    Ensure RabbitMQ is running on your local machine or a server. Update the `application.properties` or `application.yml` files with the appropriate RabbitMQ configuration.

3. **Start Eureka Server**:

    Ensure the Eureka Server is running. Update the `application.properties` or `application.yml` files in each service to point to the Eureka Server.

4. **Start Zipkin Server**:

    Ensure the Zipkin Server is running. Update the `application.properties` or `application.yml` files to point to the Zipkin Server for tracing.

