import { useState, useEffect, useCallback } from 'react';
import { dishApi, categoryApi, menuApi } from '../api';
import { Modal, ConfirmModal, Pagination, Loading, Field, useToast } from '../components/UI';

function DishForm({ initial, onSave, onClose }) {
  const [form, setForm] = useState(initial||{name:'',price:'',description:'',categoryId:'',menuId:''});
  const [categories, setCategories] = useState([]);
  const [menus, setMenus] = useState([]);
  const [saving, setSaving] = useState(false);
  const toast = useToast();
  useEffect(()=>{
    categoryApi.getAll(0,100).then(r=>setCategories(r?.content||[])).catch(()=>{});
    menuApi.getAll(0,100).then(r=>setMenus(r?.content||[])).catch(()=>{});
  },[]);
  const f=k=>e=>setForm(p=>({...p,[k]:e.target.value}));
  const submit=async()=>{
    if(!form.name||!form.price||!form.description||!form.categoryId||!form.menuId){toast('Заполните все поля','error');return;}
    setSaving(true);
    try{await onSave({...form,price:parseFloat(form.price),categoryId:+form.categoryId,menuId:+form.menuId});}
    catch(err){toast(err.message,'error');}finally{setSaving(false);}
  };
  return (
    <Modal title={initial?'Редактировать блюдо':'Новое блюдо'} onClose={onClose}
      footer={<><button className="btn btn-ghost" onClick={onClose}>Отмена</button><button className="btn btn-primary" onClick={submit} disabled={saving}>{saving?'...':'Сохранить'}</button></>}>
      <Field label="Название"><input className="form-input" value={form.name} onChange={f('name')} placeholder="Борщ" autoFocus/></Field>
      <Field label="Цена ₽"><input className="form-input" type="number" step="0.01" min="0" value={form.price} onChange={f('price')} placeholder="15.50"/></Field>
      <Field label="Описание"><textarea className="form-textarea" value={form.description} onChange={f('description')} placeholder="Состав, вес..."/></Field>
      <Field label="Категория">
        <select className="form-select" value={form.categoryId} onChange={f('categoryId')}>
          <option value="">Выберите категорию</option>
          {categories.map(c=><option key={c.id} value={c.id}>{c.name}</option>)}
        </select>
      </Field>
      <Field label="Меню">
        <select className="form-select" value={form.menuId} onChange={f('menuId')}>
          <option value="">Выберите меню</option>
          {menus.map(m=><option key={m.id} value={m.id}>{m.name} — {m.restaurantName}</option>)}
        </select>
      </Field>
    </Modal>
  );
}

export function DishesPage() {
  const [data, setData] = useState(null);
  const [page, setPage] = useState(0);
  const [sName, setSName] = useState('');
  const [sPrice, setSPrice] = useState('');
  const [loading, setLoading] = useState(true);
  const [modal, setModal] = useState(null);
  const [confirm, setConfirm] = useState(null);
  const toast = useToast();

  const load=useCallback(async()=>{
    setLoading(true);
    try{
      let res;
      if(sName.trim()) res=await dishApi.searchByName(sName.trim(),page);
      else if(sPrice) res=await dishApi.searchByPrice(parseFloat(sPrice),page);
      else res=await dishApi.getAll(page,10);
      setData(res);
    }catch(err){toast(err.message,'error');}finally{setLoading(false);}
  },[page,sName,sPrice]);

  useEffect(()=>{load();},[load]);

  const handleCreate=async(form)=>{await dishApi.create(form);toast('Добавлено','success');setModal(null);load();};
  const handleUpdate=async(form)=>{await dishApi.update(modal.id,form);toast('Обновлено','success');setModal(null);load();};
  const handleDelete=async(id)=>{try{await dishApi.delete(id);toast('Удалено','success');load();}catch(err){toast(err.message,'error');}};

  return (
    <div className="page">
      <div className="page-header">
        <div>
          <div style={{display:'flex',alignItems:'center',gap:8}}>
            <div style={{width:3,height:18,borderRadius:2,background:'var(--e-dish)'}}/>
            <h2 className="page-title">Блюда</h2>
          </div>
          <p className="page-subtitle">Позиции меню</p>
        </div>
        <button className="btn btn-primary" onClick={()=>setModal('create')}>+ Добавить</button>
      </div>
      <div className="search-bar">
        <div className="input-wrap"><span className="input-icon">⌕</span><input className="form-input input-pl" value={sName} onChange={e=>{setSName(e.target.value);setSPrice('');setPage(0);}} placeholder="По названию..."/></div>
        <div className="input-wrap" style={{maxWidth:140}}><span className="input-icon" style={{fontSize:10}}>₽</span><input className="form-input input-pl" type="number" value={sPrice} onChange={e=>{setSPrice(e.target.value);setSName('');setPage(0);}} placeholder="Цена..."/></div>
        {(sName||sPrice)&&<button className="btn btn-ghost btn-sm" onClick={()=>{setSName('');setSPrice('');setPage(0);}}>Сбросить</button>}
      </div>
      <div className="tcard">
        {loading?<Loading/>:!data?.content?.length?<div className="empty-state" style={{color:'var(--t3)'}}><p>Не найдено</p></div>:(
          <div className="tbl-wrap">
            <table>
              <thead><tr><th>Название</th><th>Цена</th><th>Категория</th><th>Ресторан</th><th/></tr></thead>
              <tbody>
                {data.content.map(d=>(
                  <tr key={d.id}>
                    <td><strong>{d.name}</strong>{d.description&&<div className="text-muted" style={{marginTop:1}}>{d.description.slice(0,46)}{d.description.length>46?'…':''}</div>}</td>
                    <td><span style={{color:'var(--blue-600)',fontWeight:600}}>{d.price?.toFixed(2)} ₽</span></td>
                    <td><span className="badge b-sky">{d.categoryName}</span></td>
                    <td><span className="badge b-blue">{d.restaurantName}</span></td>
                    <td><div className="actions-cell">
                      <button className="btn btn-ghost btn-icon btn-sm" onClick={()=>setModal({...d,categoryId:'',menuId:''})}>✏️</button>
                      <button className="btn btn-danger btn-icon btn-sm" onClick={()=>setConfirm(d.id)}>🗑️</button>
                    </div></td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
        <div style={{padding:'0 14px'}}><Pagination page={page} totalPages={data?.totalPages||0} onChange={setPage}/></div>
      </div>
      {modal==='create'&&<DishForm onSave={handleCreate} onClose={()=>setModal(null)}/>}
      {modal&&modal!=='create'&&<DishForm initial={modal} onSave={handleUpdate} onClose={()=>setModal(null)}/>}
      {confirm&&<ConfirmModal msg="Удалить блюдо?" onConfirm={()=>handleDelete(confirm)} onClose={()=>setConfirm(null)}/>}
    </div>
  );
}

export default DishesPage;
