import { useState, useEffect, useCallback } from 'react';
import { menuApi, restaurantApi, dishApi, categoryApi } from '../api';
import { Modal, ConfirmModal, Pagination, Loading, Field, useToast } from '../components/UI';

function MenuCard({ menu, onEdit, onDelete, onAddExisting, onAddBulk }) {
  return (
    <div className="menu-card">
      <div className="menu-card-header">
        <div className="menu-card-top">
          <div style={{ flex:1, minWidth:0 }}>
            <div className="menu-card-name">{menu.name}</div>
            <div className="menu-card-sub">
              <div className="sub-dot" />
              {menu.restaurantName}
            </div>
          </div>
          <div className="menu-card-actions">
            <button className="btn btn-glass btn-icon btn-sm" onClick={e=>{e.stopPropagation();onEdit();}}>✏️</button>
            <button className="btn btn-danger btn-icon btn-sm" onClick={e=>{e.stopPropagation();onDelete();}}>🗑️</button>
          </div>
        </div>
        <div style={{ display:'flex', alignItems:'center', gap:6, marginTop:6 }}>
          <span className={`badge ${menu.active?'bg-green':'bg-gray'}`} style={{ fontSize:10 }}>
            {menu.active ? '● Активно' : '○ Неактивно'}
          </span>
          {menu.dishes?.length > 0 && (
            <span className="badge bg-sky" style={{ fontSize:10 }}>{menu.dishes.length} блюд</span>
          )}
        </div>
      </div>

      {menu.dishes?.length > 0 && (
        <div style={{ padding:'0 18px 12px', display:'flex', flexWrap:'wrap', gap:5 }}>
          {menu.dishes.slice(0,5).map((d,i) => (
            <span key={d.id} className={`dtag dtag-${i%5}`}>{d.name}</span>
          ))}
          {menu.dishes.length > 5 && (
            <span className="badge bg-gray" style={{ fontSize:10 }}>+{menu.dishes.length-5}</span>
          )}
        </div>
      )}

      <div className="menu-card-footer">
        <div className="menu-card-time">
          <span>🕐</span>
          <span>{menu.description?.slice(0,28)||'Всё время'}{menu.description?.length>28?'…':''}</span>
        </div>
        {menu.id > 0 && (
          <div style={{ display:'flex', gap:5 }}>
            <button className="btn btn-glass btn-sm" style={{ fontSize:10.5, padding:'3px 8px' }} onClick={onAddExisting}>+ Блюдо</button>
            <button className="btn btn-glass btn-sm" style={{ fontSize:10.5, padding:'3px 8px' }} onClick={onAddBulk}>+ Несколько</button>
          </div>
        )}
      </div>
    </div>
  );
}

function AddExistingModal({ menuId, onClose, onDone }) {
  const [dishes, setDishes] = useState([]);
  const [sel, setSel] = useState('');
  const [saving, setSaving] = useState(false);
  const toast = useToast();
  useEffect(() => { dishApi.getAll(0,200).then(r=>setDishes(r?.content||[])); }, []);
  const submit = async () => {
    if (!sel) { toast('Выберите блюдо','error'); return; }
    setSaving(true);
    try { await menuApi.addDish(menuId,+sel); toast('Добавлено','success'); onDone(); }
    catch(err) { toast(err.message,'error'); } finally { setSaving(false); }
  };
  return (
    <Modal title="Добавить блюдо" onClose={onClose}
      footer={<><button className="btn btn-ghost" onClick={onClose}>Отмена</button><button className="btn btn-primary" onClick={submit} disabled={saving}>{saving?'...':'Добавить'}</button></>}>
      <Field label="Выберите блюдо">
        <div className="check-list">
          {dishes.map(d => (
            <div key={d.id} className={`check-row${sel===String(d.id)?' sel':''}`} onClick={()=>setSel(String(d.id))}>
              <input type="radio" name="d" checked={sel===String(d.id)} onChange={()=>setSel(String(d.id))} />
              <label>{d.name}</label>
              <span className="check-hint">{d.restaurantName}</span>
              <span style={{ fontSize:11, color:'var(--blue-600)', fontWeight:500 }}>{d.price?.toFixed(2)} ₽</span>
            </div>
          ))}
        </div>
      </Field>
    </Modal>
  );
}

const EMPTY = () => ({ name:'', price:'', description:'', categoryId:'' });

