import React from 'react';

const Button = ({ 
  children, 
  type = 'button', 
  variant = 'primary', 
  fullWidth = false,
  onClick,
  disabled = false,
  ...props 
}) => {
  const baseClasses = 'px-6 py-3 rounded-lg font-semibold transition-all duration-300 focus:outline-none focus:ring-2 focus:ring-primary/50';
  const variantClasses = variant === 'primary' 
    ? 'bg-primary text-white hover:bg-primary/90 hover:-translate-y-0.5 hover:shadow-lg hover:shadow-primary/40'
    : 'bg-transparent text-primary border-2 border-primary hover:bg-primary/10';
  const widthClass = fullWidth ? 'w-full' : '';
  const disabledClass = disabled ? 'opacity-50 cursor-not-allowed hover:transform-none hover:shadow-none' : '';

  return (
    <button
      type={type}
      className={`${baseClasses} ${variantClasses} ${widthClass} ${disabledClass}`}
      onClick={onClick}
      disabled={disabled}
      {...props}
    >
      {children}
    </button>
  );
};

export default Button;

