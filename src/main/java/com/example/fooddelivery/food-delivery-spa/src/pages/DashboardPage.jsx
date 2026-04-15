import { useState, useEffect } from 'react';
import { restaurantApi, categoryApi, dishApi, customerApi, orderApi, menuApi } from '../api';
import { Loading, StatusBadge } from '../components/UI';
import { useNavigate } from 'react-router-dom';

export default function DashboardPage() {
  const [stats, setStats] = useState(null);
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const nav = useNavigate();

  useEffect(() => {
    Promise.all([
      restaurantApi.getAll(0,1), categoryApi.getAll(0,1), dishApi.getAll(0,1),
      customerApi.getAll(0,1), orderApi.getAll(0,8), menuApi.getAll(0,1),
    ]).then(([r,c,d,cu,o,m]) => {
      setStats({ restaurants:r?.totalElements||0, categories:c?.totalElements||0, dishes:d?.totalElements||0, customers:cu?.totalElements||0, orders:o?.totalElements||0, menus:m?.totalElements||0 });
      setOrders(o?.content || []);
    }).finally(() => setLoading(false));
  }, []);

  if (loading) return <div className="page"><Loading /></div>;

  const STATS = [
    { label:'Рестораны', val:stats.restaurants, color:'#60a5fa', to:'/restaurants' },
    { label:'Меню',      val:stats.menus,        color:'#a78bfa', to:'/menus' },
    { label:'Блюда',     val:stats.dishes,        color:'#2dd4bf', to:'/dishes' },
    { label:'Клиенты',   val:stats.customers,     color:'#4ade80', to:'/customers' },
    { label:'Заказы',    val:stats.orders,         color:'#818cf8', to:'/orders' },
    { label:'Категории', val:stats.categories,    color:'#38bdf8', to:'/categories' },
  ];

  return (
    <div className="page">
      <div style={{ marginBottom: 24 }}>
        <h2 className="page-title">Дашборд</h2>
        <p className="page-subtitle">Обзор платформы</p>
      </div>

      <div style={{ display:'grid', gridTemplateColumns:'repeat(6,1fr)', gap:14, marginBottom:24 }}>
        {STATS.map(s => (
          <div key={s.label} className="stat-card" onClick={() => nav(s.to)}>
            <div className="stat-dot" style={{ background: s.color }} />
            <div className="stat-num">{s.val.toLocaleString('ru')}</div>
            <div className="stat-label">{s.label}</div>
          </div>
        ))}
      </div>

      <div className="tcard">
        <div className="tcard-head">
          <span className="tcard-title">Последние заказы</span>
          <button className="btn btn-ghost btn-sm" onClick={() => nav('/orders')}>Все заказы →</button>
        </div>
        {!orders.length ? (
          <div style={{ padding:'40px 20px', textAlign:'center', color:'var(--t3)' }}>
            <p>Заказов пока нет</p>
          </div>
        ) : (
          <table>
            <thead>
              <tr><th>ID</th><th>Клиент</th><th>Статус</th><th>Блюд</th>
                <th style={{textAlign:'right'}}>Сумма</th>
                <th style={{textAlign:'right'}}>Дата</th>
              </tr>
            </thead>
            <tbody>
              {orders.map(o => (
                <tr key={o.id}>
                  <td><span style={{ color:'var(--blue-600)', fontWeight:600, fontSize:12 }}>#{o.id}</span></td>
                  <td><strong>{o.customerFirstName} {o.customerLastName}</strong></td>
                  <td><StatusBadge status={o.status} white /></td>
                  <td>{o.amount}</td>
                  <td style={{textAlign:'right'}}><span style={{ color:'var(--blue-600)', fontWeight:600 }}>{o.totalPrice?.toFixed(2)} ₽</span></td>
                  <td style={{textAlign:'right'}}><span className="text-muted">{new Date(o.createdAt).toLocaleDateString('ru')}</span></td>
                </tr>
              ))}
            </tbody>
          </table>
        )}
      </div>
    </div>
  );
}
