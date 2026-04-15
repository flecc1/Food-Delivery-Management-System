import { useState, useEffect, useCallback } from 'react';
import { customerApi, orderApi } from '../api';
import { Modal, ConfirmModal, Pagination, Loading, Field, useToast, StatusBadge } from '../components/UI';

function CustomerForm({ initial, onSave, onClose }) {
  const [form, setForm] = useState(initial||{firstName:'',lastName:'',email:'',password:'',phoneNumber:''});
  const [saving, setSaving] = useState(false);
  const toast = useToast();
  const f = k=>e=>setForm(p=>({...p,[k]:e.target.value}));
  const submit = async () => {
    if(!form.firstName||!form.lastName||!form.email||!form.phoneNumber){toast('Заполните все поля','error');return;}
    if(!initial&&(!form.password||form.password.length<8)){toast('Пароль минимум 8 символов','error');return;}
    setSaving(true);
    try{await onSave(form);}catch(err){toast(err.message,'error');}finally{setSaving(false);}
  };
  return (
    <Modal title={initial?'Редактировать':'Новый клиент'} onClose={onClose}
      footer={<><button className="btn btn-ghost" onClick={onClose}>Отмена</button><button className="btn btn-primary" onClick={submit} disabled={saving}>{saving?'...':'Сохранить'}</button></>}>
      <div className="grid grid-2" style={{gap:0}}>
        <div style={{paddingRight:7}}><Field label="Имя"><input className="form-input" value={form.firstName} onChange={f('firstName')} placeholder="Иван" autoFocus /></Field></div>
        <div style={{paddingLeft:7}}><Field label="Фамилия"><input className="form-input" value={form.lastName} onChange={f('lastName')} placeholder="Иванов" /></Field></div>
      </div>
      <Field label="Email"><input className="form-input" type="email" value={form.email} onChange={f('email')} placeholder="ivan@example.com" /></Field>
      {!initial&&<Field label="Пароль"><input className="form-input" type="password" value={form.password} onChange={f('password')} placeholder="Минимум 8 символов" /></Field>}
      <Field label="Телефон"><input className="form-input" value={form.phoneNumber} onChange={f('phoneNumber')} placeholder="+375291234567" /></Field>
    </Modal>
  );
}

