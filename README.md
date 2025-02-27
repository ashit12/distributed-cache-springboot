# 🚀 Distributed Cache System (Spring Boot + JOOQ)  
A **backend-only** distributed caching system implementing:  
✅ **Thread-safe LRU Cache** (Least Recently Used)  
✅ **Consistent Hashing** for load balancing across cache nodes  
✅ **Spring Boot REST API** for cache operations  
✅ **JOOQ with PostgreSQL** for persistent caching  
✅ **Cache-aside pattern** for efficient DB-cache synchronization  

## 🛠️ Tech Stack
- **Java 21** (Spring Boot 3)
- **JOOQ** (PostgreSQL integration)
- **Concurrent LRU Cache** (Thread-safe with `ReentrantLock`)
- **Consistent Hashing** (Virtual nodes for key distribution)
- **Spring Boot REST API** (Expose endpoints for interacting with cache)

---

## 📌 Features
- **Efficient in-memory caching** using LRU eviction policy.
- **Consistent Hashing** minimizes key remapping during node changes.
- **Database-backed** cache persistence via JOOQ and PostgreSQL.
- **Thread-safe operations** for concurrent access.
- **REST API** to interact with the cache.

---

## 📡 API Endpoints

| Method  | Endpoint | Description |
|---------|---------|-------------|
| `GET`   | `/cache/{key}` | Retrieve a value from cache |
| `POST`  | `/cache?key=...&value=...` | Insert or update cache entry |
| `DELETE` | `/cache/{key}` | Remove a specific key from cache |
| `DELETE` | `/cache` | Clear entire cache |


## 📌 Setup Instructions
Follow these steps to set up and run the **Distributed Cache System** on your local machine.

---

### **1️⃣ Clone the Repository**
First, clone the project from GitHub and navigate into the directory.

```bash
git clone https://github.com/yourusername/distributed-cache-springboot.git
cd distributed-cache-springboot
```

### **2️⃣ Install Dependencies**
Ensure you have Java 21, Maven, and PostgreSQL installed.

✅ Check Java & Maven Installation
Run the following commands to verify your installations:

```bash
java -version
mvn -version
```
You should see outputs like:

```
nginx
openjdk version "21" 2023-09-19
Apache Maven 3.9.0
```
If not, install Java 21 and Maven before proceeding.

### **3️⃣ Setup PostgreSQL Database**
Start your PostgreSQL server.
Create a new database named cache:
```sql
CREATE DATABASE cache;
```
Update the database credentials in the application.properties file:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/cache
spring.datasource.username=your_username
spring.datasource.password=your_password
```
Replace your_username and your_password with your PostgreSQL credentials.

### **4️⃣ Build and Run the Application**
Once everything is set up, build the project and start the Spring Boot application.

```bash
mvn clean install
mvn spring-boot:run
```
The application should start on http://localhost:8080.

### **5️⃣ Test the API**
Once the server is running, test it using cURL or Postman.

🔹 Insert a key-value pair:
```bash
curl -X POST "http://localhost:8080/cache?key=myKey&value=myValue"
```
🔹 Retrieve a value:
```bash
curl -X GET "http://localhost:8080/cache/myKey"
```
🔹 Delete a key:
```bash
curl -X DELETE "http://localhost:8080/cache/myKey"
```
🔹 Clear the entire cache:
```bash
curl -X DELETE "http://localhost:8080/cache"
```
### **6️⃣ Run Tests**
To ensure everything is working correctly, run the unit tests:

```bash
mvn test
```
