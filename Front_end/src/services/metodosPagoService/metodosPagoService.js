import apiClient from '../api/apiConfig';

export const metodosPagoService = {
  getAll: () => {
    return apiClient.get('/metodos-pago');
  },

  getById: (id) => {
    return apiClient.get(`/metodos-pago/${id}`);
  },

  create: (metodoPago) => {
    return apiClient.post('/metodos-pago', metodoPago);
  },

  update: (id, metodoPago) => {
    return apiClient.put(`/metodos-pago/${id}`, metodoPago);
  },

  delete: (id) => {
    return apiClient.delete(`/metodos-pago/${id}`);
  },
};

