import { NavLink } from 'react-router-dom';

const LINKS = [
  { to: '/',            label: 'Дашборд',   end: true },
  { to: '/restaurants', label: 'Рестораны' },
  { to: '/menus',       label: 'Меню'      },
  { to: '/dishes',      label: 'Блюда'     },
  { to: '/categories',  label: 'Категории' },
  { to: '/customers',   label: 'Клиенты'   },
  { to: '/orders',      label: 'Заказы'    },
];

export default function Header() {
  return (
    <header className="header">
      <div className="header-inner">
        <a href="/" className="header-logo">
          Food<span>Delivery</span>
        </a>
        <nav className="header-nav">
          {LINKS.map(l => (
            <NavLink key={l.to} to={l.to} end={l.end}
              className={({ isActive }) => `nav-link${isActive ? ' active' : ''}`}>
              {l.label}
            </NavLink>
          ))}
        </nav>
      </div>
    </header>
  );
}
