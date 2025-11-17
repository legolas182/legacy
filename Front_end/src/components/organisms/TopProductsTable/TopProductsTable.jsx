import React from 'react';

const TopProductsTable = ({ products }) => {
  const getStockBadge = (stock) => {
    if (stock === 'Bajo' || stock === 'bajo') {
      return <span className="inline-flex items-center rounded-full bg-red-500/20 px-3 py-1 text-xs font-medium text-red-400">Bajo</span>;
    } else if (stock === 'Medio' || stock === 'medio') {
      return <span className="inline-flex items-center rounded-full bg-yellow-500/20 px-3 py-1 text-xs font-medium text-yellow-400">Medio</span>;
    } else if (stock === 'Alto' || stock === 'alto') {
      return <span className="inline-flex items-center rounded-full bg-green-500/20 px-3 py-1 text-xs font-medium text-green-400">Alto</span>;
    }
    return <span className="inline-flex items-center rounded-full bg-gray-500/20 px-3 py-1 text-xs font-medium text-gray-400">{stock}</span>;
  };

  const defaultProducts = [
    { nombre: 'Analgésico Fuerte 500mg', unidadesVendidas: 352, ingresosTotales: 1760.00, stock: 'Bajo' },
    { nombre: 'Jarabe para la Tos Infantil', unidadesVendidas: 289, ingresosTotales: 2312.00, stock: 'Medio' },
    { nombre: 'Vitamina C Efervescente', unidadesVendidas: 251, ingresosTotales: 1506.00, stock: 'Alto' },
    { nombre: 'Crema Antihistamínica', unidadesVendidas: 198, ingresosTotales: 1188.00, stock: 'Medio' },
    { nombre: 'Protector Solar FPS 50+', unidadesVendidas: 175, ingresosTotales: 2100.00, stock: 'Bajo' }
  ];

  const productsToShow = products && products.length > 0 ? products : defaultProducts;

  return (
    <div className="mt-6 flex flex-col gap-2 rounded-lg border border-white/10 bg-[#182535] p-6">
      <h3 className="text-white text-lg font-semibold">Top 5 Productos Más Vendidos (Este Mes)</h3>
      <div className="overflow-x-auto">
        <table className="w-full text-left">
          <thead>
            <tr className="border-b border-b-white/10">
              <th className="px-4 py-3 text-white/90 text-sm font-medium">Producto</th>
              <th className="px-4 py-3 text-white/90 text-sm font-medium text-right">Unidades Vendidas</th>
              <th className="px-4 py-3 text-white/90 text-sm font-medium text-right">Ingresos Totales</th>
              <th className="px-4 py-3 text-white/90 text-sm font-medium text-center">Stock Actual</th>
            </tr>
          </thead>
          <tbody>
            {productsToShow.map((product, index) => (
              <tr key={index} className={`${index !== productsToShow.length - 1 ? 'border-b border-b-white/10' : ''} hover:bg-white/5 transition-colors`}>
                <td className="px-4 py-4 text-white text-sm font-normal">{product.nombre}</td>
                <td className="px-4 py-4 text-white/70 text-sm font-normal text-right">{product.unidadesVendidas}</td>
                <td className="px-4 py-4 text-white/70 text-sm font-normal text-right">${product.ingresosTotales.toFixed(2)}</td>
                <td className="px-4 py-4 text-sm font-normal text-center">
                  {getStockBadge(product.stock)}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default TopProductsTable;

