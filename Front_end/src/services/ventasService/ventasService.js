import apiClient from '../api/apiConfig';

export const ventasService = {
  getAll: () => {
    return apiClient.get('/ventas');
  },

  getById: (id) => {
    return apiClient.get(`/ventas/${id}`);
  },

  create: (venta) => {
    return apiClient.post('/ventas', venta);
  },

  update: (id, venta) => {
    return apiClient.put(`/ventas/${id}`, venta);
  },

  delete: (id) => {
    return apiClient.delete(`/ventas/${id}`);
  },

  getByDateRange: (startDate, endDate) => {
    return apiClient.get(`/ventas/range?start=${startDate}&end=${endDate}`);
  },
};