function CustomerOrders({ customer }) {
  const [orders, setOrders] = useState(null);
  useEffect(() => {
    orderApi.searchByLastName(customer.lastName,0,20)
      .then(r=>{
        const f=(r?.content||[]).filter(o=>o.customerId===customer.id||(o.customerFirstName===customer.firstName&&o.customerLastName===customer.lastName));
        setOrders(f);
      }).catch(()=>setOrders([]));
  }, [customer]);
  if (!orders) return <Loading />;
  return (
    <div>
      <div style={{fontSize:10,fontWeight:600,color:'var(--ct3)',letterSpacing:'0.7px',textTransform:'uppercase',marginBottom:10}}>
        История заказов — {orders.length}
      </div>
      {!orders.length ? <p style={{fontSize:12,color:'var(--ct3)'}}>Заказов не найдено</p> : (
        <div style={{display:'flex',flexDirection:'column',gap:6}}>
          {orders.map(o => (
            <div key={o.id} style={{display:'flex',alignItems:'center',justifyContent:'space-between',padding:'8px 12px',background:'rgba(255,255,255,0.08)',border:'1px solid rgba(255,255,255,0.12)',borderRadius:10,fontSize:12.5}}>
              <div style={{display:'flex',alignItems:'center',gap:10}}>
                <span style={{fontSize:11,color:'var(--ct3)',minWidth:40}}>#{o.id}</span>
                <span style={{color:'var(--ct2)'}}>{o.address?.slice(0,30)}{o.address?.length>30?'…':''}</span>
              </div>
              <div style={{display:'flex',alignItems:'center',gap:10}}>
                <span style={{fontSize:11,color:'var(--ct3)'}}>{new Date(o.createdAt).toLocaleDateString('ru')}</span>
                <StatusBadge status={o.status} />
                <span style={{fontWeight:700,color:'#7dd3fc',minWidth:60,textAlign:'right'}}>{o.totalPrice?.toFixed(2)} ₽</span>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

const PAL=['#60a5fa','#a78bfa','#2dd4bf','#38bdf8','#4ade80','#818cf8'];
const ac = name => PAL[(name?.charCodeAt(0)||0)%PAL.length];

export default function CustomersPage() {
  const [data, setData] = useState(null);
  const [page, setPage] = useState(0);
  const [sf, setSf] = useState('');
  const [sl, setSl] = useState('');
  const [loading, setLoading] = useState(true);
  const [openRow, setOpenRow] = useState(null);
  const [modal, setModal] = useState(null);
  const [confirm, setConfirm] = useState(null);
  const toast = useToast();

  const load = useCallback(async () => {
    setLoading(true);
    try{
      let res;
      if(sf.trim()) res=await customerApi.searchByFirstName(sf.trim(),page);
      else if(sl.trim()) res=await customerApi.searchByLastName(sl.trim(),page);
      else res=await customerApi.getAll(page,10);
      setData(res);
    }catch(err){toast(err.message,'error');}finally{setLoading(false);}
  },[page,sf,sl]);

  useEffect(()=>{load();},[load]);

  const handleCreate=async(form)=>{await customerApi.create(form);toast('Добавлен','success');setModal(null);load();};
  const handleUpdate=async(form)=>{await customerApi.update(modal.id,{...modal,...form});toast('Обновлено','success');setModal(null);load();};
  const handleDelete=async(id)=>{try{await customerApi.delete(id);toast('Удалено','success');load();}catch(err){toast(err.message,'error');}};

  return (
    <div className="page">
      <div className="page-header">
        <div>
          <div style={{display:'flex',alignItems:'center',gap:8}}>
            <div style={{width:3,height:18,borderRadius:2,background:'var(--e-cust)'}} />
            <h2 className="page-title">Клиенты</h2>
          </div>
          <p className="page-subtitle">Нажмите на строку — увидите историю заказов</p>
        </div>
        <button className="btn btn-primary" onClick={()=>setModal('create')}>+ Добавить</button>
      </div>
      <div className="search-bar">
        <div className="input-wrap"><span className="input-icon">⌕</span><input className="form-input input-pl" value={sf} onChange={e=>{setSf(e.target.value);setSl('');setPage(0);}} placeholder="По имени..."/></div>
        <div className="input-wrap"><span className="input-icon">⌕</span><input className="form-input input-pl" value={sl} onChange={e=>{setSl(e.target.value);setSf('');setPage(0);}} placeholder="По фамилии..."/></div>
        {(sf||sl)&&<button className="btn btn-ghost btn-sm" onClick={()=>{setSf('');setSl('');setPage(0);}}>Сбросить</button>}
      </div>

      <div className="tcard">
        {loading?<Loading/>:!data?.content?.length?<div className="empty-state" style={{color:'var(--t3)'}}><p>Не найдено</p></div>:(
          <div className="tbl-wrap">
            <table>
              <thead><tr><th style={{width:24}}/><th>Клиент</th><th>Email</th><th>Телефон</th><th style={{width:80}}/></tr></thead>
              <tbody>
                {data.content.map(c=>[
                  <tr key={c.id} className={`clickable${openRow===c.id?' open':''}`} onClick={()=>setOpenRow(openRow===c.id?null:c.id)}>
                    <td style={{paddingRight:0}}><div className={`chev${openRow===c.id?' open':''}`}>▶</div></td>
                    <td>
                      <div style={{display:'flex',alignItems:'center',gap:9}}>
                        <div style={{width:28,height:28,borderRadius:'50%',flexShrink:0,background:ac(c.firstName)+'20',border:`1.5px solid ${ac(c.firstName)}50`,display:'flex',alignItems:'center',justifyContent:'center',fontSize:10,fontWeight:700,color:ac(c.firstName)}}>
                          {c.firstName?.[0]}{c.lastName?.[0]}
                        </div>
                        <strong>{c.firstName} {c.lastName}</strong>
                      </div>
                    </td>
                    <td><span className="text-muted">{c.email}</span></td>
                    <td>{c.phoneNumber}</td>
                    <td onClick={e=>e.stopPropagation()}>
                      <div className="actions-cell">
                        <button className="btn btn-ghost btn-icon btn-sm" onClick={()=>setModal(c)}>✏️</button>
                        <button className="btn btn-danger btn-icon btn-sm" onClick={()=>setConfirm(c.id)}>🗑️</button>
                      </div>
                    </td>
                  </tr>,
                  openRow===c.id&&<tr key={`e${c.id}`} className="expand-row"><td colSpan={5}><div className="expand-panel"><CustomerOrders customer={c}/></div></td></tr>,
                ])}
              </tbody>
            </table>
          </div>
        )}
        <div style={{padding:'0 14px'}}><Pagination page={page} totalPages={data?.totalPages||0} onChange={setPage}/></div>
      </div>
      {modal==='create'&&<CustomerForm onSave={handleCreate} onClose={()=>setModal(null)}/>}
      {modal&&modal!=='create'&&<CustomerForm initial={modal} onSave={handleUpdate} onClose={()=>setModal(null)}/>}
      {confirm&&<ConfirmModal msg="Удалить клиента?" onConfirm={()=>handleDelete(confirm)} onClose={()=>setConfirm(null)}/>}
    </div>
  );
}
