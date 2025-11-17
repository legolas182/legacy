export const validators = {
  email: (value) => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(value);
  },

  password: (value, minLength = 6) => {
    return value && value.length >= minLength;
  },

  required: (value) => {
    if (typeof value === 'string') {
      return value.trim().length > 0;
    }
    return value !== null && value !== undefined;
  },

  minLength: (value, length) => {
    return value && value.length >= length;
  },

  maxLength: (value, length) => {
    return value && value.length <= length;
  },

  match: (value1, value2) => {
    return value1 === value2;
  },
};

