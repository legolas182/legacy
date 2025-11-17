import React from 'react';
import Input from '../../atoms/Input/Input';
import './FormField.css';

const FormField = ({ label, error, ...inputProps }) => {
  return (
    <div className="form-field">
      {label && <label className="form-field-label">{label}</label>}
      <Input {...inputProps} />
      {error && <span className="form-field-error">{error}</span>}
    </div>
  );
};

export default FormField;

