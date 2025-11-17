import apiClient from '../api/apiConfig';

export const sucursalesService = {
  getAll: () => {
    return apiClient.get('/sucursales');
  },

  getById: (id) => {
    return apiClient.get(`/sucursales/${id}`);
  },
};

