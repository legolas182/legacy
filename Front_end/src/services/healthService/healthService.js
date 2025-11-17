import apiClient from '../api/apiConfig';

export const healthService = {
  check: () => {
    return apiClient.get('/health');
  },
};