function AddBulkModal({ menuId, onClose, onDone }) {
  const [rows, setRows] = useState([EMPTY(), EMPTY()]);
  const [categories, setCategories] = useState([]);
  const [saving, setSaving] = useState(false);
  const toast = useToast();
  useEffect(() => { categoryApi.getAll(0,100).then(r=>setCategories(r?.content||[])); }, []);
  const upd = (i,k,v) => setRows(r=>r.map((row,idx)=>idx===i?{...row,[k]:v}:row));
  const submit = async () => {
    if (rows.some(r=>!r.name||!r.price||!r.description||!r.categoryId)) { toast('Заполните все поля','error'); return; }
    setSaving(true);
    try {
      await menuApi.addDishesBulk(menuId, rows.map(r=>({ name:r.name, price:parseFloat(r.price), description:r.description, categoryId:+r.categoryId, menuId:+menuId })));
      toast(`Добавлено ${rows.length} блюд`,'success'); onDone();
    } catch(err) { toast(err.message,'error'); } finally { setSaving(false); }
  };
  return (
    <Modal title="Добавить несколько блюд" onClose={onClose} wide
      footer={<>
        <button className="btn btn-ghost btn-sm" onClick={()=>setRows(r=>[...r,EMPTY()])} style={{marginRight:'auto'}}>+ Ещё строка</button>
        <button className="btn btn-ghost" onClick={onClose}>Отмена</button>
        <button className="btn btn-primary" onClick={submit} disabled={saving}>{saving?'...':`Сохранить (${rows.length})`}</button>
      </>}>
      <div style={{ display:'flex', flexDirection:'column', gap:10 }}>
        {rows.map((row,i) => (
          <div key={i} style={{ background:'#f0f6ff', border:'1px solid var(--blue-100)', borderRadius:'var(--r3)', padding:12 }}>
            <div style={{ display:'flex', justifyContent:'space-between', marginBottom:10 }}>
              <span style={{ fontSize:10, fontWeight:600, color:'var(--t3)', textTransform:'uppercase', letterSpacing:'0.5px' }}>Блюдо {i+1}</span>
              {rows.length > 1 && <button className="btn btn-ghost btn-icon btn-sm" onClick={()=>setRows(r=>r.filter((_,j)=>j!==i))}>✕</button>}
            </div>
            <div className="grid grid-2" style={{ gap:8 }}>
              <Field label="Название"><input className="form-input" value={row.name} onChange={e=>upd(i,'name',e.target.value)} placeholder="Борщ" /></Field>
              <Field label="Цена ₽"><input className="form-input" type="number" step="0.01" value={row.price} onChange={e=>upd(i,'price',e.target.value)} placeholder="15.50" /></Field>
            </div>
            <Field label="Описание"><textarea className="form-textarea" style={{minHeight:48}} value={row.description} onChange={e=>upd(i,'description',e.target.value)} placeholder="Состав..." /></Field>
            <Field label="Категория">
              <select className="form-select" value={row.categoryId} onChange={e=>upd(i,'categoryId',e.target.value)}>
                <option value="">Выберите...</option>
                {categories.map(c=><option key={c.id} value={c.id}>{c.name}</option>)}
              </select>
            </Field>
          </div>
        ))}
      </div>
    </Modal>
  );
}

function MenuForm({ initial, onSave, onClose }) {
  const [form, setForm] = useState(initial||{name:'',description:'',restaurantId:'',dishesIds:[]});
  const [restaurants, setRestaurants] = useState([]);
  const [dishes, setDishes] = useState([]);
  const [saving, setSaving] = useState(false);
  const toast = useToast();
  useEffect(() => {
    restaurantApi.getAll(0,100).then(r=>setRestaurants(r?.content||[])).catch(()=>{});
    dishApi.getAll(0,200).then(r=>setDishes(r?.content||[])).catch(()=>{});
  }, []);
  const f = k=>e=>setForm(p=>({...p,[k]:e.target.value}));
  const toggle = id=>setForm(p=>({...p,dishesIds:p.dishesIds.includes(id)?p.dishesIds.filter(x=>x!==id):[...p.dishesIds,id]}));
  const submit = async () => {
    if (!form.name||!form.description||!form.restaurantId) { toast('Заполните поля','error'); return; }
    setSaving(true);
    try { await onSave({...form,restaurantId:+form.restaurantId}); }
    catch(err) { toast(err.message,'error'); } finally { setSaving(false); }
  };
  return (
    <Modal title={initial?'Редактировать меню':'Новое меню'} onClose={onClose}
      footer={<><button className="btn btn-ghost" onClick={onClose}>Отмена</button><button className="btn btn-primary" onClick={submit} disabled={saving}>{saving?'...':'Сохранить'}</button></>}>
      <Field label="Название"><input className="form-input" value={form.name} onChange={f('name')} placeholder="Обеденное меню" autoFocus /></Field>
      <Field label="Описание / Время"><textarea className="form-textarea" value={form.description} onChange={f('description')} placeholder="Пн–Пт 12:00–16:00" /></Field>
      <Field label="Ресторан">
        <select className="form-select" value={form.restaurantId} onChange={f('restaurantId')}>
          <option value="">Выберите ресторан</option>
          {restaurants.map(r=><option key={r.id} value={r.id}>{r.name}</option>)}
        </select>
      </Field>
      <Field label={`Блюда${form.dishesIds.length?` (${form.dishesIds.length})`:''}`}>
        <div className="check-list">
          {dishes.map(d=>(
            <div key={d.id} className={`check-row${form.dishesIds.includes(d.id)?' sel':''}`} onClick={()=>toggle(d.id)}>
              <input type="checkbox" checked={form.dishesIds.includes(d.id)} onChange={()=>toggle(d.id)} />
              <label>{d.name}</label>
              <span className="check-hint">{d.restaurantName}</span>
              <span style={{fontSize:11,color:'var(--blue-600)',fontWeight:500}}>{d.price?.toFixed(2)} ₽</span>
            </div>
          ))}
        </div>
      </Field>
    </Modal>
  );
}

