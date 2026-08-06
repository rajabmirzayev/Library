# Library Management System

Spring Boot 4.1.0 ilə hazırlanmış kitabxana idarəetmə sistemi. JPA Specification API ilə dinamik axtarış, Liquibase migration, `rollbackFor` ilə atomik tranzaksiyalar, JOIN FETCH / @EntityGraph ilə N+1 optimizasiyası, native SQL hesabatlar, JWT əsaslı RBAC, inteqrasiya testləri və Postman kolleksiyaları daxildir.

## Tech Stack

| Komponent | Versiya |
|-----------|---------|
| Java | 21 |
| Spring Boot | 4.1.0 |
| PostgreSQL | 17 |
| Hibernate | 7.4.1 |
| Liquibase | 5.0.3 |
| MapStruct | 1.6.3 |
| Spring Security + JWT (jjwt) | 0.12.6 |
| SpringDoc OpenAPI | 2.8.6 |
| H2 (test) | Latest |
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
- PostgreSQL verilənlər bazasını Docker container-da qaldırır
- Spring Boot tətbiqini build edib işə salır
- Liquibase ilə bütün migration-ları tətbiq edir (`ddl-auto: validate`)
- `admin` / `admin123` istifadəçisi avtomatik seed olunur (ROLE_ADMIN)

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
| `JPA_DDL_AUTO` | Hibernate schema rejimi | `validate` |
| `JWT_SECRET` | JWT imza açarı (base64) | *(tələb olunur)* |
| `JWT_EXPIRATION_MS` | JWT müddəti (ms) | `86400000` |
| `LIQUIBASE_ENABLED` | Liquibase aktiv/passiv | `true` |

## API Endpoint-lər

Bütün endpoint-lər `/api/v1` prefiksi ilə başlayır.

| Resurs | Endpoint | Əməliyyatlar |
|--------|----------|-------------|
| Auth | `/auth/register`, `/auth/login` | Register, Login |
| Müəlliflər | `/authors` | CRUD |
| Nəşriyyatlar | `/publishers` | CRUD |
| Kategoriyalar | `/categories` | CRUD |
| Üzvlər | `/members` | CRUD |
| Kitablar | `/books`, `/books/search` | CRUD + Dinamik axtarış |
| Kitab Nüsxələri | `/book-copies` | CRUD |
| Borclanmalar | `/loans` | CRUD |
| Cərimələr | `/fines` | CRUD |
| Rezervasiyalar | `/reservations` | CRUD |
| Hesabatlar | `/reports/overview`, `/reports/most-borrowed-books` | Admin analitika (ROLE_ADMIN) |

## Layihə Strukturu

```
src/main/java/az/library/library/
├── config/           # Liquibase BeanPostProcessor, OpenAPI konfiqurasiyası
├── controller/       # REST controller-lər
├── dto/
│   ├── request/      # Create/Update/Search request DTO-ları
│   └── response/     # Response DTO-ları + PageResponse + ApiResponse
├── entity/           # JPA entity-ləri
├── enums/            # Status və tip enumları
├── exception/        # ResourceNotFoundException, GlobalExceptionHandler
├── mapper/           # MapStruct mapper interfeysləri
├── repository/
│   └── specification/ # JPA Specification (BookSpecification)
├── security/         # JWT filter, JwtService, SecurityConfig
├── service/
│   ├── impl/         # Service implementasiyaları
│   └── *.java        # Service interfeysləri
└── utils/            # Köməkçi util siniflər

src/test/java/az/library/library/
├── service/impl/     # Mockito unit testləri + inteqrasiya testi
└── LibraryApplicationTests.java
```

## Testlər

```bash
# Bütün testləri çalışdırın
./gradlew test

# Yalnız inteqrasiya testləri
./gradlew test --tests "*TransactionRollbackIntegrationTest"

# Yalnız unit testlər
./gradlew test --tests "az.library.library.service.impl.*" --exclude-task test
```

**108 test** (101 unit + 7 inteqrasiya). İnteqrasiya testləri H2 (PostgreSQL mode) bazasında işləyir — Docker/PostgreSQL tələb olunmur.

## Postman Kolleksiyası

| Fayl | Təsvir |
|------|--------|
| `Library-API.postman_collection.json` | Əsas CRUD + Search Books + Hesabatlar + Auth + Admin (50+ request) |
| `Library-Environment.postman_environment.json` | Environment dəyişənləri (base_url, token, admin credentials) |

**İstifadə:**
1. Postman-i açın
2. `Library-Environment.postman_environment.json` import edin (environment seçin)
3. `Library-API.postman_collection.json` import edin
4. Auth → Register → Login (token avtomatik `token` env dəyişəninə yazılır)
5. Hesabatlar üçün: Hesabatlar → Login Admin → `admin_token` avtomatik yazılır


