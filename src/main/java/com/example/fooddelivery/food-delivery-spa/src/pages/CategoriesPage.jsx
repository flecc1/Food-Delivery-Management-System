import { useState, useEffect, useCallback } from 'react';
import { categoryApi, dishApi } from '../api';
import { Modal, ConfirmModal, Pagination, Loading, Field, useToast } from '../components/UI';

function CategoryForm({ initial, onSave, onClose }) {
  const [name, setName] = useState(initial?.name || '');
  const [saving, setSaving] = useState(false);
  const toast = useToast();
  const submit = async () => {
    if (!name.trim() || name.length > 64) { toast('От 1 до 64 символов','error'); return; }
    setSaving(true);
    try { await onSave({ name }); } catch(err) { toast(err.message,'error'); } finally { setSaving(false); }
  };
  return (
    <Modal title={initial ? 'Редактировать' : 'Новая категория'} onClose={onClose}
      footer={<><button className="btn btn-ghost" onClick={onClose}>Отмена</button><button className="btn btn-primary" onClick={submit} disabled={saving}>{saving?'...':'Сохранить'}</button></>}>
      <Field label="Название">
        <input className="form-input" value={name} onChange={e => setName(e.target.value)} placeholder="Пицца, Суши, Напитки..." autoFocus />
      </Field>
    </Modal>
  );
}

// Dish cards shown when a category is expanded
function DishCards({ categoryName }) {
  const [dishes, setDishes] = useState(null);

  useEffect(() => {
    dishApi.getAll(0, 500)
      .then(r => {
        const all = r?.content || [];
        setDishes(all.filter(d => d.categoryName === categoryName));
      })
      .catch(() => setDishes([]));
  }, [categoryName]);

  if (!dishes) return <div style={{ padding:'10px 0' }}><Loading /></div>;
  if (!dishes.length) return <p style={{ color:'var(--ct3)', fontSize:12 }}>Блюд нет</p>;

  return (
    <div style={{ display:'grid', gridTemplateColumns:'repeat(auto-fill, minmax(160px, 1fr))', gap:10 }}>
      {dishes.map((d, i) => (
        <div key={d.id} className="dish-card">
          <div style={{ display:'flex', alignItems:'center', justifyContent:'space-between', marginBottom:2 }}>
            <span className={`dtag dtag-${i%5}`} style={{ fontSize:9.5 }}>{d.categoryName}</span>
          </div>
          <div className="dish-card-name">{d.name}</div>
          {d.description && <div className="dish-card-desc">{d.description.slice(0,60)}{d.description.length>60?'…':''}</div>}
          <div style={{ marginTop:'auto', paddingTop:8 }}>
            <div className="dish-card-price">{d.price?.toFixed(2)} ₽</div>
            <div className="dish-card-cat" style={{ marginTop:3 }}>🏪 {d.restaurantName}</div>
          </div>
        </div>
      ))}
    </div>
  );
}

// Category card
function CategoryCard({ cat, onEdit, onDelete }) {
  const [expanded, setExpanded] = useState(false);

  return (
    <div className="menu-card">
      <div className="menu-card-header">
        <div className="menu-card-top">
          <div style={{ flex:1, minWidth:0 }}>
            <div className="menu-card-name">{cat.name}</div>
            <div className="menu-card-sub">
              <div className="sub-dot" style={{ background:'var(--sky-400)' }} />
              {cat.dishCount} блюд в категории
            </div>
          </div>
          <div className="menu-card-actions">
            <button className="btn btn-glass btn-icon btn-sm" onClick={e=>{e.stopPropagation();onEdit();}}>✏️</button>
            <button className="btn btn-danger btn-icon btn-sm" onClick={e=>{e.stopPropagation();onDelete();}}>🗑️</button>
          </div>
        </div>

        <div style={{ marginTop:8 }}>
          <span className="badge bg-sky" style={{ fontSize:10 }}>Категория</span>
        </div>
      </div>

      <div className="menu-card-footer">
        <div style={{ color:'var(--ct3)', fontSize:11 }}>
          <span style={{ color:'var(--sky-400)' }}>●</span> {cat.dishCount} позиций
        </div>
        {cat.dishCount > 0 && (
          <button className="btn btn-glass btn-sm" style={{ fontSize:11, padding:'3px 10px' }}
            onClick={() => setExpanded(!expanded)}>
            {expanded ? '▲ Скрыть' : '▼ Блюда'}
          </button>
        )}
      </div>

      {expanded && (
        <div style={{ padding:'14px 18px', borderTop:'1px solid rgba(255,255,255,0.08)', background:'rgba(0,0,0,0.12)' }}>
          <DishCards categoryName={cat.name} />
        </div>
      )}
    </div>
  );
}

