const BASE = '/api/v1';

async function req(url, options = {}) {
  const res = await fetch(BASE + url, {
    headers: { 'Content-Type': 'application/json', ...options.headers },
    ...options,
  });
  if (!res.ok) {
    const err = await res.json().catch(() => ({ message: res.statusText }));
    throw new Error(err.message || 'Ошибка запроса');
  }
  if (res.status === 204 || res.headers.get('content-length') === '0') return null;
  return res.json();
}

export const restaurantApi = {
  getAll: (page = 0, size = 10) => req(`/restaurants?page=${page}&size=${size}`),
  getById: (id) => req(`/restaurants/${id}`),
  searchByName: (name, page = 0, size = 10) => req(`/restaurants/search-by-name?name=${encodeURIComponent(name)}&page=${page}&size=${size}`),
  searchByCategory: (categoryName, page = 0, size = 10) => req(`/restaurants/search?categoryName=${encodeURIComponent(categoryName)}&page=${page}&size=${size}`),
  create: (data) => req('/restaurants', { method: 'POST', body: JSON.stringify(data) }),
  update: (id, data) => req(`/restaurants/${id}`, { method: 'PUT', body: JSON.stringify(data) }),
  delete: (id) => req(`/restaurants/${id}`, { method: 'DELETE' }),
};

export const categoryApi = {
  getAll: (page = 0, size = 100) => req(`/categories?page=${page}&size=${size}`),
  getById: (id) => req(`/categories/${id}`),
  searchByName: (name, page = 0, size = 10) => req(`/categories?name=${encodeURIComponent(name)}&page=${page}&size=${size}`),
  create: (data) => req('/categories', { method: 'POST', body: JSON.stringify(data) }),
  update: (id, data) => req(`/categories/${id}`, { method: 'PUT', body: JSON.stringify(data) }),
  delete: (id) => req(`/categories/${id}`, { method: 'DELETE' }),
};

export const dishApi = {
  getAll: (page = 0, size = 200) => req(`/dishes?page=${page}&size=${size}`),
  getById: (id) => req(`/dishes/dishes/${id}`),
  searchByName: (name, page = 0, size = 10) => req(`/dishes?name=${encodeURIComponent(name)}&page=${page}&size=${size}`),
  searchByPrice: (price, page = 0, size = 10) => req(`/dishes?price=${price}&page=${page}&size=${size}`),
  create: (data) => req('/dishes', { method: 'POST', body: JSON.stringify(data) }),
  update: (id, data) => req(`/dishes/${id}`, { method: 'PUT', body: JSON.stringify(data) }),
  delete: (id) => req(`/dishes/${id}`, { method: 'DELETE' }),
};

export const menuApi = {
  getAll: (page = 0, size = 12) => req(`/menus?page=${page}&size=${size}`),
  getById: (id) => req(`/menus/${id}`),
  getByRestaurant: (restaurantId, page = 0, size = 50) => req(`/menus/restaurant/${restaurantId}?page=${page}&size=${size}`),
  searchByName: (name, page = 0, size = 12) => req(`/menus?name=${encodeURIComponent(name)}&page=${page}&size=${size}`),
  create: (data) => req('/menus', { method: 'POST', body: JSON.stringify(data) }),
  update: (id, data) => req(`/menus/${id}`, { method: 'PUT', body: JSON.stringify(data) }),
  delete: (id) => req(`/menus/${id}`, { method: 'DELETE' }),
  addDish: (menuId, dishId) => req(`/menus/${menuId}/dishes/${dishId}`, { method: 'POST' }),
  addDishesBulk: (menuId, dishes) => req(`/menus/${menuId}/dishes/bulk`, { method: 'POST', body: JSON.stringify(dishes) }),
};

export const customerApi = {
  getAll: (page = 0, size = 10) => req(`/customers?page=${page}&size=${size}`),
  getById: (id) => req(`/customers/${id}`),
  searchByFirstName: (firstName, page = 0, size = 10) => req(`/customers?firstName=${encodeURIComponent(firstName)}&page=${page}&size=${size}`),
  searchByLastName: (lastName, page = 0, size = 10) => req(`/customers?lastName=${encodeURIComponent(lastName)}&page=${page}&size=${size}`),
  create: (data) => req('/customers', { method: 'POST', body: JSON.stringify(data) }),
  update: (id, data) => req(`/customers/${id}`, { method: 'PUT', body: JSON.stringify(data) }),
  delete: (id) => req(`/customers/${id}`, { method: 'DELETE' }),
};

export const orderApi = {
  getById: (id) => req(`/orders/${id}`),
  getAll: (page = 0, size = 10) => req(`/orders/search?page=${page}&size=${size}`),
  searchByLastName: (lastName, page = 0, size = 10) => req(`/orders/search?lastName=${encodeURIComponent(lastName)}&page=${page}&size=${size}`),
  create: (data) => req('/orders', { method: 'POST', body: JSON.stringify(data) }),
  update: (id, data) => req(`/orders/${id}`, { method: 'PUT', body: JSON.stringify(data) }),
  delete: (id) => req(`/orders/${id}`, { method: 'DELETE' }),
};