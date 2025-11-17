import React, { useState, useEffect } from 'react';
import DashboardLayout from '../../../components/templates/DashboardLayout/DashboardLayout';
import StatCard from '../../../components/molecules/StatCard/StatCard';
import SalesChart from '../../../components/organisms/SalesChart/SalesChart';
import AlertsPanel from '../../../components/organisms/AlertsPanel/AlertsPanel';
import TopProductsTable from '../../../components/organisms/TopProductsTable/TopProductsTable';
import { dashboardService } from '../../../services/dashboardService/dashboardService';

const DashboardPage = () => {
  // eslint-disable-next-line no-unused-vars
  const [dashboardData, setDashboardData] = useState(null);
  // eslint-disable-next-line no-unused-vars
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const loadDashboardData = async () => {
      try {
        // Intentar cargar datos del backend
        const response = await dashboardService.getResumen();
        setDashboardData(response.data);
      } catch (error) {
        console.log('Usando datos de ejemplo:', error);
        // Datos de ejemplo si el backend no está disponible
        setDashboardData(null);
      } finally {
        setLoading(false);
      }
    };

    loadDashboardData();
  }, []);

  // Datos de ejemplo
  const exampleAlerts = [
    {
      type: 'error',
      message: 'Analgésico Fuerte 500mg a punto de vencer.',
      detail: 'Vence en 3 días'
    },
    {
      type: 'warning',
      message: 'Factura de Proveedor "Distribuidora Médica" pendiente.',
      detail: 'Vence mañana'
    },
    {
      type: 'info',
      message: 'Bajo stock de "Vitamina C Efervescente".',
      detail: 'Quedan 5 unidades'
    }
  ];

  return (
    <DashboardLayout>
      <div className="w-full">
            {/* Page Heading */}
            <div className="flex flex-wrap justify-between items-center gap-4 mb-6">
              <div className="flex flex-col gap-1">
                <h2 className="text-white text-3xl font-bold tracking-tight">Resumen Ejecutivo</h2>
                <p className="text-white/60 text-base font-normal">Estado actual de la farmacia.</p>
              </div>
            </div>

            {/* Stats Cards */}
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-5 gap-4 mb-6">
              <StatCard 
                title="Ventas del Día"
                value="$1,250.00"
                change="+5% vs ayer"
                trend="up"
              />
              <StatCard 
                title="Ventas del Mes"
                value="$35,700.00"
                change="+2.1%"
                trend="up"
              />
              <StatCard 
                title="Ganancia Neta (Mes)"
                value="$12,450.00"
                change="-0.5%"
                trend="down"
              />
              <StatCard 
                title="Productos con Stock Bajo"
                value="8"
                change="+2"
                trend="up"
              />
              <StatCard 
                title="Alertas Activas"
                value="3"
                change="+1"
                trend="down"
              />
            </div>

            {/* Charts and Alerts */}
            <div className="grid grid-cols-1 lg:grid-cols-3 gap-6">
              <SalesChart />
              <AlertsPanel alerts={exampleAlerts} />
            </div>

            {/* Top Products Table */}
            <TopProductsTable />
          </div>
    </DashboardLayout>
  );
};

export default DashboardPage;

