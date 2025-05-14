# Subscription-Service

**REST-сервис на Spring Boot 3 + PostgreSQL**

Сервис для управления пользователями и их подписками. Поддерживает добавление, получение, обновление и удаление пользователей, управление подписками, а также выдачу топ-N популярных сервисов.

---

## 📋 Содержание

* [Требования](#-требования)
* [Быстрый старт](#-быстрый-старт)

    * [Локальный запуск (без Docker)](#локальный-запуск--без-docker)
    * [Запуск через Docker + docker-compose](#запуск-через-docker--docker-compose)
* [Структура проекта](#-структура-проекта)
* [Документация API](#-документация-api)
* [Примеры запросов (cURL)](#-примеры-запросов-curl)
* [Проверка работы](#-проверка-работы)
* [Управление контейнерами](#-управление-контейнерами)

---

## ⚙️ Требования

* Java 17+ (OpenJDK 17 или новее)
* Maven 3.6+
* PostgreSQL 14+

> **По умолчанию** приложение запускается на порту `8086` и подключается к локальной БД Postgres.

---

## 🚀 Быстрый старт

### Локальный запуск (без Docker)

1. Перейдите в корень проекта:

   ```bash
   cd subscription-service
   ```
2. Запустите приложение с помощью Maven Wrapper:

   ```bash
   ./mvnw spring-boot:run
   ```

   или соберите и запустите jar-файл:

   ```bash
   mvn clean package
   java -jar target/*.jar
   ```
3. Приложение доступно по адресу: `http://localhost:8086`

### Запуск через Docker + docker-compose

> Собирает образ приложения и поднимает сервис вместе с базой данных.

1. Выполните в корне проекта:

   ```bash
   docker compose up --build -d
   ```
2. Для просмотра логов приложения:

   ```bash
   docker compose logs -f app
   ```
3. API также будет доступно на `http://localhost:8086`.

---

## 📂 Структура проекта

```
subscription-service/
├── Dockerfile          # Сборка образа приложения
├── docker-compose.yml  # Конфигурация для app + Postgres
├── src/                # Исходный код приложения
│   ├── main/
│   └── test/
└── pom.xml             # Maven конфигурация
```

---

## 📑 Документация API

| Метод  | Путь                                                | Описание                                           |
| ------ | --------------------------------------------------- | -------------------------------------------------- |
| POST   | `/api/v1/users`                                     | Создать нового пользователя                        |
| GET    | `/api/v1/users/{id}`                                | Получить данные пользователя по UUID               |
| PUT    | `/api/v1/users/{id}`                                | Обновить данные пользователя                       |
| DELETE | `/api/v1/users/{id}`                                | Удалить пользователя                               |
| POST   | `/api/v1/users/{id}/subscriptions`                  | Добавить подписку для пользователя                 |
| GET    | `/api/v1/users/{id}/subscriptions`                  | Получить список подписок пользователя              |
| DELETE | `/api/v1/users/{id}/subscriptions/{subscriptionId}` | Удалить конкретную подписку                        |
| GET    | `/api/v1/subscriptions/top?limit={N}`               | Получить топ-N популярных сервисов (по количеству) |

> Замените `{id}`, `{subscriptionId}` и `{N}` на реальные значения.

---

## 💻 Примеры запросов (cURL)

1. **Создать пользователя**

   ```bash
   curl -X POST http://localhost:8086/api/v1/users \
        -H "Content-Type: application/json" \
        -d '{ "name": "Ivan", "email": "ivan@mail.com" }'
   ```

2. **Получить пользователя**

   ```bash
   curl http://localhost:8086/api/v1/users/<USER_ID>
   ```

3. **Обновить пользователя**

   ```bash
   curl -X PUT http://localhost:8086/api/v1/users/<USER_ID> \
        -H "Content-Type: application/json" \
        -d '{ "name": "Ivan P. Petrov", "email": "ivan.p@mail.com" }'
   ```

4. **Удалить пользователя**

   ```bash
   curl -X DELETE http://localhost:8086/api/v1/users/<USER_ID>
   ```

5. **Добавить подписку**

   ```bash
   curl -X POST http://localhost:8086/api/v1/users/<USER_ID>/subscriptions \
        -H "Content-Type: application/json" \
        -d '{ "serviceName": "Netflix" }'
   ```

6. **Список подписок пользователя**

   ```bash
   curl http://localhost:8086/api/v1/users/<USER_ID>/subscriptions
   ```

7. **Удалить подписку**

   ```bash
   curl -X DELETE http://localhost:8086/api/v1/users/<USER_ID>/subscriptions/<SUB_ID>
   ```

8. **Получить топ-3 популярных сервисов**

   ```bash
   curl http://localhost:8086/api/v1/subscriptions/top?limit=3
   ```

---

## 🐳 Управление контейнерами

```bash
# Просмотр статуса контейнеров
docker compose ps

# Просмотр логов приложения
docker compose logs -f app

# Просмотр логов базы данных
docker compose logs -f db

# Остановить все контейнеры
docker compose down

# Остановить контейнеры и удалить том с данными
docker compose down -v
```

---

