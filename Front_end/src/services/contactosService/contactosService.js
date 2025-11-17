import apiClient from '../api/apiConfig';

export const contactosService = {
  getAll: () => {
    return apiClient.get('/contactos');
  },

  getById: (id) => {
    return apiClient.get(`/contactos/${id}`);
  },

  create: (contacto) => {
    return apiClient.post('/contactos', contacto);
  },

  update: (id, contacto) => {
    return apiClient.put(`/contactos/${id}`, contacto);
  },

  delete: (id) => {
    return apiClient.delete(`/contactos/${id}`);
  },
};

