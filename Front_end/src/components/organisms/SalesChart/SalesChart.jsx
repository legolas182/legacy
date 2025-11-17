import React, { useState } from 'react';

const SalesChart = ({ data }) => {
  const [activeTab, setActiveTab] = useState('year');

  return (
    <div className="lg:col-span-2 flex flex-col gap-2 rounded-lg border border-white/10 bg-[#182535] p-6">
      <div className="flex justify-between items-start flex-wrap gap-4">
        <div>
          <p className="text-white/90 text-base font-medium">Evolución de Ventas Mensuales</p>
          <div className="flex items-baseline gap-2">
            <p className="text-white text-4xl font-bold tracking-tight truncate">$315,400.00</p>
            <p className="text-[#0bda5e] text-base font-medium">+15.3%</p>
          </div>
          <p className="text-white/60 text-sm font-normal">Este Año</p>
        </div>
        <div className="flex items-center gap-1 rounded-md bg-background-dark p-1 text-sm">
          <button 
            onClick={() => setActiveTab('30days')}
            className={`px-2 py-1 rounded transition-colors ${activeTab === '30days' ? 'bg-primary/30 text-primary font-semibold' : 'text-white/60 hover:bg-white/10'}`}
          >
            30 Días
          </button>
          <button 
            onClick={() => setActiveTab('month')}
            className={`px-2 py-1 rounded transition-colors ${activeTab === 'month' ? 'bg-primary/30 text-primary font-semibold' : 'text-white/60 hover:bg-white/10'}`}
          >
            Este Mes
          </button>
          <button 
            onClick={() => setActiveTab('year')}
            className={`px-2 py-1 rounded transition-colors ${activeTab === 'year' ? 'bg-primary/30 text-primary font-semibold' : 'text-white/60 hover:bg-white/10'}`}
          >
            Este Año
          </button>
        </div>
      </div>
      <div className="flex flex-1 flex-col justify-end min-h-[250px] py-4">
        <svg fill="none" height="100%" preserveAspectRatio="none" viewBox="0 0 472 150" width="100%" xmlns="http://www.w3.org/2000/svg">
          <path d="M0 109C18.1538 109 18.1538 21 36.3077 21C54.4615 21 54.4615 41 72.6154 41C90.7692 41 90.7692 93 108.923 93C127.077 93 127.077 33 145.231 33C163.385 33 163.385 101 181.538 101C199.692 101 199.692 61 217.846 61C236 61 236 45 254.154 45C272.308 45 272.308 121 290.462 121C308.615 121 308.615 149 326.769 149C344.923 149 344.923 1 363.077 1C381.231 1 381.231 81 399.385 81C417.538 81 417.538 129 435.692 129C453.846 129 453.846 25 472 25V149H0V109Z" fill="url(#paint0_linear_chart)"></path>
          <path d="M0 109C18.1538 109 18.1538 21 36.3077 21C54.4615 21 54.4615 41 72.6154 41C90.7692 41 90.7692 93 108.923 93C127.077 93 127.077 33 145.231 33C163.385 33 163.385 101 181.538 101C199.692 101 199.692 61 217.846 61C236 61 236 45 254.154 45C272.308 45 272.308 121 290.462 121C308.615 121 308.615 149 326.769 149C344.923 149 344.923 1 363.077 1C381.231 1 381.231 81 399.385 81C417.538 81 417.538 129 435.692 129C453.846 129 453.846 25 472 25" stroke="#51a0fb" strokeLinecap="round" strokeWidth="3"></path>
          <defs>
            <linearGradient gradientUnits="userSpaceOnUse" id="paint0_linear_chart" x1="236" x2="236" y1="1" y2="149">
              <stop stopColor="#51a0fb" stopOpacity="0.3"></stop>
              <stop offset="1" stopColor="#51a0fb" stopOpacity="0"></stop>
            </linearGradient>
          </defs>
        </svg>
        <div className="flex justify-around pt-2">
          <p className="text-white/60 text-xs font-bold uppercase">Ene</p>
          <p className="text-white/60 text-xs font-bold uppercase">Feb</p>
          <p className="text-white/60 text-xs font-bold uppercase">Mar</p>
          <p className="text-white/60 text-xs font-bold uppercase">Abr</p>
          <p className="text-white/60 text-xs font-bold uppercase">May</p>
          <p className="text-white/60 text-xs font-bold uppercase">Jun</p>
          <p className="text-white/60 text-xs font-bold uppercase">Jul</p>
          <p className="text-white/60 text-xs font-bold uppercase">Ago</p>
          <p className="text-white/60 text-xs font-bold uppercase">Sep</p>
          <p className="text-white/60 text-xs font-bold uppercase">Oct</p>
          <p className="text-white/60 text-xs font-bold uppercase">Nov</p>
          <p className="text-white/60 text-xs font-bold uppercase">Dic</p>
        </div>
      </div>
    </div>
  );
};

export default SalesChart;

