import { useState } from 'react';

export const useForm = (initialValues = {}) => {
  const [values, setValues] = useState(initialValues);
  const [errors, setErrors] = useState({});

  const handleChange = (e) => {
    const { name, value } = e.target;
    setValues({
      ...values,
      [name]: value,
    });

    // Limpiar error del campo cuando el usuario escribe
    if (errors[name]) {
      setErrors({
        ...errors,
        [name]: '',
      });
    }
  };

  const handleReset = () => {
    setValues(initialValues);
    setErrors({});
  };

  const setFieldError = (field, error) => {
    setErrors({
      ...errors,
      [field]: error,
    });
  };

  const setFieldValue = (field, value) => {
    setValues({
      ...values,
      [field]: value,
    });
  };

  return {
    values,
    errors,
    handleChange,
    handleReset,
    setFieldError,
    setFieldValue,
    setErrors,
    setValues,
  };
};