export default function MenusPage() {
  const [data, setData] = useState(null);
  const [page, setPage] = useState(0);
  const [search, setSearch] = useState('');
  const [restId, setRestId] = useState('');
  const [restaurants, setRestaurants] = useState([]);
  const [loading, setLoading] = useState(true);
  const [modal, setModal] = useState(null);
  const [confirm, setConfirm] = useState(null);
  const [addExisting, setAddExisting] = useState(null);
  const [addBulk, setAddBulk] = useState(null);
  const toast = useToast();

  useEffect(() => { restaurantApi.getAll(0,100).then(r=>setRestaurants(r?.content||[])).catch(()=>{}); }, []);

  const load = useCallback(async () => {
    setLoading(true);
    try {
      let res;
      if (restId) res = await menuApi.getByRestaurant(+restId, page);
      else if (search.trim()) res = await menuApi.searchByName(search.trim(), page);
      else res = await menuApi.getAll(page);
      setData(res);
    } catch(err) { toast(err.message,'error'); } finally { setLoading(false); }
  }, [page, search, restId]);

  useEffect(() => { load(); }, [load]);

  const handleCreate = async (form) => { await menuApi.create(form); toast('Создано','success'); setModal(null); load(); };
  const handleUpdate = async (form) => { await menuApi.update(modal.id, form); toast('Обновлено','success'); setModal(null); load(); };
  const handleDelete = async (id) => { try{await menuApi.delete(id);toast('Удалено','success');load();}catch(err){toast(err.message,'error');} };

  const combined = data?.content || [];

  return (
    <div className="page">
      <div className="page-header">
        <div>
          <div style={{ display:'flex', alignItems:'center', gap:8 }}>
            <div style={{ width:3, height:18, borderRadius:2, background:'var(--e-menu)' }} />
            <h2 className="page-title">Меню</h2>
          </div>
          <p className="page-subtitle">{data?.totalElements ? `${data.totalElements} меню` : 'Меню ресторанов'}</p>
        </div>
        <button className="btn btn-primary" onClick={() => setModal('create')}>+ Новое меню</button>
      </div>

      <div className="search-bar">
        <div className="input-wrap">
          <span className="input-icon">⌕</span>
          <input className="form-input input-pl" value={search}
            onChange={e => { setSearch(e.target.value); setRestId(''); setPage(0); }}
            placeholder="Поиск по названию..." />
        </div>
        <select className="form-select" style={{ width:185, flex:'none' }} value={restId}
          onChange={e => { setRestId(e.target.value); setSearch(''); setPage(0); }}>
          <option value="">Все рестораны</option>
          {restaurants.map(r=><option key={r.id} value={r.id}>{r.name}</option>)}
        </select>
        {(search||restId) && <button className="btn btn-ghost btn-sm" onClick={() => { setSearch(''); setRestId(''); setPage(0); }}>Сбросить</button>}
      </div>

      {loading ? <Loading /> : (
        <div className="card-grid">
          {combined.map(m => (
            <MenuCard key={m.id} menu={m}
              onEdit={() => m.id>0 && setModal({...m, restaurantId:'', dishesIds:m.dishes?.map(d=>d.id)||[]})}
              onDelete={() => m.id>0 && setConfirm(m.id)}
              onAddExisting={() => m.id>0 && setAddExisting(m.id)}
              onAddBulk={() => m.id>0 && setAddBulk(m.id)}
            />
          ))}
        </div>
      )}
      {data && <Pagination page={page} totalPages={data.totalPages||0} onChange={setPage} />}

      {modal==='create' && <MenuForm onSave={handleCreate} onClose={() => setModal(null)} />}
      {modal && modal!=='create' && <MenuForm initial={modal} onSave={handleUpdate} onClose={() => setModal(null)} />}
      {confirm && <ConfirmModal msg="Удалить меню?" onConfirm={() => handleDelete(confirm)} onClose={() => setConfirm(null)} />}
      {addExisting && <AddExistingModal menuId={addExisting} onClose={() => setAddExisting(null)} onDone={() => { setAddExisting(null); load(); }} />}
      {addBulk && <AddBulkModal menuId={addBulk} onClose={() => setAddBulk(null)} onDone={() => { setAddBulk(null); load(); }} />}
    </div>
  );
}
