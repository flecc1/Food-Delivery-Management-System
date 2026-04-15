# FoodFlow — SPA клиент для Food Delivery API

## Установка и запуск

### 1. Установите зависимости
```bash
npm install
```

### 2. Запустите проект
```bash
npm run dev
```

Откройте браузер по адресу: **http://localhost:5173**

> ⚠️ Убедитесь, что ваш Spring Boot backend запущен на **localhost:8080**
> Vite автоматически проксирует все запросы `/api` на `http://localhost:8080`

---

## Структура проекта
```
src/
├── api/index.js          # Все вызовы API (все эндпоинты)
├── components/
│   ├── UI.jsx            # Modal, Toast, Pagination, Loading...
│   └── Sidebar.jsx       # Боковое меню навигации
├── pages/
│   ├── DashboardPage.jsx # Главная страница со статистикой
│   ├── RestaurantsPage.jsx
│   ├── CategoriesPage.jsx
│   ├── DishesPage.jsx
│   ├── MenusPage.jsx     # OneToMany + ManyToMany связи
│   ├── CustomersPage.jsx
│   └── OrdersPage.jsx
├── App.jsx               # Роутинг
├── main.jsx              # Точка входа
└── index.css             # Глобальные стили
```

## Покрытые эндпоинты

### Рестораны
- GET /api/v1/restaurants — список с пагинацией
- GET /api/v1/restaurants/{id} — по ID
- GET /api/v1/restaurants/search-by-name?name= — поиск по названию
- GET /api/v1/restaurants/search?categoryName= — поиск по категории
- POST /api/v1/restaurants — создать
- PUT /api/v1/restaurants/{id} — обновить
- DELETE /api/v1/restaurants/{id} — удалить

### Категории
- GET/POST/PUT/DELETE с пагинацией и поиском

### Блюда
- GET с фильтром по имени и цене (сортировка по убыванию)
- POST/PUT/DELETE

### Меню (OneToMany с рестораном, ManyToMany с блюдами)
- GET /api/v1/menus/restaurant/{restaurantId} — меню ресторана
- POST /api/v1/menus/{menuId}/dishes/{dishId} — добавить блюдо
- POST /api/v1/menus/{menuId}/dishes/bulk — добавить несколько блюд
- Полный CRUD

### Клиенты
- Поиск по имени / фамилии
- Полный CRUD

### Заказы
- GET /api/v1/orders/search?lastName= — сортировка по дате
- Детальный просмотр заказа
- Полный CRUD
