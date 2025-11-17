import React from 'react';
import Logo from '../../atoms/Logo/Logo';
import './AuthHeader.css';

const AuthHeader = ({ title, subtitle }) => {
  return (
    <div className="auth-header">
      <Logo />
      <h1 className="auth-header-title">{title}</h1>
      <p className="auth-header-subtitle">{subtitle}</p>
    </div>
  );
};

export default AuthHeader;

