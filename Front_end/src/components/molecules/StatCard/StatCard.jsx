import React from 'react';

const StatCard = ({ title, value, change, trend }) => {
  const trendColor = trend === 'up' ? 'text-[#0bda5e]' : 'text-[#fa6238]';
  
  return (
    <div className="flex flex-col gap-2 rounded-lg p-6 bg-[#182535] border border-white/10">
      <p className="text-white/90 text-base font-medium">{title}</p>
      <p className="text-white text-3xl font-bold tracking-tight">{value}</p>
      <p className={`${trendColor} text-sm font-medium`}>{change}</p>
    </div>
  );
};

export default StatCard;

