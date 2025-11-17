import React from 'react';
import { Link as RouterLink } from 'react-router-dom';

const Link = ({ to, children, variant = 'default' }) => {
  const variantClasses = variant === 'default' 
    ? 'text-primary hover:text-primary/80'
    : 'text-slate-500 hover:text-slate-400';

  return (
    <RouterLink to={to} className={`text-sm font-medium transition-colors ${variantClasses}`}>
      {children}
    </RouterLink>
  );
};

export default Link;

