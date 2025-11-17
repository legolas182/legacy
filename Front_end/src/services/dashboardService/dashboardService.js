import apiClient from '../api/apiConfig';

export const dashboardService = {
  getResumen: () => {
    return apiClient.get('/dashboard/resumen');
  },

  getVentasMensuales: () => {
    return apiClient.get('/dashboard/ventas-mensuales');
  },

  getTopProductos: () => {
    return apiClient.get('/dashboard/top-productos');
  },

  getAlertasRecientes: () => {
    return apiClient.get('/dashboard/alertas-recientes');
  },
};

