import apiClient from '../api/apiConfig';

export const ventasDetalleService = {
  getAll: () => {
    return apiClient.get('/ventas-detalle');
  },

  getById: (id) => {
    return apiClient.get(`/ventas-detalle/${id}`);
  },

  create: (ventaDetalle) => {
    return apiClient.post('/ventas-detalle', ventaDetalle);
  },

  update: (id, ventaDetalle) => {
    return apiClient.put(`/ventas-detalle/${id}`, ventaDetalle);
  },

  delete: (id) => {
    return apiClient.delete(`/ventas-detalle/${id}`);
  },
};

