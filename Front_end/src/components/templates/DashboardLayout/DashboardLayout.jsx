import React from 'react';
import Sidebar from '../../organisms/Sidebar/Sidebar';
import Header from '../../organisms/Header/Header';

const DashboardLayout = ({ children }) => {
  return (
    <div className="dark min-h-screen bg-background-dark font-display flex">
      {/* Sidebar */}
      <Sidebar />

      {/* Main Content Area */}
      <div className="flex-1 flex flex-col min-h-screen lg:ml-0">
        {/* Header */}
        <Header />

        {/* Page Content */}
        <main className="flex-1 p-4 sm:p-5 lg:p-6 w-full">
          {children}
        </main>
      </div>
    </div>
  );
};

export default DashboardLayout;

