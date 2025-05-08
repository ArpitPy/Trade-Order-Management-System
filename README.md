# Trade Order Management System (TOMS)

A  production-grade Spring Boot application for managing trade orders.  
Built with clean code practices, proper architecture, validation, testing, and containerization-ready setup.

---

## 🚀 Features Implemented

- ✅ Create new trade orders (BUY/SELL)
- ✅ Fetch order by ID
- ✅ Update existing trade orders
- ✅ Cancel (soft delete) trade orders
- ✅ H2 in-memory database with H2 Console for easy viewing
- ✅ DTOs and input validation using Jakarta Validation
- ✅ Error handling with custom exception responses
- ✅ Unit and Integration Testing with JUnit and Mockito
- ✅ Enum-based order status handling (`NEW`, `FILLED`, `PARTIALLY_FILLED`, `CANCELLED`)

---

## 🧱 Tech Stack

- **Java 24**
- **Spring Boot 3**
- **Spring Data JPA**
- **H2 Database**
- **Lombok**
- **JUnit 5 + Mockito**
- **Postman** (for API testing)

---

## 📂 Project Structure

```bash
com.toms.toms
├── controller         # REST Controllers
├── dto               # DTOs for request/response
├── exception         # Custom exceptions and error handlers
├── model             # JPA entities and enums
├── repository        # Spring Data JPA Repositories
├── service           # Service layer (business logic)
├── test              # Unit and integration tests
```

---

## ⚙️ How to Run
### 🏁 Run the application
```
./mvnw spring-boot:run
```
### 🔗 H2 Console
1. Visit: http://localhost:8080/h2-console
2. JDBC URL: jdbc:h2:mem:testdb
3. User: sa, Password: (leave blank)

---

## 📬 API Endpoints

| Method | Endpoint            | Description          |
|--------|---------------------|----------------------|
| POST   | `/api/orders`       | Place a new order    |
| GET    | `/api/orders/{id}`  | Get order by ID      |
| PUT    | `/api/orders/{id}`  | Update an order      |
| DELETE | `/api/orders/{id}`  | Cancel (soft delete) |

## Example JSON (Place Order)
```
{
  "symbol": "AAPL",
  "type": "BUY",
  "quantity": 10,
  "price": 150.25
}
```

---

## 🧪 Testing
```
./mvnw test
```
- Unit tests for service logic
- Integration tests for controller endpoints using MockMvcf
