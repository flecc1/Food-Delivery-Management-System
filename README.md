# 🍴 Food Delivery Management System

<p align="center">
  <img src="https://img.shields.io/badge/Java-25-orange?style=for-the-badge&logo=openjdk" />
  <img src="https://img.shields.io/badge/Spring_Boot-4.0.2-brightgreen?style=for-the-badge&logo=springboot" />
  <img src="https://img.shields.io/badge/PostgreSQL-15-blue?style=for-the-badge&logo=postgresql" />
  <img src="https://img.shields.io/badge/Docker-ready-2496ED?style=for-the-badge&logo=docker" />
  <img src="https://img.shields.io/badge/CI%2FCD-GitHub_Actions-black?style=for-the-badge&logo=githubactions" />
  <img src="https://img.shields.io/badge/Deployed-Railway-8B5CF6?style=for-the-badge&logo=railway" />
</p>

<p align="center">
  REST API платформа для управления ресторанами, меню, категориями, клиентами и заказами — от каталогизации до доставки.
</p>

---

## 📋 Содержание

- [О проекте](#-о-проекте)
- [Технологический стек](#-технологический-стек)
- [Архитектура](#-архитектура)
- [API](#-api)
- [Быстрый старт](#-быстрый-старт)
- [Переменные окружения](#-переменные-окружения)
- [CI/CD](#-cicd)
- [Структура проекта](#-структура-проекта)

---

## 🎯 О проекте

**Food Delivery Management System** — это масштабируемый REST API-бэкенд для агрегации и управления данными в сфере доставки еды. Система предоставляет полноценный инструментарий для:

- каталогизации ресторанов, меню и блюд по категориям
- управления клиентами и их заказами
- структурированной выдачи данных через REST-интерфейс с валидацией

Проект спроектирован как фундамент для построения агрегаторов доставки, рекомендательных сервисов и систем бронирования.

---

## 🛠 Технологический стек

| Слой | Технология |
|---|---|
| Язык | Java 25 |
| Фреймворк | Spring Boot 4.0.2 |
| База данных | PostgreSQL 15 |
| ORM | Spring Data JPA / Hibernate |
| Валидация | Spring Validation |
| Документация | SpringDoc OpenAPI (Swagger UI) |
| Сборка | Gradle 9 |
| Контейнеризация | Docker, Docker Compose |
| CI/CD | GitHub Actions |
| Деплой | Railway |
| Генерация кода | Lombok |

---

## 🏗 Архитектура

Приложение построено на классической многослойной архитектуре:

```
┌─────────────────────────────────────┐
│           REST Controller           │  ← Принимает HTTP-запросы
├─────────────────────────────────────┤
│              Service                │  ← Бизнес-логика
├─────────────────────────────────────┤
│            Repository               │  ← Доступ к данным (JPA)
├─────────────────────────────────────┤
│            PostgreSQL               │  ← Хранилище
└─────────────────────────────────────┘
```

**Ключевые паттерны:**
- **DTO** — разграничение внутренней модели и внешнего интерфейса
- **Mapper** — чистая конвертация между Entity и DTO без дублирования
- **Optional** — безопасная обработка пустых результатов в сервисном слое

**Основные сущности:**

```
Restaurant ──── Menu ──── Dish
    │                      │
    │                   Category
    │
Customer ──── Order
```

---

## 📡 API

Полная документация доступна через Swagger UI после запуска:

```
http://localhost:8080/swagger-ui.html
```

| Метод | Эндпоинт | Описание |
|---|---|---|
| GET | `/api/restaurants` | Список всех ресторанов |
| GET | `/api/restaurants/{id}` | Ресторан по ID |
| POST | `/api/restaurants` | Создать ресторан |
| PUT | `/api/restaurants/{id}` | Обновить ресторан |
| DELETE | `/api/restaurants/{id}` | Удалить ресторан |
| GET | `/api/menus` | Список меню |
| GET | `/api/dishes` | Список блюд |
| GET | `/api/categories` | Список категорий |
| GET | `/api/customers` | Список клиентов |
| GET | `/api/orders` | Список заказов |

---

## 🚀 Быстрый старт

### Требования

- Docker & Docker Compose
- JDK 25 (для локальной разработки без Docker)

### Запуск через Docker Compose

```bash
# 1. Клонировать репозиторий
git clone https://github.com/flecc1/Food-Delivery-Management-System.git
cd Food-Delivery-Management-System

# 2. Создать файл с переменными окружения
cp .env.example .env
# Заполнить .env своими значениями

# 3. Запустить
docker compose -f docker-compose2.yaml up -d
```

Приложение будет доступно на `http://localhost:8080`

### Локальная разработка

```bash
# Запустить только базу данных
docker compose -f docker-compose2.yaml up db -d

# Запустить приложение
./gradlew bootRun
```

### Запуск тестов

```bash
./gradlew test
```

---

## 🔐 Переменные окружения

Создай файл `.env` на основе `.env.example`:

```env
POSTGRES_DB=food_delivery
POSTGRES_USER=your_user
POSTGRES_PASSWORD=your_password
```

> ⚠️ Никогда не коммить `.env` в репозиторий — он добавлен в `.gitignore`

---

## ⚙️ CI/CD

Проект использует GitHub Actions для автоматической сборки и деплоя.

```
push to main
     │
     ▼
┌─────────────┐
│  Run Tests  │  ← Gradle + H2 in-memory DB
└──────┬──────┘
       │
       ▼
┌──────────────────────────────┐
│   Docker Build & Push        │  ← backend + frontend → Docker Hub
│   (параллельно, матрица)     │
└──────────────────────────────┘
       │
       ▼
┌─────────────────┐
│  Auto-deploy    │  ← Railway следит за Docker Hub и деплоит автоматически
│  on Railway     │
└─────────────────┘
```

**Образы на Docker Hub:**
- `fleccy/backend:latest`
- `fleccy/frontend:latest`

---

## 📁 Структура проекта

```
Food-Delivery-Management-System/
├── src/
│   ├── main/
│   │   ├── java/com/example/fooddelivery/
│   │   │   ├── controller/     # REST контроллеры
│   │   │   ├── service/        # Бизнес-логика
│   │   │   ├── repository/     # JPA репозитории
│   │   │   ├── entity/         # JPA сущности
│   │   │   ├── dto/            # Data Transfer Objects
│   │   │   └── mapper/         # Entity ↔ DTO маппинг
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/               # Тесты
├── src/main/java/com/example/fooddelivery/food-delivery-spa/  # Frontend (Vue/React)
├── .github/workflows/
│   └── workflow.yml            # CI/CD pipeline
├── Dockerfile                  # Backend образ
├── docker-compose2.yaml        # Локальный запуск
├── build.gradle
├── .env.example                # Шаблон переменных окружения
└── init_db.sql                 # Инициализация БД
```

---

## 👤 Автор

**flecc1** — [GitHub](https://github.com/flecc1)

## 🌐 Ссылка на Sonar:
https://sonarcloud.io/project/issues?issueStatuses=OPEN%2CCONFIRMED&id=flecc1_Food-Delivery-Management-System
