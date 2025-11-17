import React from 'react';
import { useAuth } from '../../../contexts/AuthContext/AuthContext';

const Header = () => {
  const { user, logout } = useAuth();

  const handleLogout = () => {
    logout();
    window.location.href = '/login';
  };

  return (
    <header className="sticky top-0 z-30 flex items-center justify-between border-b border-solid border-white/10 bg-[#1e2a3a]/80 px-4 py-3 backdrop-blur-sm sm:px-6 lg:px-8">
      {/* Left side - Empty for mobile menu button space */}
      <div className="w-10 lg:hidden"></div>
      
      {/* Right side - User actions */}
      <div className="flex items-center gap-4 ml-auto">
        <button className="flex h-10 w-10 cursor-pointer items-center justify-center overflow-hidden rounded-lg bg-white/10 text-white transition-colors hover:bg-white/20">
          <span className="material-symbols-outlined text-xl">notifications</span>
        </button>
        <div 
          className="bg-center bg-no-repeat aspect-square bg-cover rounded-full size-10 cursor-pointer"
          onClick={handleLogout}
          title={`${user?.nombre || user?.username} - Click para cerrar sesión`}
          style={{backgroundImage: 'url("https://ui-avatars.com/api/?name=' + encodeURIComponent(user?.nombre || user?.username || 'U') + '&background=51a0fb&color=fff")'}}
        />
      </div>
    </header>
  );
};

export default Header;

