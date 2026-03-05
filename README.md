# Sky Takeout - Food Delivery System

[![Java](https://img.shields.io/badge/Java-17%2B-blue)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.3-brightgreen)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-orange)](https://www.mysql.com/)
[![Redis](https://img.shields.io/badge/Redis-7.0-red)](https://redis.io/)
[![MyBatis](https://img.shields.io/badge/MyBatis-2.2.2-yellow)](https://mybatis.org/mybatis-3/)
[![License](https://img.shields.io/badge/License-MIT-green)](LICENSE)

## 📋 Overview

Sky Takeout is a comprehensive food delivery platform built with Spring Boot. The system consists of two main applications: an admin management backend and a user-facing mobile interface. It provides a complete solution for food ordering, delivery management, and restaurant operations.

### ✨ Key Features

- **Dual-Interface Design**: Separate admin and user interfaces
- **Order Management**: Complete order lifecycle management
- **Real-time Tracking**: Order status updates and delivery tracking
- **Shopping Cart**: Persistent shopping cart functionality
- **Payment Integration**: Mock payment processing
- **Address Management**: User address book for deliveries
- **Data Analytics**: Sales statistics and business insights
- **File Management**: Image upload and storage

## 🏗️ System Architecture

```
sky-takeout/
├── sky-common/           # Common utilities and constants
│   ├── src/main/java/com/sky/constant/
│   ├── src/main/java/com/sky/exception/
│   ├── src/main/java/com/sky/properties/
│   └── src/main/java/com/sky/utils/
│
├── sky-pojo/             # POJO classes
│   ├── src/main/java/com/sky/dto/      # Data Transfer Objects
│   ├── src/main/java/com/sky/entity/   # Entity classes
│   └── src/main/java/com/sky/vo/       # View Objects
│
├── sky-server/           # Main server application
│   ├── src/main/java/com/sky/
│   │   ├── controller/    # REST controllers
│   │   ├── service/       # Business logic
│   │   ├── mapper/        # MyBatis interfaces
│   │   ├── config/        # Configuration classes
│   │   ├── interceptor/   # Interceptors
│   │   └── task/          # Scheduled tasks
│   └── src/main/resources/
│       ├── mapper/        # MyBatis XML mappers
│       └── application.yml # Application config
│
└── pom.xml                # Parent Maven configuration
```

## 🛠️ Technology Stack

### Backend Framework
- **Spring Boot 2.7.3**: Core application framework
- **Spring MVC**: Web layer and REST APIs
- **Spring Security**: Authentication and authorization
- **JWT**: Token-based authentication

### Data Access
- **MyBatis**: ORM framework
- **MySQL**: Relational database
- **Redis**: Caching and session management
- **PageHelper**: Pagination support

### Development Tools
- **Lombok**: Boilerplate code reduction
- **Swagger/Knife4j**: API documentation
- **Maven**: Build and dependency management

### External Services
- **Aliyun OSS**: File storage service
- **WebSocket**: Real-time notifications
- **HttpClient**: HTTP communication

## 📦 Project Structure
sky-takeout/
├── sky-common/ # Common utilities and constants
│ ├── src/main/java/com/sky/constant/
│ ├── src/main/java/com/sky/exception/
│ ├── src/main/java/com/sky/properties/
│ └── src/main/java/com/sky/utils/
│
├── sky-pojo/ # POJO classes
│ ├── src/main/java/com/sky/dto/ # Data Transfer Objects
│ ├── src/main/java/com/sky/entity/ # Entity classes
│ └── src/main/java/com/sky/vo/ # View Objects
│
├── sky-server/ # Main server application
│ ├── src/main/java/com/sky/
│ │ ├── controller/ # REST controllers
│ │ ├── service/ # Business logic
│ │ ├── mapper/ # MyBatis interfaces
│ │ ├── config/ # Configuration classes
│ │ ├── interceptor/ # Interceptors
│ │ └── task/ # Scheduled tasks
│ └── src/main/resources/
│ ├── mapper/ # MyBatis XML mappers
│ └── application.yml # Application config
│
└── pom.xml # Parent Maven configuration

text

## 🚀 Getting Started

### Prerequisites

- JDK 17 or higher
- MySQL 8.0
- Redis 7.0
- Maven 3.6+
- Git

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/BODYsuperman/sky-take-out.git
   cd sky-takeout