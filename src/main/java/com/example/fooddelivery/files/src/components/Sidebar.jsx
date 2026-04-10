import { NavLink } from 'react-router-dom';

const NAV = [
    { section: 'Главная', items: [
            { to: '/', icon: '📊', label: 'Дашборд', end: true },
        ]},
    { section: 'Каталог', items: [
            { to: '/restaurants', icon: '🍽', label: 'Рестораны' },
            { to: '/menus', icon: '📋', label: 'Меню' },
            { to: '/dishes', icon: '🍜', label: 'Блюда' },
            { to: '/categories', icon: '🏷', label: 'Категории' },
        ]},
    { section: 'Пользователи', items: [
            { to: '/customers', icon: '👤', label: 'Клиенты' },
            { to: '/orders', icon: '📦', label: 'Заказы' },
        ]},
];

export default function Sidebar() {
  return (
    <aside className="sidebar">
      <div className="sidebar-logo">
        <h1>FoodFlow</h1>
        <span>Панель управления</span>
      </div>
      <nav className="sidebar-nav">
        {NAV.map(sec => (
          <div key={sec.section} className="nav-section">
            <div className="nav-section-title">{sec.section}</div>
            {sec.items.map(item => (
              <NavLink key={item.to} to={item.to} end={item.end}
                className={({ isActive }) => `nav-item ${isActive ? 'active' : ''}`}>
                <span className="icon" style={{ fontStyle: 'normal' }}>{item.icon}</span>
                {item.label}
              </NavLink>
            ))}
          </div>
        ))}
      </nav>
      <div className="sidebar-footer">
        Food Delivery API v1<br />
        <strong>localhost:8080</strong>
      </div>
    </aside>
  );
}
