// Formateadores de datos
export const formatters = {
  // Formatear fecha a formato local
  date: (dateString) => {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleDateString('es-ES', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
    });
  },

  // Formatear fecha y hora
  dateTime: (dateString) => {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleString('es-ES', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
  },

  // Formatear moneda
  currency: (amount, currency = 'USD') => {
    if (!amount && amount !== 0) return '';
    return new Intl.NumberFormat('es-ES', {
      style: 'currency',
      currency: currency,
    }).format(amount);
  },

  // Formatear número
  number: (number) => {
    if (!number && number !== 0) return '';
    return new Intl.NumberFormat('es-ES').format(number);
  },

  // Formatear porcentaje
  percentage: (value, decimals = 2) => {
    if (!value && value !== 0) return '';
    return `${value.toFixed(decimals)}%`;
  },

  // Truncar texto
  truncate: (text, maxLength = 50) => {
    if (!text) return '';
    if (text.length <= maxLength) return text;
    return `${text.substring(0, maxLength)}...`;
  },
};