export default function CategoriesPage() {
  const [data, setData] = useState(null);
  const [page, setPage] = useState(0);
  const [search, setSearch] = useState('');
  const [loading, setLoading] = useState(true);
  const [modal, setModal] = useState(null);
  const [confirm, setConfirm] = useState(null);
  const toast = useToast();

  const load = useCallback(async () => {
    setLoading(true);
    try {
      const res = search.trim()
        ? await categoryApi.searchByName(search.trim(), page)
        : await categoryApi.getAll(page, 12);
      setData(res);
    } catch(err) { toast(err.message,'error'); } finally { setLoading(false); }
  }, [page, search]);

  useEffect(() => { load(); }, [load]);

  const handleCreate = async (form) => { await categoryApi.create(form); toast('Создано','success'); setModal(null); load(); };
  const handleUpdate = async (form) => { await categoryApi.update(modal.id, form); toast('Обновлено','success'); setModal(null); load(); };
  const handleDelete = async (id) => { try{await categoryApi.delete(id);toast('Удалено','success');load();}catch(err){toast(err.message,'error');} };

  return (
    <div className="page">
      <div className="page-header">
        <div>
          <div style={{ display:'flex', alignItems:'center', gap:8 }}>
            <div style={{ width:3, height:18, borderRadius:2, background:'var(--e-cat)' }} />
            <h2 className="page-title">Категории</h2>
          </div>
          <p className="page-subtitle">Нажмите «Блюда» на карточке — увидите все блюда категории</p>
        </div>
        <button className="btn btn-primary" onClick={() => setModal('create')}>+ Добавить</button>
      </div>

      <div className="search-bar">
        <div className="input-wrap">
          <span className="input-icon">⌕</span>
          <input className="form-input input-pl" value={search}
            onChange={e => { setSearch(e.target.value); setPage(0); }}
            placeholder="Поиск по названию..." />
        </div>
        {search && <button className="btn btn-ghost btn-sm" onClick={() => { setSearch(''); setPage(0); }}>Сбросить</button>}
      </div>

      {loading ? <Loading /> : !data?.content?.length ? (
        <div className="gcard" style={{ padding:'50px 20px', textAlign:'center' }}>
          <p style={{ color:'var(--ct3)' }}>Категории не найдены</p>
        </div>
      ) : (
        <div className="card-grid">
          {data.content.map(c => (
            <CategoryCard key={c.id} cat={c}
              onEdit={() => setModal(c)}
              onDelete={() => setConfirm(c.id)}
            />
          ))}
        </div>
      )}
      <Pagination page={page} totalPages={data?.totalPages||0} onChange={setPage} />

      {modal==='create' && <CategoryForm onSave={handleCreate} onClose={() => setModal(null)} />}
      {modal && modal!=='create' && <CategoryForm initial={modal} onSave={handleUpdate} onClose={() => setModal(null)} />}
      {confirm && <ConfirmModal msg="Удалить категорию?" onConfirm={() => handleDelete(confirm)} onClose={() => setConfirm(null)} />}
    </div>
  );
}
