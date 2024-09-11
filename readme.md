```markdown
# BrokerCore 

This project provides an API for managing customers, assets, and orders in a financial system. It supports money transfers, order management (BUY/SELL), and user authentication. The application follows a microservice architecture, where the money transfer functionality can be decoupled into a separate microservice.

## Features

- User Registration and Authentication
- Asset Management (e.g., managing asset sizes like TRY)
- Order Management (BUY/SELL orders)
- Money Transfer Service
- Admin controls for managing pending orders
- Swagger integration for API testing

## Controllers Overview

### 1. AuthController
Handles user registration and authentication. It provides endpoints for login and user registration.

### 2. CustomerController
Manages customer data, allowing you to retrieve, update, and manage customer-related information.

### 3. AssetController
Provides endpoints to manage customer assets, such as checking asset size and usable size for a given customer.

### 4. OrderController
Manages orders (BUY/SELL) for assets. It supports creating, listing, and canceling orders.

### 5. AdminController
Designed for administrators to manage orders, including matching pending orders.

### 6. MoneyTransferController
Handles money transfers between customers and external IBANs. A key service in the system, providing APIs for money withdrawal.
```
## Prerequisites

Ensure you have the following installed:

- Java 17+
- Maven 3+
- A PostgreSQL or MySQL database (or use H2 for testing)

## Building and Running the Application

To build and run the application in production:

1. **Clone the Repository**

   ```bash
   git clone <repository-url>
   cd <repository-directory>
   ```

2. **Configure the Database**

   Update your `application.properties` or `application.yml` file with the correct database credentials.

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/your_database
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Build the Project**

   Use Maven to build the project:

   ```bash
   mvn clean install
   ```

4. **Run the Application**

   Run the application with the following command:

   ```bash
   mvn spring-boot:run
   ```

   Or, create a JAR file and run it directly:

   ```bash
   mvn clean package
   java -jar target/money-transfer-system-0.0.1-SNAPSHOT.jar
   ```

## Swagger API Documentation

The project integrates with **Swagger** for easy API testing. Once the application is running, you can access the Swagger UI at:

```
http://localhost:8080/swagger-ui.html
```

Through the Swagger UI, you can test all the API endpoints provided by the controllers.

## Example Endpoints

Here are some example endpoints for testing:

### Authentication

- **POST** `/auth/login`: Login a user.
- **POST** `/auth/register`: Register a new user.


### Asset Management

- **GET** `/assets/list`: List all assets of a customer.
- **POST** `/assets/deposit`: Deposit money into a customer's asset.
- **POST** `/assets/withdrawMoney`: Withdraw money from a customer's asset to IBAN.

### Order Management

- **POST** `/orders/create`: Create a new order (BUY/SELL).
- **GET** `/orders/list`: List orders for a customer.
- **DELETE** `/orders/delete`: Cancel a pending order.

### Admin Endpoints

- **POST** `/admin/orders/match/{orderId}`: Match a pending order.

### Money Transfer

- **POST** `/money-transfer/transfer`: Withdraw money from a customer's account to an IBAN.

## Production Deployment

For production deployment:

1. **Build the Project** using Maven to create the JAR file.

   ```bash
   mvn clean package
   ```

2. **Run the Application** on your production server:

   ```bash
   java -jar target/money-transfer-system-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
   ```

   Ensure that you have configured the `application-prod.yml` or `application-prod.properties` file with your production database settings.

## Testing

You can test the project using the following methods:

1. **Swagger UI**: Access the Swagger interface to test the API endpoints.
2. **Postman**: Use Postman to send HTTP requests to the API.
3. **Unit and Integration Tests**: The project includes unit and integration tests that can be run using:

   ```bash
   mvn test
   ```
