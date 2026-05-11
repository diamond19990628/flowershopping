# Flower Shop API

## Language

- [japanese](./README.ja.md)

# Flower Shop Backend System

This is the backend system for a flower shopping application.

The system includes both administrator pages and user pages.

Users are not allowed to access administrator pages unless their role is set to administrator.

The administrator pages include four main features:

- Product Management
- Category Management
- Order Management
- Notice Management

Session and token-based authentication are used to control access to protected APIs.

Users without valid authentication tokens are not allowed to use restricted APIs.

During order creation, optimistic locking is implemented to prevent duplicate order creation and inventory overselling.

The system uses MySQL transaction atomicity to ensure data consistency during order processing.

## Technology Stack

- Java
- Spring Boot
- MyBatis
- MySQL
- Maven
- RabbitMQ
- RESTful API
- Nginx
- AWS

## Features

- User Authentication
- Role-Based Access Control
- Product Management
- Category Management
- Order Management
- Notice Management
- Optimistic Locking
- Idempotency Protection
- Delayed Order Cancellation
- WeChat Pay Integration
- Refund Processing

## Prerequisites
- java17
- mysql
- rabbitMQ
- wechat miniprogram
- mvn environment

## Install step

1. Clone this repository to your local machine.

2. Create a new MySQL database named `flowershoppingDB`.

3. Execute all SQL scripts in the `db-src` folder for the created database before starting the application.

4. Configure your environment settings in `application.properties`.
   You may modify the configuration values according to your local environment.

