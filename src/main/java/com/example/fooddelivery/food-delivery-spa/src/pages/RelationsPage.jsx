import { useState, useEffect, useCallback } from 'react';
import { restaurantApi, menuApi, customerApi, orderApi } from '../api';
import { Loading, OrderStatusBadge } from '../components/UI';

// ── OneToMany: Restaurant → Menu → Dishes ──────────────────────────
function RestaurantTree() {
  const [restaurants, setRestaurants] = useState([]);
  const [loading, setLoading] = useState(true);
  const [openRest, setOpenRest] = useState(null);
  const [menus, setMenus] = useState({});       // restId → Page
  const [loadingMenus, setLoadingMenus] = useState({});
  const [openMenu, setOpenMenu] = useState(null);

  useEffect(() => {
    restaurantApi.getAll(0, 50).then(r => setRestaurants(r?.content || [])).finally(() => setLoading(false));
  }, []);

  const toggleRest = async (id) => {
    if (openRest === id) { setOpenRest(null); return; }
    setOpenRest(id);
    if (!menus[id]) {
      setLoadingMenus(p => ({ ...p, [id]: true }));
      try {
        const res = await menuApi.getByRestaurant(id, 0, 50);
        setMenus(p => ({ ...p, [id]: res?.content || [] }));
      } finally {
        setLoadingMenus(p => ({ ...p, [id]: false }));
      }
    }
  };

  if (loading) return <Loading />;
  if (!restaurants.length) return <div className="empty-state"><p>Рестораны не найдены</p></div>;

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: 8 }}>
      {restaurants.map(r => (
        <div key={r.id} className="tree-item" style={{ borderTop: `3px solid var(--c-rest)` }}>
          <div className="tree-header" onClick={() => toggleRest(r.id)}>
            <div className="tree-header-left">
              <span style={{ fontWeight: 600, fontSize: 13, color: 'var(--t1)' }}>{r.name}</span>
              <span className="badge b-rest" style={{ fontSize: 10 }}>Ресторан</span>
              {r.rating > 0 && <span style={{ fontSize: 11, color: 'var(--amber)' }}>★ {r.rating?.toFixed(1)}</span>}
            </div>
            <span className={`tree-chevron ${openRest === r.id ? 'open' : ''}`}>▶</span>
          </div>

          {openRest === r.id && (
            <div className="tree-body">
              {loadingMenus[r.id] ? (
                <Loading />
              ) : !menus[r.id]?.length ? (
                <p style={{ color: 'var(--t3)', fontSize: 12, padding: '4px 0' }}>Меню не найдено</p>
              ) : menus[r.id].map(m => (
                <div key={m.id} className="tree-sub" style={{ borderLeft: `3px solid var(--c-menu)` }}>
                  <div className="tree-sub-header" onClick={() => setOpenMenu(openMenu === m.id ? null : m.id)}>
                    <div style={{ display: 'flex', alignItems: 'center', gap: 8 }}>
                      <span style={{ fontSize: 12.5, fontWeight: 500, color: 'var(--t1)' }}>{m.name}</span>
                      <span className="badge b-menu" style={{ fontSize: 9.5 }}>Меню</span>
                      <span className={`badge ${m.active ? 'b-green' : 'b-gray'}`} style={{ fontSize: 9.5 }}>{m.active ? 'Активно' : 'Неактивно'}</span>
                    </div>
                    <div style={{ display: 'flex', alignItems: 'center', gap: 7 }}>
                      <span style={{ fontSize: 11, color: 'var(--t3)' }}>{m.dishes?.length || 0} блюд</span>
                      <span className={`tree-chevron ${openMenu === m.id ? 'open' : ''}`} style={{ fontSize: 9 }}>▶</span>
                    </div>
                  </div>
                  {openMenu === m.id && (
                    <div className="tree-sub-body">
                      <p style={{ fontSize: 11, color: 'var(--t3)', marginBottom: 8 }}>{m.description}</p>
                      {!m.dishes?.length ? (
                        <p style={{ fontSize: 12, color: 'var(--t3)' }}>Блюд нет</p>
                      ) : (
                        <div className="dishes-list">
                          {m.dishes.map(d => (
                            <span key={d.id} style={{
                              display: 'inline-flex', alignItems: 'center', gap: 5,
                              background: 'var(--c-dish-lt)', border: '1px solid var(--c-dish-md)',
                              borderRadius: 'var(--r1)', padding: '3px 9px', fontSize: 11.5, color: 'var(--c-dish-dk)'
                            }}>
                              {d.name}
                              <span style={{ color: 'var(--t3)', fontSize: 10.5 }}>{d.price?.toFixed(2)} ₽</span>
                            </span>
                          ))}
                        </div>
                      )}
                    </div>
                  )}
                </div>
              ))}
            </div>
          )}
        </div>
      ))}
    </div>
  );
}

