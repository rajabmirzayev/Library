# Library Management System

Spring Boot 4.1.0 ilə hazırlanmış kitabxana idarəetmə sistemi. CRUD əməliyyatları, səhifələmə, giriş validasiyası, Swagger/OpenAPI sənədləşdirilməsi və Postman test kolleksiyaları daxildir.

## Tech Stack

| Komponent | Versiya |
|-----------|---------|
| Java | 21 |
| Spring Boot | 4.1.0 |
| PostgreSQL | 17 |
| Hibernate | 7.4.1 |
| MapStruct | 1.6.3 |
| SpringDoc OpenAPI | 2.8.6 |
| Lombok | Latest |
| JUnit 5 + Mockito | Latest |

## prerequisites

- Java 21+
- Docker v20.10+ və Docker Compose v2+
- (opsional) Gradle 9.5+

## Tez Başlanğıc

### 1. Clone

```bash
git clone https://github.com/rajabmirzayev/Library.git
cd Library
```

### 2. .env faylı yaradın

```bash
cp .env.example .env
```

### 3. Docker ilə işə salın

```bash
docker compose up -d --build
```

Bu əmr:
- PostgreSQL verilənlər bazasını Docker container-daaldırır
- Spring Boot tətbiqini build edib işə salır
- Baza schemanı avtomatik yaradır (`ddl-auto: update`)

> Daha sonrakı həftələrdə bunu Liquibase ilə əvəzləyəcəm

### 4. Tətbiqi yoxlayın

| Xidmət | URL |
|--------|-----|
| API | http://localhost:8080 |
| Swagger UI | http://localhost:8080/swagger-ui/index.html |
| PostgreSQL | localhost:5432 |

### Dayandırmaq

```bash
docker compose down
```

Baza məlumatlarını silmədən dayandırmaq üçün:

```bash
docker compose down -v  # diqqət: bu baza məlumatlarını silir
```

## Yerli İnkişaf (Docker olmadan)

PostgreSQL-i yerli quraşdırdıqdan sonra:

```bash
# .env.example-dən .env yaradın
cp .env.example .env

# Tətbiqi çalışdırın
./gradlew bootRun
```

## Environment Variables

| Dəyişən | Təsvir | Default |
|---------|--------|---------|
| `APP_PORT` | Server portu | `8080` |
| `SPRING_PROFILES_ACTIVE` | Aktiv Spring profili | `default` |
| `POSTGRES_DB` | Baza adı | `library` |
| `POSTGRES_USER` | PostgreSQL istifadəçi adı | `postgres` |
| `POSTGRES_PASSWORD` | PostgreSQL şifrəsi | `postgres` |
| `DB_HOST` | Baza hostu | `localhost` |
| `DB_PORT` | Baza portu | `5432` |
| `DB_USERNAME` | Tətbiq istifadəçisi | `postgres` |
| `DB_PASSWORD` | Tətbiq şifrəsi | `postgres` |
| `JPA_DDL_AUTO` | Hibernate schema rejimi | `update` |

## API Endpoint-lər

Bütün endpoint-lər `/api/v1` prefiksi ilə başlayır.

| Resurs | Endpoint | Əməliyyatlar |
|--------|----------|-------------|
| Müəlliflər | `/authors` | CRUD |
| Nəşriyyatlar | `/publishers` | CRUD |
| Kategoriyalar | `/categories` | CRUD |
| Üzvlər | `/members` | CRUD |
| Kitablar | `/books` | CRUD |
| Kitab Nüsxələri | `/book-copies` | CRUD |
| Borclanmalar | `/loans` | CRUD |
| Cərimələr | `/fines` | CRUD |
| Rezervasiyalar | `/reservations` | CRUD |

## Layihə Strukturu

```
src/main/java/az/library/library/
├── config/           # OpenAPI konfiqurasiyası
├── controller/       # REST controller-lər
├── dto/
│   ├── request/      # Create/Update request DTO-ları
│   └── response/     # Response DTO-ları + PageResponse
├── entity/           # JPA entity-ləri
├── enums/            # Status və tip enumları
├── exception/        # ResourceNotFoundException, GlobalExceptionHandler
├── mapper/           # MapStruct mapper interfeysləri
├── repository/       # Spring Data JPA repository-ləri
└── service/
    ├── impl/         # Service implementasiyaları
    └── *.java        # Service interfeysləri

src/test/java/az/library/library/
└── service/impl/     # JUnit 5 + Mockito testləri (100 test)
```

## Testlər

```bash
# Bütün testləri çalışdırın
./gradlew test

# Yalnız service testləri
./gradlew test --tests "az.library.library.service.impl.*"
```

## Postman Kolleksiyaları

`postman/` qovluğunda iki kolleksiya var:

| Kolleksiya | Təsvir |
|-----------|--------|
| `Library-API.postman_collection.json` | Əsas CRUD əməliyyatları (45 request) |
| `Library-KPI-Collection.postman_collection.json` | KPI testləri: validasiya, 404, pagination, response time (74 request) |
| `Library-Local.postman_environment.json` | Yerli development environment dəyişənləri |

**İstifadə:**
1. Postman-i açın
2. `Library-Local.postman_environment.json` import edin
3. Kolleksiyanı import edin
4. Health Check qovluğunu çalışdırın
5. Sonra CRUD endpoint-lərini test edin


