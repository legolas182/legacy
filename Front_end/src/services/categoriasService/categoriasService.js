import apiClient from '../api/apiConfig';

export const categoriasService = {
  getAll: () => {
    return apiClient.get('/categorias');
  },

  getById: (id) => {
    return apiClient.get(`/categorias/${id}`);
  },

  create: (categoria) => {
    return apiClient.post('/categorias', categoria);
  },

  update: (id, categoria) => {
    return apiClient.put(`/categorias/${id}`, categoria);
  },

  delete: (id) => {
    return apiClient.delete(`/categorias/${id}`);
  },
};