// ── OneToMany: Customer → Orders ───────────────────────────────────
function CustomerOrderTree() {
  const [customers, setCustomers] = useState([]);
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [open, setOpen] = useState(null);

  useEffect(() => {
    Promise.all([customerApi.getAll(0, 50), orderApi.getAll(0, 100)])
      .then(([c, o]) => {
        setCustomers(c?.content || []);
        setOrders(o?.content || []);
      }).finally(() => setLoading(false));
  }, []);

  if (loading) return <Loading />;
  if (!customers.length) return <div className="empty-state"><p>Клиенты не найдены</p></div>;

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: 8 }}>
      {customers.map(c => {
        const custOrders = orders.filter(o => o.customerId === c.id || (o.customerFirstName === c.firstName && o.customerLastName === c.lastName));
        return (
          <div key={c.id} className="tree-item" style={{ borderTop: `3px solid var(--c-cust)` }}>
            <div className="tree-header" onClick={() => setOpen(open === c.id ? null : c.id)}>
              <div className="tree-header-left">
                <div style={{ width: 30, height: 30, borderRadius: '50%', background: 'var(--c-cust-lt)', border: '1px solid var(--c-cust-md)', display: 'flex', alignItems: 'center', justifyContent: 'center', fontSize: 11, fontWeight: 700, color: 'var(--c-cust-dk)' }}>
                  {c.firstName?.[0]}{c.lastName?.[0]}
                </div>
                <span style={{ fontWeight: 600, fontSize: 13, color: 'var(--t1)' }}>{c.firstName} {c.lastName}</span>
                <span className="badge b-cust" style={{ fontSize: 10 }}>Клиент</span>
                <span style={{ fontSize: 11, color: 'var(--t3)' }}>{custOrders.length} заказов</span>
              </div>
              <span className={`tree-chevron ${open === c.id ? 'open' : ''}`}>▶</span>
            </div>
            {open === c.id && (
              <div className="tree-body">
                {!custOrders.length ? (
                  <p style={{ fontSize: 12, color: 'var(--t3)', padding: '2px 0' }}>Заказов не найдено в текущей выборке</p>
                ) : custOrders.map(o => (
                  <div key={o.id} style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', padding: '8px 11px', background: 'rgba(255,255,255,0.5)', border: '1px solid var(--b1)', borderLeft: '3px solid var(--c-ord)', borderRadius: 'var(--r2)' }}>
                    <div>
                      <div style={{ fontSize: 12.5, fontWeight: 500, color: 'var(--t1)' }}>Заказ #{o.id}</div>
                      <div className="text-muted">{o.address} · {new Date(o.createdAt).toLocaleDateString('ru')}</div>
                    </div>
                    <div style={{ display: 'flex', alignItems: 'center', gap: 8 }}>
                      <OrderStatusBadge status={o.status} />
                      <span style={{ fontWeight: 600, color: 'var(--c-ord)', fontSize: 12.5 }}>{o.totalPrice?.toFixed(2)} ₽</span>
                    </div>
                  </div>
                ))}
              </div>
            )}
          </div>
        );
      })}
    </div>
  );
}

