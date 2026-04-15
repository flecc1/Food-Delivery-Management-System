import { useState, useEffect, createContext, useContext } from 'react';

const ToastCtx = createContext(null);
export function useToast() { return useContext(ToastCtx); }

export function ToastProvider({ children }) {
  const [toasts, setToasts] = useState([]);
  const show = (msg, type = 'info') => {
    const id = Date.now();
    setToasts(t => [...t, { id, msg, type }]);
    setTimeout(() => setToasts(t => t.filter(x => x.id !== id)), 3200);
  };
  return (
    <ToastCtx.Provider value={show}>
      {children}
      <div className="toast-container">
        {toasts.map(t => (
          <div key={t.id} className={`toast toast-${t.type}`}>
            <span className="t-icon">{t.type === 'success' ? '✓' : t.type === 'error' ? '✕' : '·'}</span>
            {t.msg}
          </div>
        ))}
      </div>
    </ToastCtx.Provider>
  );
}

export function Modal({ title, onClose, children, footer, wide }) {
  useEffect(() => {
    const h = e => { if (e.key === 'Escape') onClose(); };
    window.addEventListener('keydown', h);
    return () => window.removeEventListener('keydown', h);
  }, [onClose]);
  return (
    <div className="modal-backdrop" onClick={e => e.target === e.currentTarget && onClose()}>
      <div className={`modal${wide ? ' modal-wide' : ''}`}>
        <div className="modal-header">
          <span className="modal-title">{title}</span>
          <button className="btn btn-ghost btn-icon btn-sm" onClick={onClose}>✕</button>
        </div>
        <div className="modal-body">{children}</div>
        {footer && <div className="modal-footer">{footer}</div>}
      </div>
    </div>
  );
}

export function ConfirmModal({ msg, onConfirm, onClose }) {
  return (
    <Modal title="Удалить?" onClose={onClose}
      footer={<>
        <button className="btn btn-ghost" onClick={onClose}>Отмена</button>
        <button className="btn btn-danger" onClick={() => { onConfirm(); onClose(); }}>Удалить</button>
      </>}>
      <p style={{ color: 'var(--t2)', fontSize: 13 }}>{msg}</p>
    </Modal>
  );
}

export function Pagination({ page, totalPages, onChange }) {
  if (totalPages <= 1) return null;
  const pages = [];
  const s = Math.max(0, page - 2), e = Math.min(totalPages - 1, page + 2);
  for (let i = s; i <= e; i++) pages.push(i);
  return (
    <div className="pagination">
      <button className="page-btn" disabled={page === 0} onClick={() => onChange(page - 1)}>‹</button>
      {s > 0 && <><button className="page-btn" onClick={() => onChange(0)}>1</button><span style={{ color:'var(--t3)', padding:'0 2px' }}>…</span></>}
      {pages.map(p => <button key={p} className={`page-btn${p === page ? ' active' : ''}`} onClick={() => onChange(p)}>{p + 1}</button>)}
      {e < totalPages - 1 && <><span style={{ color:'var(--t3)', padding:'0 2px' }}>…</span><button className="page-btn" onClick={() => onChange(totalPages - 1)}>{totalPages}</button></>}
      <button className="page-btn" disabled={page >= totalPages - 1} onClick={() => onChange(page + 1)}>›</button>
    </div>
  );
}

export function Loading() {
  return <div className="loading"><div className="spinner" /><span>Загрузка</span></div>;
}

export function Field({ label, children, error }) {
  return (
    <div className="form-group">
      {label && <label className="form-label">{label}</label>}
      {children}
      {error && <p className="error-msg">{error}</p>}
    </div>
  );
}

// On glass: use bg-* classes; on white: use b-* classes
const STATUS = {
  PENDING:    ['bg-gray',   'Ожидание'],
  CONFIRMED:  ['bg-blue',   'Подтверждён'],
  PREPARING:  ['bg-amber',  'Готовится'],
  DELIVERING: ['bg-violet', 'В пути'],
  DELIVERED:  ['bg-green',  'Доставлен'],
  CANCELLED:  ['bg-red',    'Отменён'],
};
export function StatusBadge({ status, white }) {
  const map = white ? {
    PENDING: ['b-gray','Ожидание'], CONFIRMED: ['b-blue','Подтверждён'],
    PREPARING: ['b-amber','Готовится'], DELIVERING: ['b-violet','В пути'],
    DELIVERED: ['b-green','Доставлен'], CANCELLED: ['b-red','Отменён'],
  } : STATUS;
  const [cls, label] = map[status] || (white ? ['b-gray', status||'—'] : ['bg-gray', status||'—']);
  return <span className={`badge ${cls}`}>{label}</span>;
}
