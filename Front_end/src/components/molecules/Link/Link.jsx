import React from 'react';
import { Link as RouterLink } from 'react-router-dom';
import './Link.css';

const Link = ({ to, children, variant = 'default' }) => {
  return (
    <RouterLink to={to} className={`link link--${variant}`}>
      {children}
    </RouterLink>
  );
};

export default Link;

