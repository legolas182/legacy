import React, { useState, useEffect } from 'react';
import { useAuth } from '../../../contexts/AuthContext/AuthContext';
import Button from '../../../components/atoms/Button/Button';
import Logo from '../../../components/atoms/Logo/Logo';
import { healthService } from '../../../services/healthService/healthService';
import './DashboardPage.css';

const DashboardPage = () => {
  const { user, logout } = useAuth();
  const [backendStatus, setBackendStatus] = useState(null);

  useEffect(() => {
    // Verificar conexión con el backend
    const checkBackendHealth = async () => {
      try {
        const response = await healthService.check();
        setBackendStatus({
          status: 'success',
          data: response.data
        });
        console.log('✅ Backend conectado:', response.data);
      } catch (error) {
        setBackendStatus({
          status: 'error',
          message: error.message
        });
        console.error('❌ Error de conexión con backend:', error);
      }
    };

    checkBackendHealth();
  }, []);

  const handleLogout = () => {
    logout();
    window.location.href = '/login';
  };

  return (
    <div className="dashboard-page">
      <header className="dashboard-header">
        <Logo />
        <div className="dashboard-user">
          <span>Bienvenido, {user?.fullName || user?.username}</span>
          <Button onClick={handleLogout} variant="secondary">
            Cerrar Sesión
          </Button>
        </div>
      </header>

      <main className="dashboard-content">
        <div className="dashboard-welcome">
          <h1>Dashboard</h1>
          <p>Sistema de gestión de farmacia Legacy Pharmacy</p>
          
          {backendStatus && (
            <div className={`backend-status ${backendStatus.status === 'success' ? 'status-success' : 'status-error'}`}>
              {backendStatus.status === 'success' ? (
                <>
                  <span>🟢 Backend: {backendStatus.data.message}</span>
                </>
              ) : (
                <span>🔴 Backend desconectado - {backendStatus.message}</span>
              )}
            </div>
          )}
        </div>

        <div className="dashboard-grid">
          <div className="dashboard-card">
            <h3>Productos</h3>
            <p>Gestión de inventario de medicamentos</p>
          </div>
          <div className="dashboard-card">
            <h3>Ventas</h3>
            <p>Registro y seguimiento de ventas</p>
          </div>
          <div className="dashboard-card">
            <h3>Compras</h3>
            <p>Control de compras y proveedores</p>
          </div>
          <div className="dashboard-card">
            <h3>Reportes</h3>
            <p>Informes y análisis del sistema</p>
          </div>
        </div>
      </main>
    </div>
  );
};

export default DashboardPage;

