import React, { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../../../contexts/AuthContext/AuthContext';

const Sidebar = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { logout } = useAuth();
  const [isMobileMenuOpen, setIsMobileMenuOpen] = useState(false);

  const menuItems = [
    { icon: 'dashboard', label: 'Dashboard', path: '/dashboard' },
    { icon: 'shopping_cart', label: 'Ventas', path: '/ventas' },
    { icon: 'shopping_bag', label: 'Compras', path: '/compras' },
    { icon: 'inventory_2', label: 'Inventario', path: '/inventario' },
    { icon: 'medication', label: 'Productos', path: '/productos' },
    { icon: 'bar_chart', label: 'Reportes', path: '/reportes' },
    { icon: 'settings', label: 'Configuración', path: '/configuracion' },
  ];

  const isActive = (path) => location.pathname === path;

  const handleNavigation = (path) => {
    navigate(path);
    setIsMobileMenuOpen(false);
  };

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <>
      {/* Mobile Menu Button */}
      <button
        className="lg:hidden fixed top-4 left-4 z-50 p-2 rounded-lg bg-[#1e2a3a] text-white"
        onClick={() => setIsMobileMenuOpen(!isMobileMenuOpen)}
      >
        <span className="material-symbols-outlined">menu</span>
      </button>

      {/* Overlay for mobile */}
      {isMobileMenuOpen && (
        <div
          className="lg:hidden fixed inset-0 bg-black/50 z-40"
          onClick={() => setIsMobileMenuOpen(false)}
        />
      )}

      {/* Sidebar */}
      <aside
        className={`
          fixed lg:static inset-y-0 left-0 z-40
          w-64 bg-[#1e2a3a] text-white
          flex flex-col
          transition-transform duration-300 ease-in-out
          ${isMobileMenuOpen ? 'translate-x-0' : '-translate-x-full lg:translate-x-0'}
        `}
      >
        {/* Header */}
        <div className="flex items-center gap-3 p-6 border-b border-white/10">
          <div className="flex items-center justify-center w-10 h-10 rounded-lg bg-primary">
            <span className="material-symbols-outlined text-white text-2xl">local_pharmacy</span>
          </div>
          <div>
            <h1 className="text-lg font-bold">Legacy Pharmacy</h1>
            <p className="text-xs text-white/60">Gestión de Farmacia</p>
          </div>
        </div>

        {/* Navigation Menu */}
        <nav className="flex-1 py-6 px-3 overflow-y-auto">
          <ul className="space-y-1">
            {menuItems.map((item) => (
              <li key={item.path}>
                <button
                  onClick={() => handleNavigation(item.path)}
                  className={`
                    w-full flex items-center gap-3 px-4 py-3 rounded-lg
                    transition-all duration-200
                    ${
                      isActive(item.path)
                        ? 'bg-[#2d4a5c] text-white font-medium'
                        : 'text-white/70 hover:bg-white/5 hover:text-white'
                    }
                  `}
                >
                  <span className="material-symbols-outlined text-xl">
                    {item.icon}
                  </span>
                  <span className="text-sm">{item.label}</span>
                </button>
              </li>
            ))}
          </ul>
        </nav>

        {/* Nueva Venta Button */}
        <div className="px-3 pb-4">
          <button
            onClick={() => handleNavigation('/ventas/nueva')}
            className="w-full bg-primary hover:bg-primary/90 text-white font-semibold py-3 px-4 rounded-lg transition-all duration-200 hover:shadow-lg hover:shadow-primary/30"
          >
            Nueva Venta
          </button>
        </div>

        {/* Footer Actions */}
        <div className="border-t border-white/10 p-3 space-y-1">
          <button
            onClick={() => handleNavigation('/ayuda')}
            className="w-full flex items-center gap-3 px-4 py-3 rounded-lg text-white/70 hover:bg-white/5 hover:text-white transition-all duration-200"
          >
            <span className="material-symbols-outlined text-xl">help</span>
            <span className="text-sm">Ayuda</span>
          </button>
          <button
            onClick={handleLogout}
            className="w-full flex items-center gap-3 px-4 py-3 rounded-lg text-white/70 hover:bg-white/5 hover:text-white transition-all duration-200"
          >
            <span className="material-symbols-outlined text-xl">logout</span>
            <span className="text-sm">Salir</span>
          </button>
        </div>
      </aside>
    </>
  );
};

export default Sidebar;