// ── ManyToMany: Menu ↔ Dishes ──────────────────────────────────────
function MenuDishManyToMany() {
  const [menus, setMenus] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    menuApi.getAll(0, 30).then(r => setMenus(r?.content || [])).finally(() => setLoading(false));
  }, []);

  if (loading) return <Loading />;
  if (!menus.length) return <div className="empty-state"><p>Меню не найдено</p></div>;

  // Build dish→menus map to find shared dishes
  const dishMenus = {};
  menus.forEach(m => {
    m.dishes?.forEach(d => {
      if (!dishMenus[d.id]) dishMenus[d.id] = { dish: d, menuNames: [] };
      dishMenus[d.id].menuNames.push(m.name);
    });
  });
  const sharedDishes = Object.values(dishMenus).filter(x => x.menuNames.length > 1);

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: 14 }}>
      <div className="grid grid-2">
        {menus.map(m => (
          <div key={m.id} className="mm-card">
            <div className="mm-header">
              <div style={{ display: 'flex', alignItems: 'center', gap: 8 }}>
                <div style={{ width: 3, height: 16, borderRadius: 2, background: 'var(--c-menu)', flexShrink: 0 }} />
                <span style={{ fontWeight: 600, fontSize: 12.5, color: 'var(--t1)' }}>{m.name}</span>
              </div>
              <div style={{ display: 'flex', alignItems: 'center', gap: 6 }}>
                <span style={{ fontSize: 11, color: 'var(--t3)' }}>{m.restaurantName}</span>
                <span className="badge b-dish" style={{ fontSize: 9.5 }}>{m.dishes?.length || 0} блюд</span>
              </div>
            </div>
            <div className="mm-body">
              {!m.dishes?.length ? (
                <p style={{ fontSize: 12, color: 'var(--t3)' }}>Блюд нет</p>
              ) : (
                <div className="dishes-list">
                  {m.dishes.map(d => {
                    const isShared = dishMenus[d.id]?.menuNames.length > 1;
                    return (
                      <span key={d.id} title={isShared ? `Это блюдо есть в: ${dishMenus[d.id].menuNames.join(', ')}` : d.name}
                        style={{
                          display: 'inline-flex', alignItems: 'center', gap: 4,
                          padding: '3px 8px', borderRadius: 'var(--r1)', fontSize: 11.5, cursor: 'default',
                          background: isShared ? 'rgba(234,88,12,0.1)' : 'var(--c-dish-lt)',
                          border: `1px solid ${isShared ? 'var(--c-rest-md)' : 'var(--c-dish-md)'}`,
                          color: isShared ? 'var(--c-rest-dk)' : 'var(--c-dish-dk)',
                        }}>
                        {isShared && <span style={{ fontSize: 9, fontWeight: 700 }}>↔</span>}
                        {d.name}
                      </span>
                    );
                  })}
                </div>
              )}
            </div>
          </div>
        ))}
      </div>

      {sharedDishes.length > 0 && (
        <div style={{ background: 'rgba(234,88,12,0.06)', border: '1px solid var(--c-rest-md)', borderRadius: 'var(--r3)', padding: '13px 15px' }}>
          <div style={{ fontSize: 11, fontWeight: 600, color: 'var(--c-rest-dk)', marginBottom: 9, textTransform: 'uppercase', letterSpacing: '0.5px' }}>
            Блюда в нескольких меню — общие элементы ManyToMany
          </div>
          <div style={{ display: 'flex', flexDirection: 'column', gap: 6 }}>
            {sharedDishes.map(({ dish, menuNames }) => (
              <div key={dish.id} style={{ display: 'flex', alignItems: 'center', gap: 9, flexWrap: 'wrap' }}>
                <span style={{ fontWeight: 500, fontSize: 12.5, color: 'var(--t1)', minWidth: 120 }}>{dish.name}</span>
                <span style={{ fontSize: 11, color: 'var(--t3)' }}>присутствует в:</span>
                {menuNames.map(mn => (
                  <span key={mn} className="badge b-menu" style={{ fontSize: 10 }}>{mn}</span>
                ))}
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}

// ── ManyToMany: Order ↔ Dishes ──────────────────────────────────────
function OrderDishManyToMany() {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const [open, setOpen] = useState(null);

  useEffect(() => {
    orderApi.getAll(0, 20).then(r => setOrders(r?.content || [])).finally(() => setLoading(false));
  }, []);

  if (loading) return <Loading />;
  if (!orders.length) return <div className="empty-state"><p>Заказов нет</p></div>;

  return (
    <div style={{ display: 'flex', flexDirection: 'column', gap: 7 }}>
      {orders.map(o => (
        <div key={o.id} className="tree-item" style={{ borderTop: `3px solid var(--c-ord)` }}>
          <div className="tree-header" onClick={() => setOpen(open === o.id ? null : o.id)}>
            <div className="tree-header-left">
              <span style={{ fontWeight: 600, fontSize: 13, color: 'var(--t1)' }}>Заказ #{o.id}</span>
              <span style={{ fontSize: 12, color: 'var(--t2)' }}>{o.customerFirstName} {o.customerLastName}</span>
              <OrderStatusBadge status={o.status} />
              <span className="badge b-dish" style={{ fontSize: 10 }}>{o.amount} блюд</span>
            </div>
            <div style={{ display: 'flex', alignItems: 'center', gap: 9 }}>
              <span style={{ fontWeight: 600, color: 'var(--c-ord)', fontSize: 12.5 }}>{o.totalPrice?.toFixed(2)} ₽</span>
              <span className={`tree-chevron ${open === o.id ? 'open' : ''}`}>▶</span>
            </div>
          </div>
          {open === o.id && (
            <div className="tree-body">
              {!o.dishes?.length ? (
                <p style={{ fontSize: 12, color: 'var(--t3)' }}>Блюда не загружены</p>
              ) : (
                <div className="dishes-list">
                  {o.dishes.map(d => (
                    <span key={d.id} style={{ display: 'inline-flex', alignItems: 'center', gap: 5, background: 'var(--c-dish-lt)', border: '1px solid var(--c-dish-md)', borderRadius: 'var(--r1)', padding: '3px 9px', fontSize: 11.5, color: 'var(--c-dish-dk)' }}>
                      {d.name}
                      <span style={{ color: 'var(--t3)', fontSize: 10.5 }}>{d.price?.toFixed(2)} ₽</span>
                    </span>
                  ))}
                </div>
              )}
              <div style={{ marginTop: 8 }}>
                <span className="text-muted">📍 {o.address}</span>
              </div>
            </div>
          )}
        </div>
      ))}
    </div>
  );
}

// ── Main page ──────────────────────────────────────────────────────
const TABS = ['one-to-many', 'many-to-many'];
const TAB_LABEL = { 'one-to-many': 'OneToMany', 'many-to-many': 'ManyToMany' };

export default function RelationsPage() {
  const [tab, setTab] = useState('one-to-many');
  const [subtab, setSubtab] = useState(0);

  return (
    <div className="page">
      <div className="page-header" style={{ flexDirection: 'column', alignItems: 'flex-start', gap: 2 }}>
        <h2 className="page-title">Связи данных</h2>
        <p className="page-subtitle">Интерактивный просмотр отношений между сущностями</p>
      </div>

      {/* Tab switcher */}
      <div style={{ display: 'flex', gap: 6, marginBottom: 20 }}>
        {TABS.map(t => (
          <button key={t} onClick={() => { setTab(t); setSubtab(0); }}
            className="btn"
            style={{
              background: tab === t ? 'var(--t1)' : 'rgba(255,255,255,0.7)',
              color: tab === t ? '#fff' : 'var(--t2)',
              border: `1px solid ${tab === t ? 'var(--t1)' : 'var(--b2)'}`,
              fontWeight: tab === t ? 600 : 400,
            }}>
            {TAB_LABEL[t]}
          </button>
        ))}
      </div>

      {tab === 'one-to-many' && (
        <>
          <div style={{ display: 'flex', gap: 6, marginBottom: 16 }}>
            {['Ресторан → Меню → Блюда', 'Клиент → Заказы'].map((s, i) => (
              <button key={i} onClick={() => setSubtab(i)}
                className="btn btn-sm"
                style={{
                  background: subtab === i ? (i === 0 ? 'var(--c-rest-lt)' : 'var(--c-cust-lt)') : 'rgba(255,255,255,0.6)',
                  color: subtab === i ? (i === 0 ? 'var(--c-rest-dk)' : 'var(--c-cust-dk)') : 'var(--t2)',
                  border: `1px solid ${subtab === i ? (i === 0 ? 'var(--c-rest-md)' : 'var(--c-cust-md)') : 'var(--b2)'}`,
                  fontWeight: subtab === i ? 600 : 400,
                }}>
                {s}
              </button>
            ))}
          </div>
          {subtab === 0 ? <RestaurantTree /> : <CustomerOrderTree />}
        </>
      )}

      {tab === 'many-to-many' && (
        <>
          <div style={{ display: 'flex', gap: 6, marginBottom: 16 }}>
            {['Меню ↔ Блюда', 'Заказ ↔ Блюда'].map((s, i) => (
              <button key={i} onClick={() => setSubtab(i)}
                className="btn btn-sm"
                style={{
                  background: subtab === i ? 'var(--c-menu-lt)' : 'rgba(255,255,255,0.6)',
                  color: subtab === i ? 'var(--c-menu-dk)' : 'var(--t2)',
                  border: `1px solid ${subtab === i ? 'var(--c-menu-md)' : 'var(--b2)'}`,
                  fontWeight: subtab === i ? 600 : 400,
                }}>
                {s}
              </button>
            ))}
          </div>
          {subtab === 0 ? <MenuDishManyToMany /> : <OrderDishManyToMany />}
        </>
      )}
    </div>
  );
}
