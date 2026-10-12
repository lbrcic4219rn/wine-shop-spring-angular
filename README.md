# Fondrum

A small wine shop: browse wines, filter them by style and variety, and place an order.

- `backend/` — Spring Boot 3.4 REST API (Java 17, JPA, PostgreSQL)
- `frontend/` — Angular 19 single-page app (Angular Material)

## Running locally

Requirements: JDK 17+, Node 22+, Docker.

1. Start the database (published on host port 5434):

   ```bash
   docker compose up -d
   ```

2. Create the backend configuration and start the API on http://localhost:8080:

   ```bash
   cd backend
   cp src/main/resources/application.properties.example src/main/resources/application.properties
   ./mvnw spring-boot:run
   ```

   With the `dev` profile (the default in the example file) the first start seeds 8 varieties, 3 styles, 48 wines and
   one sample order.

3. Start the frontend on http://localhost:4200:

   ```bash
   cd frontend
   npm install
   npm start
   ```

## Configuration

`application.properties` is gitignored. Every value in the example file can be overridden with an environment variable:

| Variable                      | Default                                    | Purpose                                         |
|-------------------------------|--------------------------------------------|-------------------------------------------------|
| `DB_URL`                      | `jdbc:postgresql://localhost:5434/fondrum` | JDBC URL                                        |
| `DB_USERNAME` / `DB_PASSWORD` | `fondrum` / `fondrum`                      | Database credentials                            |
| `SPRING_PROFILES_ACTIVE`      | `dev`                                      | `dev` enables the sample-data seed              |
| `CORS_ALLOWED_ORIGINS`        | `http://localhost:4200`                    | Comma-separated origins allowed to call the API |
| `DB_PORT` (Docker Compose)    | `5434`                                     | Host port for the Postgres container            |

The frontend reads the API base URL from `frontend/src/environments/`: `http://localhost:8080` in development, `/api` in
production builds (backend expected behind a reverse proxy on the same origin).

## API

| Method | Path                                                             | Description                                         |
|--------|------------------------------------------------------------------|-----------------------------------------------------|
| `GET`  | `/wine-style`                                                    | All wine styles                                     |
| `GET`  | `/wine-variety`                                                  | All wine varieties                                  |
| `GET`  | `/wine?styleIds=1,2&varietyIds=3&page=0&size=12&sort=price,desc` | Paged wine search; all parameters optional          |
| `POST` | `/order`                                                         | Place an order; returns `201` with the stored order |
| `GET`  | `/order?page=0&size=20`                                          | Paged orders, newest first                          |
| `GET`  | `/order/{id}`                                                    | One order                                           |
| `GET`  | `/images/{file}`                                                 | Wine images                                         |

Order request:

```json
{
  "customerName": "Ana",
  "customerEmail": "ana@example.com",
  "items": [{"wineId": 1, "quantity": 2}]
}
```

Errors are returned as RFC 9457 problem details: `400` for invalid input (with a per-field `errors` map), `404` for an
unknown wine or order.

The API has no authentication; every endpoint, including the order list with customer names and emails, is open to
anyone who can reach it.

## Tests

```bash
cd backend && ./mvnw verify
```

```bash
cd frontend && npm run test:ci
```

Backend tests run against in-memory H2 and need no database.
