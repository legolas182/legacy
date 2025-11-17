import React from 'react';
import { useAuth } from '../../../contexts/AuthContext/AuthContext';

const Header = () => {
  const { user } = useAuth();

  return (
    <header className="sticky top-0 z-30 flex items-center justify-between border-b border-solid border-white/10 bg-[#1e2a3a]/80 px-4 py-3 backdrop-blur-sm sm:px-6 lg:px-8">
      {/* Left side - Empty for mobile menu button space */}
      <div className="w-10 lg:hidden"></div>
      
      {/* Right side - User actions */}
      <div className="flex items-center gap-4 ml-auto">
        <button className="flex h-10 w-10 cursor-pointer items-center justify-center overflow-hidden rounded-lg bg-white/10 text-white transition-colors hover:bg-white/20">
          <span className="material-symbols-outlined text-xl">notifications</span>
        </button>
        {user?.sucursalNombre && (
          <span className="text-white text-sm font-medium">
            {user.sucursalNombre}
          </span>
        )}
      </div>
    </header>
  );
};

export default Header;

