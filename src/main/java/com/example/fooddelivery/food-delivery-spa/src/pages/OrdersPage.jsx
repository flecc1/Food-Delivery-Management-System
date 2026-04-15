import { useState, useEffect, useCallback } from 'react';
import { orderApi, customerApi, restaurantApi, dishApi } from '../api';
import { Modal, ConfirmModal, Pagination, Loading, Field, useToast, StatusBadge } from '../components/UI';

function OrderForm({ initial, onSave, onClose }) {
  const [form, setForm] = useState(initial||{dishesId:[],address:'',customerId:'',restaurantId:''});
  const [customers, setCustomers] = useState([]);
  const [restaurants, setRestaurants] = useState([]);
  const [dishes, setDishes] = useState([]);
  const [saving, setSaving] = useState(false);
  const toast = useToast();

  useEffect(()=>{
    customerApi.getAll(0,100).then(r=>setCustomers(r?.content||[])).catch(()=>{});
    restaurantApi.getAll(0,100).then(r=>setRestaurants(r?.content||[])).catch(()=>{});
    dishApi.getAll(0,200).then(r=>setDishes(r?.content||[])).catch(()=>{});
  },[]);

  const f=k=>e=>setForm(p=>({...p,[k]:e.target.value}));
  const toggle=id=>setForm(p=>({...p,dishesId:p.dishesId.includes(id)?p.dishesId.filter(x=>x!==id):[...p.dishesId,id]}));
  const grouped=dishes.reduce((acc,d)=>{const rn=d.restaurantName||'Без ресторана';if(!acc[rn])acc[rn]=[];acc[rn].push(d);return acc;},{});
  const total=dishes.filter(d=>form.dishesId.includes(d.id)).reduce((s,d)=>s+d.price,0);

  const submit=async()=>{
    if(!form.dishesId.length||!form.address||!form.customerId||!form.restaurantId){toast('Заполните все поля','error');return;}
    setSaving(true);
    try{await onSave({...form,customerId:+form.customerId,restaurantId:+form.restaurantId});}
    catch(err){toast(err.message,'error');}finally{setSaving(false);}
  };

  return (
    <Modal title={initial?'Редактировать заказ':'Новый заказ'} onClose={onClose}
      footer={<>
        <span style={{flex:1,fontWeight:600,color:'var(--blue-600)',fontSize:13}}>{form.dishesId.length>0?`${total.toFixed(2)} ₽`:''}</span>
        <button className="btn btn-ghost" onClick={onClose}>Отмена</button>
        <button className="btn btn-primary" onClick={submit} disabled={saving}>{saving?'...':'Оформить'}</button>
      </>}>
      <Field label="Клиент">
        <select className="form-select" value={form.customerId} onChange={f('customerId')}>
          <option value="">Выберите клиента</option>
          {customers.map(c=><option key={c.id} value={c.id}>{c.firstName} {c.lastName}</option>)}
        </select>
      </Field>
      <Field label="Ресторан">
        <select className="form-select" value={form.restaurantId} onChange={f('restaurantId')}>
          <option value="">Выберите ресторан</option>
          {restaurants.map(r=><option key={r.id} value={r.id}>{r.name}</option>)}
        </select>
      </Field>
      <Field label="Адрес доставки">
        <input className="form-input" value={form.address} onChange={f('address')} placeholder="г. Минск, ул. Ленина, д. 10"/>
      </Field>
      <Field label={`Блюда${form.dishesId.length?` — ${form.dishesId.length}`:''}`}>
        <div className="check-list">
          {Object.entries(grouped).map(([rn,rds])=>(
            <div key={rn}>
              <div style={{padding:'5px 8px 2px',fontSize:9.5,fontWeight:700,color:'var(--blue-600)',textTransform:'uppercase',letterSpacing:'0.5px'}}>{rn}</div>
              {rds.map(d=>(
                <div key={d.id} className={`check-row${form.dishesId.includes(d.id)?' sel':''}`} onClick={()=>toggle(d.id)}>
                  <input type="checkbox" checked={form.dishesId.includes(d.id)} onChange={()=>toggle(d.id)}/>
                  <label>{d.name}</label>
                  <span style={{fontSize:11,color:'var(--blue-600)',fontWeight:500}}>{d.price?.toFixed(2)} ₽</span>
                </div>
              ))}
            </div>
          ))}
        </div>
      </Field>
    </Modal>
  );
}

