import { BrowserRouter, Routes, Route } from 'react-router-dom';
import { ToastProvider } from './components/UI';
import Header        from './components/Header';
import DashboardPage   from './pages/DashboardPage';
import RestaurantsPage from './pages/RestaurantsPage';
import CategoriesPage  from './pages/CategoriesPage';
import DishesPage      from './pages/DishesPage';
import MenusPage       from './pages/MenusPage';
import CustomersPage   from './pages/CustomersPage';
import OrdersPage      from './pages/OrdersPage';
import './index.css';

export default function App() {
  return (
    <BrowserRouter>
      <ToastProvider>
        <Header />
        <div className="app-body">
          <Routes>
            <Route path="/"            element={<DashboardPage />} />
            <Route path="/restaurants" element={<RestaurantsPage />} />
            <Route path="/categories"  element={<CategoriesPage />} />
            <Route path="/dishes"      element={<DishesPage />} />
            <Route path="/menus"       element={<MenusPage />} />
            <Route path="/customers"   element={<CustomersPage />} />
            <Route path="/orders"      element={<OrdersPage />} />
          </Routes>
        </div>
      </ToastProvider>
    </BrowserRouter>
  );
}
