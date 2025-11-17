import React from 'react';
import Input from '../../atoms/Input/Input';

const FormField = ({ label, error, ...inputProps }) => {
  return (
    <div className="flex flex-col gap-2 w-full">
      {label && <label className="text-sm font-medium text-slate-300">{label}</label>}
      <Input {...inputProps} />
      {error && <span className="text-sm text-red-400 mt-1">{error}</span>}
    </div>
  );
};

export default FormField;

