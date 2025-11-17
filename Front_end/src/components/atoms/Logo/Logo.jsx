import React from 'react';

const Logo = ({ size = 'medium' }) => {
  const sizeClasses = {
    small: { svg: 'w-6 h-6', text: 'text-lg' },
    medium: { svg: 'w-8 h-8', text: 'text-2xl' },
    large: { svg: 'w-12 h-12', text: 'text-3xl' }
  };

  const classes = sizeClasses[size];

  return (
    <div className="flex items-center gap-3 text-primary">
      <svg className={classes.svg} xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round">
        <path d="M4.5 16.5c-1.5 1.26-2 5-2 5s3.74-.5 5-2c.71-.84.7-2.13-.09-2.91a2.18 2.18 0 0 0-2.91-.09z" />
        <path d="m12 15-3-3a22 22 0 0 1 2-3.95A12.88 12.88 0 0 1 22 2c0 2.72-.78 7.5-6 11a22.35 22.35 0 0 1-4 2z" />
        <path d="M9 12H4s.55-3.03 2-4c1.62-1.08 5 0 5 0" />
        <path d="M12 15v5s3.03-.55 4-2c1.08-1.62 0-5 0-5" />
      </svg>
      <span className={`${classes.text} font-bold text-slate-100`}>Legacy Pharmacy</span>
    </div>
  );
};

export default Logo;

