# 📦 API Purchase Order  
**Spring Boot 3 · Java 17 · MySQL · Docker**

Microservicio RESTful para la gestión de órdenes de compra con filtros avanzados combinables mediante *query parameters*.  
Implementado siguiendo principios REST y desplegado con Docker **sin Docker Compose**.

---

## 🚀 Tecnologías

- Java 17  
- Spring Boot 3  
- Spring Data JPA (Specifications)  
- MySQL 8  
- Docker  

---

## 📂 Entidad: `PurchaseOrder`

| Campo | Tipo |
|------|------|
| id | Long |
| orderNumber | String (único) |
| supplierName | String |
| status | Enum |
| totalAmount | BigDecimal |
| currency | Enum |
| createdAt | LocalDateTime |
| expectedDeliveryDate | LocalDate |

---

## 🌐 Endpoints

### ➕ Crear orden
**POST**
```
/api/v1/purchase-orders
```

Body:
```json
{
  "orderNumber": "PO-2025-000001",
  "supplierName": "ACME Corp",
  "status": "DRAFT",
  "totalAmount": 150.75,
  "currency": "USD",
  "expectedDeliveryDate": "2025-07-20"
}
```

---

### 📄 Listar órdenes con filtros
**GET**
```
/api/v1/purchase-orders
```

Filtros opcionales:
- `q`
- `status`
- `currency`
- `minTotal`
- `maxTotal`
- `from`
- `to`

Ejemplo:
```
/api/v1/purchase-orders?q=acme&status=APPROVED&currency=USD&minTotal=100
```

---

## ⚙️ Configuración

### `application.properties`
```properties
spring.application.name=PurchaseOrder
server.port=${SERVER_PORT:8001}

spring.datasource.url=jdbc:mysql://${DB_HOST:localhost}:${DB_PORT:3306}/${DB_NAME:sisdb2025}
spring.datasource.username=${DB_USER:AppRoot}
spring.datasource.password=${DB_PASSWORD:abcd}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=${JPA_DDL_AUTO:update}
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

logging.level.org.springframework.web=${LOG_WEB_LEVEL:DEBUG}
logging.level.org.hibernate.type.descriptor.sql.BasicBinder=${LOG_SQL_LEVEL:trace}
```

---

## 🔐 Variables de entorno

### `.env` (NO se sube a GitHub)
```env
SERVER_PORT=8001

DB_HOST=mysql-purchase
DB_PORT=3306
DB_NAME=sisdb2025
DB_USER=AppRoot
DB_PASSWORD=abcd

JPA_DDL_AUTO=update

LOG_WEB_LEVEL=DEBUG
LOG_SQL_LEVEL=trace
```

Agregar a `.gitignore`:
```
.env
target/
```

---

## 🐳 Docker

### 1️⃣ Compilar el JAR
```powershell
./mvnw clean package -DskipTests
```

---

### 2️⃣ Construir imagen Docker
```powershell
docker build -t kacortez/api-purchase-order .
```

---

### 3️⃣ Crear red Docker (solo una vez)
```powershell
docker network create po-net
```

---

### 4️⃣ Ejecutar MySQL (SIN exponer puertos)
```powershell
docker run -d `
  --name mysql-purchase `
  --network po-net `
  -e MYSQL_DATABASE=sisdb2025 `
  -e MYSQL_USER=AppRoot `
  -e MYSQL_PASSWORD=abcd `
  -e MYSQL_ROOT_PASSWORD=root `
  mysql:8.0
```

Verificar:
```powershell
docker logs mysql-purchase
```

---

### 5️⃣ Ejecutar la API (usando `.env`)
```powershell
docker run -d `
  --name api-purchase-order `
  --network po-net `
  --env-file .env `
  -p 8001:8001 `
  kacortez/api-purchase-order:latest
```

---

## 🔖 Publicar imagen en Docker Hub

### Login
```powershell
docker login
```

### Tag
```powershell
docker tag kacortez/api-purchase-order kacortez/api-purchase-order:1.0.0
docker tag kacortez/api-purchase-order kacortez/api-purchase-order:latest
```

### Push
```powershell
docker push kacortez/api-purchase-order:1.0.0
docker push kacortez/api-purchase-order:latest
```

---

## 🌍 URL Base
```
http://localhost:8001/api/v1/purchase-orders
```

---

## 📦 Docker Hub
```
kacortez/api-purchase-order
```

---

## 🧠 Notas importantes

- MySQL usa **3306 interno**, no se expone al host.
- La API se conecta a MySQL usando el nombre del contenedor (`mysql-purchase`).
- Todos los filtros se combinan con lógica **AND**.
- `createdAt` es generado automáticamente por el backend.
- No se usa Docker Compose (requisito del proyecto).
