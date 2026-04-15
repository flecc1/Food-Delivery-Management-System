import { useState, useEffect, useCallback } from 'react';
import { restaurantApi, menuApi } from '../api';
import { Modal, ConfirmModal, Pagination, Loading, Field, useToast } from '../components/UI';

function RestaurantForm({ initial, onSave, onClose }) {
  const [form, setForm] = useState(initial || { name:'', address:'', city:'' });
  const [errors, setErrors] = useState({});
  const [saving, setSaving] = useState(false);
  const toast = useToast();
  const f = k => e => setForm(p => ({ ...p, [k]: e.target.value }));

  const submit = async () => {
    const e = {};
    if (!form.name || form.name.length < 2) e.name = 'Минимум 2 символа';
    if (!form.address || form.address.length < 2) e.address = 'Минимум 2 символа';
    if (!form.city || form.city.length < 2) e.city = 'Минимум 2 символа';
    if (Object.keys(e).length) { setErrors(e); return; }
    setSaving(true);
    try { await onSave(form); } catch(err) { toast(err.message,'error'); } finally { setSaving(false); }
  };

  return (
    <Modal title={initial ? 'Редактировать ресторан' : 'Новый ресторан'} onClose={onClose}
      footer={<><button className="btn btn-ghost" onClick={onClose}>Отмена</button><button className="btn btn-primary" onClick={submit} disabled={saving}>{saving?'...':'Сохранить'}</button></>}>
      <Field label="Название" error={errors.name}><input className="form-input" value={form.name} onChange={f('name')} placeholder="Название ресторана" autoFocus /></Field>
      <Field label="Адрес" error={errors.address}><input className="form-input" value={form.address} onChange={f('address')} placeholder="Улица, дом" /></Field>
      <Field label="Город" error={errors.city}><input className="form-input" value={form.city} onChange={f('city')} placeholder="Город" /></Field>
    </Modal>
  );
}

