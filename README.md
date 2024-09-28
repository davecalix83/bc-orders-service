# Orders Service

## Description
This microservice handles the management of orders in the system, allowing clients to create, retrieve, update, and delete orders.

## Features
- Create a new order
- Retrieve order details by ID
- Update existing orders
- Delete orders
- List all orders

## API Endpoints

| Method | Endpoint              | Description                      |
|--------|-----------------------|----------------------------------|
| POST   | `/api/v1/orders`      | Create a new order               |
| GET    | `/api/v1/orders/{id}` | Retrieve order details by ID     |
| PUT    | `/api/v1/orders/{id}` | Update an existing order         |
| DELETE | `/api/v1/orders/{id}` | Delete an order                  |
| GET    | `/api/v1/orders`      | Retrieve all orders              |

## Models

- **OrderRequestDto**: Represents the order creation request.
- **OrderResponseDto**: Represents the response for an order.

## Technologies Used
- Spring Boot
- Java
- JPA/Hibernate
- Swagger/OpenAPI

## Installation
1. Clone the repository.
2. Run `mvn clean install` to build the project.
3. Configure your application properties.
4. Run the application using your IDE or with `mvn spring-boot:run`.

## License
This project is licensed under the License Apache 2.0.