function OrderDetail({ id, onClose }) {
  const [order, setOrder] = useState(null);
  const toast = useToast();
  useEffect(()=>{orderApi.getById(id).then(setOrder).catch(err=>{toast(err.message,'error');onClose();});},[id]);
  if(!order) return <Modal title="Загрузка..." onClose={onClose}><Loading/></Modal>;
  return (
    <Modal title={`Заказ #${order.id}`} onClose={onClose}
      footer={<button className="btn btn-ghost" onClick={onClose}>Закрыть</button>}>
      <div style={{display:'flex',flexDirection:'column',gap:12}}>
        <div style={{display:'flex',justifyContent:'space-between',alignItems:'center'}}>
          <StatusBadge status={order.status} white/><span className="text-muted">{new Date(order.createdAt).toLocaleString('ru')}</span>
        </div>
        <div style={{background:'var(--blue-50)',border:'1px solid var(--blue-100)',borderRadius:'var(--r3)',padding:'10px 12px'}}>
          <div className="text-muted" style={{marginBottom:2}}>Клиент</div>
          <strong style={{fontSize:13}}>{order.customerFirstName} {order.customerLastName}</strong>
        </div>
        <div style={{background:'var(--blue-50)',border:'1px solid var(--blue-100)',borderRadius:'var(--r3)',padding:'10px 12px'}}>
          <div className="text-muted" style={{marginBottom:2}}>Адрес</div>
          <span style={{fontSize:13}}>{order.address}</span>
        </div>
        <div>
          <div className="text-muted" style={{marginBottom:8}}>Состав — {order.amount} позиций</div>
          {order.dishes?.map(d=>(
            <div key={d.id} style={{display:'flex',justifyContent:'space-between',alignItems:'center',padding:'6px 0',borderBottom:'1px solid var(--b1)',fontSize:12.5}}>
              <div style={{display:'flex',alignItems:'center',gap:7}}>
                <span style={{color:'var(--t1)'}}>{d.name}</span>
                {d.restaurantName&&<span className="badge b-blue" style={{fontSize:9.5}}>{d.restaurantName}</span>}
              </div>
              <span style={{color:'var(--blue-600)',fontWeight:500}}>{d.price?.toFixed(2)} ₽</span>
            </div>
          ))}
          <div style={{display:'flex',justifyContent:'space-between',padding:'10px 0 0',fontWeight:600,fontSize:13}}>
            <span>Итого</span><span style={{color:'var(--blue-600)'}}>{order.totalPrice?.toFixed(2)} ₽</span>
          </div>
        </div>
      </div>
    </Modal>
  );
}

export default function OrdersPage() {
  const [data, setData] = useState(null);
  const [page, setPage] = useState(0);
  const [search, setSearch] = useState('');
  const [loading, setLoading] = useState(true);
  const [modal, setModal] = useState(null);
  const [detail, setDetail] = useState(null);
  const [confirm, setConfirm] = useState(null);
  const toast = useToast();

  const load=useCallback(async()=>{
    setLoading(true);
    try{const res=search.trim()?await orderApi.searchByLastName(search.trim(),page):await orderApi.getAll(page);setData(res);}
    catch(err){toast(err.message,'error');}finally{setLoading(false);}
  },[page,search]);

  useEffect(()=>{load();},[load]);

  const handleCreate=async(form)=>{await orderApi.create(form);toast('Оформлен','success');setModal(null);load();};
  const handleUpdate=async(form)=>{await orderApi.update(modal.id,form);toast('Обновлено','success');setModal(null);load();};
  const handleDelete=async(id)=>{try{await orderApi.delete(id);toast('Удалено','success');load();}catch(err){toast(err.message,'error');}};

  return (
    <div className="page">
      <div className="page-header">
        <div>
          <div style={{display:'flex',alignItems:'center',gap:8}}>
            <div style={{width:3,height:18,borderRadius:2,background:'var(--e-ord)'}}/>
            <h2 className="page-title">Заказы</h2>
          </div>
          <p className="page-subtitle">Нажмите ID заказа, чтобы увидеть детали</p>
        </div>
        <button className="btn btn-primary" onClick={()=>setModal('create')}>+ Новый заказ</button>
      </div>
      <div className="search-bar">
        <div className="input-wrap"><span className="input-icon">⌕</span><input className="form-input input-pl" value={search} onChange={e=>{setSearch(e.target.value);setPage(0);}} placeholder="По фамилии клиента..."/></div>
        {search&&<button className="btn btn-ghost btn-sm" onClick={()=>{setSearch('');setPage(0);}}>Сбросить</button>}
      </div>
      <div className="tcard">
        {loading?<Loading/>:!data?.content?.length?<div className="empty-state" style={{color:'var(--t3)'}}><p>Не найдено</p></div>:(
          <div className="tbl-wrap">
            <table>
              <thead><tr><th>ID</th><th>Клиент</th><th>Статус</th><th className="hide-sm">Блюд</th><th>Сумма</th><th className="hide-sm">Дата</th><th/></tr></thead>
              <tbody>
                {data.content.map(o=>(
                  <tr key={o.id}>
                    <td><button style={{background:'none',border:'none',color:'var(--blue-600)',cursor:'pointer',fontWeight:600,fontSize:12.5,padding:0,textDecoration:'underline',textUnderlineOffset:2}} onClick={()=>setDetail(o.id)}>#{o.id}</button></td>
                    <td><strong>{o.customerFirstName} {o.customerLastName}</strong></td>
                    <td><StatusBadge status={o.status} white/></td>
                    <td>{o.amount}</td>
                    <td><span style={{color:'var(--blue-600)',fontWeight:600}}>{o.totalPrice?.toFixed(2)} ₽</span></td>
                    <td className="hide-sm"><span className="text-muted">{new Date(o.createdAt).toLocaleDateString('ru')}</span></td>
                    <td><div className="actions-cell">
                      <button className="btn btn-ghost btn-icon btn-sm" onClick={()=>setModal(o)}>✏️</button>
                      <button className="btn btn-danger btn-icon btn-sm" onClick={()=>setConfirm(o.id)}>🗑️</button>
                    </div></td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
        <div style={{padding:'0 14px'}}><Pagination page={page} totalPages={data?.totalPages||0} onChange={setPage}/></div>
      </div>
      {modal==='create'&&<OrderForm onSave={handleCreate} onClose={()=>setModal(null)}/>}
      {modal&&modal!=='create'&&<OrderForm initial={{dishesId:modal.dishes?.map(d=>d.id)||[],address:modal.address,customerId:modal.customerId,restaurantId:''}} onSave={handleUpdate} onClose={()=>setModal(null)}/>}
      {detail&&<OrderDetail id={detail} onClose={()=>setDetail(null)}/>}
      {confirm&&<ConfirmModal msg="Удалить заказ?" onConfirm={()=>handleDelete(confirm)} onClose={()=>setConfirm(null)}/>}
    </div>
  );
}