// Menus inside expanded restaurant card
function RestaurantMenuPanel({ restId }) {
  const [menus, setMenus] = useState(null);
  const [openMenu, setOpenMenu] = useState(null);

  useEffect(() => {
    menuApi.getByRestaurant(restId, 0, 50).then(r => setMenus(r?.content || []));
  }, [restId]);

  if (!menus) return <div style={{ padding:'12px 0' }}><Loading /></div>;
  if (!menus.length) return <p style={{ color:'var(--ct3)', fontSize:12, padding:'4px 0' }}>Меню не найдены</p>;

  return (
    <div>
      <div style={{ fontSize:10, fontWeight:600, color:'var(--ct3)', letterSpacing:'0.7px', textTransform:'uppercase', marginBottom:10 }}>
        Меню — {menus.length}
      </div>
      <div style={{ display:'flex', flexDirection:'column', gap:6 }}>
        {menus.map(m => (
          <div key={m.id} style={{ background:'rgba(255,255,255,0.08)', border:'1px solid rgba(255,255,255,0.14)', borderRadius:10, overflow:'hidden' }}>
            <div style={{ padding:'8px 12px', display:'flex', alignItems:'center', justifyContent:'space-between', cursor:'pointer' }}
              onClick={() => setOpenMenu(openMenu===m.id ? null : m.id)}>
              <div style={{ display:'flex', alignItems:'center', gap:8 }}>
                <div style={{ width:14, height:14, borderRadius:3, border:'1px solid rgba(255,255,255,0.2)', display:'flex', alignItems:'center', justifyContent:'center', fontSize:7, color:'var(--ct3)', transition:'transform 0.15s', transform: openMenu===m.id?'rotate(90deg)':'none' }}>▶</div>
                <span style={{ fontWeight:600, fontSize:12.5, color:'var(--ct1)' }}>{m.name}</span>
                <span className={`badge ${m.active?'bg-green':'bg-gray'}`} style={{ fontSize:9.5 }}>{m.active?'Активно':'Неактивно'}</span>
              </div>
              <span style={{ fontSize:11, color:'var(--ct3)' }}>{m.dishes?.length||0} блюд</span>
            </div>
            {openMenu===m.id && (
              <div style={{ borderTop:'1px solid rgba(255,255,255,0.1)', padding:'10px 12px', background:'rgba(0,0,0,0.1)' }}>
                <p style={{ fontSize:11, color:'var(--ct3)', marginBottom:8 }}>{m.description}</p>
                {!m.dishes?.length ? <p style={{ fontSize:12, color:'var(--ct3)' }}>Блюд нет</p> : (
                  <div style={{ display:'flex', flexWrap:'wrap', gap:5 }}>
                    {m.dishes.map((d,i) => (
                      <span key={d.id} className={`dtag dtag-${i%5}`}>{d.name} · {d.price?.toFixed(2)} ₽</span>
                    ))}
                  </div>
                )}
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
}

// Restaurant card
function RestaurantCard({ r, onEdit, onDelete }) {
  const [expanded, setExpanded] = useState(false);

  return (
    <div className="menu-card">
      <div className="menu-card-header">
        <div className="menu-card-top">
          <div style={{ flex:1, minWidth:0 }}>
            <div className="menu-card-name">{r.name}</div>
            {r.rating > 0 && (
              <div style={{ display:'flex', alignItems:'center', gap:5, marginTop:4 }}>
                <span style={{ color:'#fbbf24', fontSize:12, letterSpacing:1 }}>{'★'.repeat(Math.round(r.rating))}</span>
                <span style={{ fontSize:11, color:'var(--ct3)' }}>{r.rating.toFixed(1)}</span>
              </div>
            )}
          </div>
          <div className="menu-card-actions">
            <button className="btn btn-glass btn-icon btn-sm" onClick={e=>{e.stopPropagation();onEdit();}}>✏️</button>
            <button className="btn btn-danger btn-icon btn-sm" onClick={e=>{e.stopPropagation();onDelete();}}>🗑️</button>
          </div>
        </div>
      </div>

      <div className="menu-card-footer">
        <div style={{ color:'var(--ct3)', fontSize:11 }}>
          <span style={{ color:'var(--sky-400)', fontSize:12 }}>●</span> Ресторан
        </div>
        <button className="btn btn-glass btn-sm" style={{ fontSize:11, padding:'3px 10px' }}
          onClick={() => setExpanded(!expanded)}>
          {expanded ? '▲ Скрыть' : '▼ Меню'}
        </button>
      </div>

      {expanded && (
        <div style={{ padding:'14px 18px', borderTop:'1px solid rgba(255,255,255,0.08)', background:'rgba(0,0,0,0.12)' }}>
          <RestaurantMenuPanel restId={r.id} />
        </div>
      )}
    </div>
  );
}

export default function RestaurantsPage() {
  const [data, setData] = useState(null);
  const [page, setPage] = useState(0);
  const [search, setSearch] = useState('');
  const [catSearch, setCatSearch] = useState('');
  const [loading, setLoading] = useState(true);
  const [modal, setModal] = useState(null);
  const [confirm, setConfirm] = useState(null);
  const toast = useToast();

  const load = useCallback(async () => {
    setLoading(true);
    try {
      let res;
      if (catSearch.trim()) res = await restaurantApi.searchByCategory(catSearch.trim(), page);
      else if (search.trim()) res = await restaurantApi.searchByName(search.trim(), page);
      else res = await restaurantApi.getAll(page);
      setData(res);
    } catch(err) { toast(err.message,'error'); } finally { setLoading(false); }
  }, [page, search, catSearch]);

  useEffect(() => { load(); }, [load]);

  const handleCreate = async (form) => { await restaurantApi.create(form); toast('Добавлено','success'); setModal(null); load(); };
  const handleUpdate = async (form) => { await restaurantApi.update(modal.id, form); toast('Обновлено','success'); setModal(null); load(); };
  const handleDelete = async (id) => { try{await restaurantApi.delete(id);toast('Удалено','success');load();}catch(err){toast(err.message,'error');} };

  return (
    <div className="page">
      <div className="page-header">
        <div>
          <div style={{ display:'flex', alignItems:'center', gap:8 }}>
            <div style={{ width:3, height:18, borderRadius:2, background:'var(--e-rest)' }} />
            <h2 className="page-title">Рестораны</h2>
          </div>
          <p className="page-subtitle">Нажмите «Меню» на карточке — увидите меню и блюда</p>
        </div>
        <button className="btn btn-primary" onClick={() => setModal('create')}>+ Добавить</button>
      </div>

      <div className="search-bar">
        <div className="input-wrap">
          <span className="input-icon">⌕</span>
          <input className="form-input input-pl" value={search}
            onChange={e => { setSearch(e.target.value); setCatSearch(''); setPage(0); }}
            placeholder="Поиск по названию..." />
        </div>
        <div className="input-wrap">
          <span className="input-icon">⌕</span>
          <input className="form-input input-pl" value={catSearch}
            onChange={e => { setCatSearch(e.target.value); setSearch(''); setPage(0); }}
            placeholder="Поиск по категории..." />
        </div>
        {(search||catSearch) && <button className="btn btn-ghost btn-sm" onClick={() => { setSearch(''); setCatSearch(''); setPage(0); }}>Сбросить</button>}
      </div>

      {loading ? <Loading /> : !data?.content?.length ? (
        <div className="gcard" style={{ padding:'50px 20px', textAlign:'center' }}>
          <p style={{ color:'var(--ct3)' }}>Рестораны не найдены</p>
        </div>
      ) : (
        <div className="card-grid">
          {data.content.map(r => (
            <RestaurantCard key={r.id} r={r}
              onEdit={() => setModal(r)}
              onDelete={() => setConfirm(r.id)}
            />
          ))}
        </div>
      )}
      <Pagination page={page} totalPages={data?.totalPages||0} onChange={setPage} />

      {modal==='create' && <RestaurantForm onSave={handleCreate} onClose={() => setModal(null)} />}
      {modal && modal!=='create' && <RestaurantForm initial={modal} onSave={handleUpdate} onClose={() => setModal(null)} />}
      {confirm && <ConfirmModal msg="Удалить ресторан?" onConfirm={() => handleDelete(confirm)} onClose={() => setConfirm(null)} />}
    </div>
  );
}
