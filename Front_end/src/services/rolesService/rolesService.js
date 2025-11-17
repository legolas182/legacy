import apiClient from '../api/apiConfig';

export const rolesService = {
  getAll: () => {
    return apiClient.get('/roles');
  },

  getById: (id) => {
    return apiClient.get(`/roles/${id}`);
  },
};

