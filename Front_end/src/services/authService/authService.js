import apiClient from '../api/apiConfig';

export const authService = {
  login: (credentials) => {
    return apiClient.post('/auth/login', credentials);
  },

  logout: () => {
    return apiClient.post('/auth/logout');
  },

  getCurrentUser: () => {
    return apiClient.get('/auth/me');
  },
};

