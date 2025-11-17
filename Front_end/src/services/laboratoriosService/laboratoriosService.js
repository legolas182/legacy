import apiClient from '../api/apiConfig';

export const laboratoriosService = {
  getAll: () => {
    return apiClient.get('/laboratorios');
  },

  getById: (id) => {
    return apiClient.get(`/laboratorios/${id}`);
  },

  create: (laboratorio) => {
    return apiClient.post('/laboratorios', laboratorio);
  },

  update: (id, laboratorio) => {
    return apiClient.put(`/laboratorios/${id}`, laboratorio);
  },

  delete: (id) => {
    return apiClient.delete(`/laboratorios/${id}`);
  },
};

