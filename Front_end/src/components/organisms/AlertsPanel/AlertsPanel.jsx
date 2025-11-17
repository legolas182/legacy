import React from 'react';

const AlertsPanel = ({ alerts }) => {
  const getIconColor = (type) => {
    switch (type) {
      case 'error': return 'text-red-500';
      case 'warning': return 'text-yellow-500';
      case 'info': return 'text-blue-400';
      default: return 'text-gray-400';
    }
  };

  const getIcon = (type) => {
    switch (type) {
      case 'error': return 'error';
      case 'warning': return 'warning';
      case 'info': return 'inventory_2';
      default: return 'info';
    }
  };

  return (
    <div className="flex flex-col gap-4 rounded-lg border border-white/10 bg-[#182535] p-6">
      <h3 className="text-white text-lg font-semibold">Alertas Recientes</h3>
      <div className="flex flex-col gap-3">
        {alerts && alerts.length > 0 ? (
          alerts.map((alert, index) => (
            <div key={index} className="flex items-start gap-3 rounded-lg bg-background-dark p-3">
              <div className="flex-shrink-0 mt-1">
                <span className={`material-symbols-outlined text-xl ${getIconColor(alert.type)}`}>
                  {getIcon(alert.type)}
                </span>
              </div>
              <div>
                <p className="text-white text-sm font-medium">{alert.message}</p>
                <p className="text-white/60 text-xs">{alert.detail}</p>
              </div>
            </div>
          ))
        ) : (
          <div className="flex items-start gap-3 rounded-lg bg-background-dark p-3">
            <div className="flex-shrink-0 mt-1">
              <span className="material-symbols-outlined text-xl text-red-500">error</span>
            </div>
            <div>
              <p className="text-white text-sm font-medium">Analgésico Fuerte 500mg a punto de vencer.</p>
              <p className="text-white/60 text-xs">Vence en 3 días</p>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default AlertsPanel;

