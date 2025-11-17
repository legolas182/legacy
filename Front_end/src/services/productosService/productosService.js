import apiClient from '../api/apiConfig';

export const productosService = {
  getAll: () => {
    return apiClient.get('/productos');
  },

  getById: (id) => {
    return apiClient.get(`/productos/${id}`);
  },

  create: (producto) => {
    return apiClient.post('/productos', producto);
  },

  update: (id, producto) => {
    return apiClient.put(`/productos/${id}`, producto);
  },

  delete: (id) => {
    return apiClient.delete(`/productos/${id}`);
  },

  search: (query) => {
    return apiClient.get(`/productos/search?q=${query}`);
  },
};

