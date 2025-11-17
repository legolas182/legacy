import React from 'react';
import Logo from '../../atoms/Logo/Logo';

const AuthHeader = ({ title, subtitle }) => {
  return (
    <div className="flex flex-col items-center gap-4 mb-8 text-center">
      <Logo />
      <h1 className="text-3xl font-bold text-slate-100">{title}</h1>
      {subtitle && <p className="text-[15px] text-slate-400 max-w-md">{subtitle}</p>}
    </div>
  );
};

export default AuthHeader;

