# Company Person Task API

REST API на `Spring Boot` і `Neo4j` для роботи з компаніями, персонами та завданнями.

## Stack

- `Java 21`
- `Spring Boot 3`
- `Spring Data Neo4j`
- `Neo4j`
- `Maven`

## Project structure

Файл `compose.yaml` знаходиться в корені проєкту:

- `compose.yaml`

## How to run Neo4j

1. Перейти в корінь проєкту:

```bash
cd company-person-task-api
```

2. Запустити `Neo4j`:

```bash
docker compose up -d
```

3. Перевірити, що контейнер запустився:

```bash
docker compose ps
```

4. Відкрити `Neo4j Browser`:

- [http://localhost:7474](http://localhost:7474)

Параметри входу за замовчуванням:

- `username`: `neo4j`
- `password`: `qwerty123`

## How to run the application

Після запуску `Neo4j` можна підняти застосунок:

```bash
mvn spring-boot:run
```

Застосунок стартує за адресами:

- [http://localhost:8080](http://localhost:8080)
- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- OpenAPI JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## How to load seed data

Після запуску `Neo4j` можна завантажити тестові дані:

```bash
docker exec -i company-person-task-neo4j cypher-shell -u neo4j -p qwerty123 < ./seeds/seed.cypher
```

Скрипт створює:

- `100` компаній
- `100` персон
- `100` завдань
- зв'язки `Company -> Person` з атрибутом `personPosition`
- зв'язки `Person -> Task` з атрибутом `participationType`

## API endpoints

### Companies

- `GET /companies`
- `POST /companies`
- `GET /companies/{id}`
- `PUT /companies/{id}`
- `DELETE /companies/{id}`
- `GET /companies/{companyId}/hierarchy`
- `POST /companies/{companyId}/persons/{personId}`
- `PUT /companies/{companyId}/persons/{personId}`
- `DELETE /companies/{companyId}/persons/{personId}`

### Persons

- `GET /persons`
- `POST /persons`
- `GET /persons/{id}`
- `PUT /persons/{id}`
- `DELETE /persons/{id}`
- `POST /persons/{personId}/tasks/{taskId}`
- `PUT /persons/{personId}/tasks/{taskId}`
- `DELETE /persons/{personId}/tasks/{taskId}`

### Tasks

- `GET /tasks`
- `POST /tasks`
- `GET /tasks/{id}`
- `PUT /tasks/{id}`
- `DELETE /tasks/{id}`

### Analytics

- `GET /analytics/tasks/top-multi-user`
- `GET /analytics/persons/top-busy`

## Query parameters

### Pagination and sorting

Для списків підтримуються:

- `page`
- `size`
- `sortBy`
- `sortDir=asc|desc`

Приклад:

```bash
curl "http://localhost:8080/companies?page=0&size=10&sortBy=name&sortDir=asc"
```

### Hierarchy filters

Для `GET /companies/{companyId}/hierarchy` підтримуються:

- `taskIds`
- `personPosition`
- `participationType`

Приклад:

```bash
curl "http://localhost:8080/companies/company-1/hierarchy?personPosition=CTO&participationType=Owner"
```

### Analytics filters

Для аналітичних endpoint’ів підтримуються:

- `limit`
- `participationType`

Приклад:

```bash
curl "http://localhost:8080/analytics/tasks/top-multi-user?limit=10"
```

## API examples

### Create company

```bash
curl -X POST "http://localhost:8080/companies" \
  -H "Content-Type: application/json" \
  -d '{"name":"Acme"}'
```

Приклад відповіді:

```json
{
  "id": "f3e16cf6-c53a-4ceb-8d29-1c75a5d8a5fa",
  "name": "Acme"
}
```

### Create person

```bash
curl -X POST "http://localhost:8080/persons" \
  -H "Content-Type: application/json" \
  -d '{"firstName":"John","lastName":"Smith"}'
```

Приклад відповіді:

```json
{
  "id": "4f2c8ed2-d2f9-41f6-8543-fb8fd8314b55",
  "firstName": "John",
  "lastName": "Smith"
}
```

### Create task

```bash
curl -X POST "http://localhost:8080/tasks" \
  -H "Content-Type: application/json" \
  -d '{"title":"Launch MVP","description":"Initial release"}'
```

Приклад відповіді:

```json
{
  "id": "0aa6610c-c668-4292-aa96-d79101d5365b",
  "title": "Launch MVP",
  "description": "Initial release"
}
```

### Create company-person relationship

```bash
curl -X POST "http://localhost:8080/companies/{companyId}/persons/{personId}" \
  -H "Content-Type: application/json" \
  -d '{"personPosition":"Manager"}'
```

Приклад відповіді:

```json
{
  "sourceId": "company-1",
  "targetId": "person-1",
  "attributeName": "personPosition",
  "attributeValue": "Manager"
}
```

### Create person-task relationship

```bash
curl -X POST "http://localhost:8080/persons/{personId}/tasks/{taskId}" \
  -H "Content-Type: application/json" \
  -d '{"participationType":"Owner"}'
```

Приклад відповіді:

```json
{
  "sourceId": "person-1",
  "targetId": "task-1",
  "attributeName": "participationType",
  "attributeValue": "Owner"
}
```

### Get companies list

```bash
curl "http://localhost:8080/companies?page=0&size=3&sortBy=name&sortDir=asc"
```

Приклад відповіді:

```json
{
  "content": [
    {
      "id": "company-1",
      "name": "Company 1"
    },
    {
      "id": "company-10",
      "name": "Company 10"
    },
    {
      "id": "company-100",
      "name": "Company 100"
    }
  ],
  "page": 0,
  "size": 3,
  "totalElements": 100,
  "totalPages": 34,
  "first": true,
  "last": false
}
```

### Get company hierarchy

```bash
curl "http://localhost:8080/companies/company-1/hierarchy?personPosition=CTO&participationType=Owner"
```

Приклад відповіді:

```json
{
  "companyId": "company-1",
  "companyName": "Company 1",
  "persons": [
    {
      "personId": "person-1",
      "firstName": "Person1",
      "lastName": "User1",
      "personPosition": "CTO",
      "tasks": [
        {
          "taskId": "task-55",
          "title": "Task 55",
          "description": "Seeded task #55",
          "participationType": "Owner"
        }
      ]
    }
  ]
}
```

### Get top multi-user tasks

```bash
curl "http://localhost:8080/analytics/tasks/top-multi-user?limit=3"
```

Пример ответа:

```json
[
  {
    "taskId": "task-1",
    "title": "Task 1",
    "personCount": 8
  },
  {
    "taskId": "task-10",
    "title": "Task 10",
    "personCount": 8
  },
  {
    "taskId": "task-100",
    "title": "Task 100",
    "personCount": 8
  }
]
```

### Get top busy persons

```bash
curl "http://localhost:8080/analytics/persons/top-busy?limit=3"
```

Пример ответа:

```json
[
  {
    "personId": "person-1",
    "firstName": "Person1",
    "lastName": "User1",
    "taskCount": 8
  },
  {
    "personId": "person-10",
    "firstName": "Person10",
    "lastName": "User10",
    "taskCount": 8
  },
  {
    "personId": "person-100",
    "firstName": "Person100",
    "lastName": "User100",
    "taskCount": 8
  }
]
```

## How to stop Neo4j

```bash
docker compose down
```

Якщо потрібно видалити також том з даними:

```bash
docker compose down -v
```
