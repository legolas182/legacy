import React from 'react';

const Spinner = ({ size = 'medium', color = 'primary' }) => {
  const sizeClasses = {
    small: 'w-4 h-4',
    medium: 'w-8 h-8',
    large: 'w-12 h-12',
  };

  const colorClasses = {
    primary: 'border-primary',
    white: 'border-white',
    blue: 'border-blue-500',
  };

  return (
    <div className={`inline-block ${sizeClasses[size] || sizeClasses.medium}`}>
      <div
        className={`
          ${sizeClasses[size] || sizeClasses.medium}
          ${colorClasses[color] || colorClasses.primary}
          border-4 border-t-transparent rounded-full
          animate-spin
        `}
      />
    </div>
  );
};

export default Spinner;

