import React, { useState, useEffect } from 'react';
import DashboardLayout from '../../../components/templates/DashboardLayout/DashboardLayout';
import { ventasService } from '../../../services/ventasService/ventasService';
import { metodosPagoService } from '../../../services/metodosPagoService/metodosPagoService';
import StatCard from '../../../components/molecules/StatCard/StatCard';
import Button from '../../../components/atoms/Button/Button';
import Input from '../../../components/atoms/Input/Input';
import Spinner from '../../../components/atoms/Spinner/Spinner';

const VentasPage = () => {
  const [ventas, setVentas] = useState([]);
  const [ventasFiltradas, setVentasFiltradas] = useState([]);
  const [metodosPago, setMetodosPago] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Filtros
  const [busqueda, setBusqueda] = useState('');
  const [rangoFechas, setRangoFechas] = useState('');
  const [metodoPagoFiltro, setMetodoPagoFiltro] = useState('Todos');

  // Paginación
  const [paginaActual, setPaginaActual] = useState(1);
  const itemsPorPagina = 10;

  // Métricas
  const [metricas, setMetricas] = useState({
    ventasHoy: 0,
    ventasMes: 0,
    ventaPromedio: 0,
    totalTransacciones: 0,
  });

  useEffect(() => {
    loadData();
  }, []);

  useEffect(() => {
    aplicarFiltros();
  }, [busqueda, rangoFechas, metodoPagoFiltro, ventas]);

  const loadData = async () => {
    try {
      setLoading(true);
      setError(null);
      
      const [ventasResponse, metodosResponse] = await Promise.all([
        ventasService.getAll(),
        metodosPagoService.getAll(),
      ]);

      const ventasData = ventasResponse.data || [];
      setVentas(ventasData);
      setVentasFiltradas(ventasData);
      
      const metodosData = metodosResponse.data || [];
      setMetodosPago(metodosData);

      calcularMetricas(ventasData);
    } catch (err) {
      console.error('Error al cargar datos:', err);
      setError(err.response?.data?.message || 'Error al cargar las ventas');
    } finally {
      setLoading(false);
    }
  };

  const calcularMetricas = (ventasData) => {
    const hoy = new Date();
    hoy.setHours(0, 0, 0, 0);

    const inicioMes = new Date(hoy.getFullYear(), hoy.getMonth(), 1);

    const ventasHoy = ventasData.filter((venta) => {
      const fechaVenta = new Date(venta.fecha);
      fechaVenta.setHours(0, 0, 0, 0);
      return fechaVenta.getTime() === hoy.getTime();
    });

    const ventasMes = ventasData.filter((venta) => {
      const fechaVenta = new Date(venta.fecha);
      return fechaVenta >= inicioMes;
    });

    const totalHoy = ventasHoy.reduce((sum, v) => sum + (parseFloat(v.totalGeneral) || 0), 0);
    const totalMes = ventasMes.reduce((sum, v) => sum + (parseFloat(v.totalGeneral) || 0), 0);
    const totalGeneral = ventasData.reduce((sum, v) => sum + (parseFloat(v.totalGeneral) || 0), 0);
    const promedio = ventasData.length > 0 ? totalGeneral / ventasData.length : 0;

    setMetricas({
      ventasHoy: totalHoy,
      ventasMes: totalMes,
      ventaPromedio: promedio,
      totalTransacciones: ventasData.length,
    });
  };

  const aplicarFiltros = () => {
    let ventasFiltradas = [...ventas];

    // Filtro de búsqueda
    if (busqueda) {
      const busquedaLower = busqueda.toLowerCase();
      ventasFiltradas = ventasFiltradas.filter((venta) => {
        const idVenta = `VENTA-${String(venta.id).padStart(3, '0')}`;
        const nombreCliente = venta.cliente?.nombre || '';
        return (
          idVenta.toLowerCase().includes(busquedaLower) ||
          nombreCliente.toLowerCase().includes(busquedaLower)
        );
      });
    }

    // Filtro de rango de fechas
    if (rangoFechas) {
      const partes = rangoFechas.split(' - ').map(f => f.trim());
      if (partes.length === 2 && partes[0] && partes[1]) {
        const parsearFecha = (fechaStr) => {
          if (fechaStr.includes('/')) {
            const [dia, mes, anio] = fechaStr.split('/');
            return new Date(anio, mes - 1, dia);
          }
          return new Date(fechaStr);
        };

        const inicio = parsearFecha(partes[0]);
        const fin = parsearFecha(partes[1]);
        fin.setHours(23, 59, 59, 999);

        if (!isNaN(inicio.getTime()) && !isNaN(fin.getTime())) {
          ventasFiltradas = ventasFiltradas.filter((venta) => {
            const fechaVenta = new Date(venta.fecha);
            return fechaVenta >= inicio && fechaVenta <= fin;
          });
        }
      }
    }

    // Filtro de método de pago
    if (metodoPagoFiltro && metodoPagoFiltro !== 'Todos') {
      ventasFiltradas = ventasFiltradas.filter((venta) => {
        return venta.metodoPago?.nombre === metodoPagoFiltro;
      });
    }

    setVentasFiltradas(ventasFiltradas);
    setPaginaActual(1);
  };

  const limpiarFiltros = () => {
    setBusqueda('');
    setRangoFechas('');
    setMetodoPagoFiltro('Todos');
  };

  const getColorMetodoPago = (metodoPago) => {
    const nombre = metodoPago?.nombre || '';
    if (nombre.includes('Efectivo')) return 'bg-green-500/20 text-green-300';
    if (nombre.includes('Crédito')) return 'bg-blue-500/20 text-blue-300';
    if (nombre.includes('Débito')) return 'bg-blue-500/20 text-blue-300';
    if (nombre.includes('Transferencia')) return 'bg-purple-500/20 text-purple-300';
    return 'bg-gray-500/20 text-gray-300';
  };

  const formatearFechaHora = (fechaString) => {
    if (!fechaString) return '';
    const fecha = new Date(fechaString);
    return fecha.toLocaleString('es-ES', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
  };

  const handleDelete = async (id) => {
    if (window.confirm('¿Está seguro de eliminar esta venta?')) {
      try {
        await ventasService.delete(id);
        await loadData();
      } catch (err) {
        console.error('Error al eliminar venta:', err);
        alert('Error al eliminar la venta');
      }
    }
  };

  // Paginación
  const indiceInicio = (paginaActual - 1) * itemsPorPagina;
  const indiceFin = indiceInicio + itemsPorPagina;
  const ventasPaginadas = ventasFiltradas.slice(indiceInicio, indiceFin);
  const totalPaginas = Math.ceil(ventasFiltradas.length / itemsPorPagina);

  const cambiarPagina = (nuevaPagina) => {
    if (nuevaPagina >= 1 && nuevaPagina <= totalPaginas) {
      setPaginaActual(nuevaPagina);
    }
  };

  const generarNumerosPagina = () => {
    const numeros = [];
    const maxVisible = 5;
    let inicio = Math.max(1, paginaActual - Math.floor(maxVisible / 2));
    let fin = Math.min(totalPaginas, inicio + maxVisible - 1);

    if (fin - inicio < maxVisible - 1) {
      inicio = Math.max(1, fin - maxVisible + 1);
    }

    for (let i = inicio; i <= fin; i++) {
      numeros.push(i);
    }

    return numeros;
  };

  if (loading) {
    return (
      <DashboardLayout>
        <div className="flex items-center justify-center h-64">
          <Spinner />
        </div>
      </DashboardLayout>
    );
  }

  return (
    <DashboardLayout>
      <div className="w-full space-y-4">
        {/* Header */}
        <div className="flex flex-wrap justify-between items-center gap-4">
          <h2 className="text-white text-3xl font-bold tracking-tight">Gestión de Ventas</h2>
          <Button onClick={() => alert('Funcionalidad de Nueva Venta próximamente')}>
            <span className="material-symbols-outlined inline mr-2">add</span>
            Nueva Venta
          </Button>
        </div>

        {/* Error Message */}
        {error && (
          <div className="p-3 bg-red-500/20 border border-red-500/50 rounded-lg text-red-200">
            {error}
          </div>
        )}

        {/* Métricas Cards - Opcional */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-3">
          <StatCard
            title="Ventas Totales (Hoy)"
            value={`$${metricas.ventasHoy.toFixed(2)}`}
          />
          <StatCard
            title="Ventas Totales (Mes)"
            value={`$${metricas.ventasMes.toFixed(2)}`}
          />
          <StatCard
            title="Venta Promedio"
            value={`$${metricas.ventaPromedio.toFixed(2)}`}
          />
          <StatCard
            title="Total de Transacciones"
            value={metricas.totalTransacciones.toString()}
          />
        </div>

        {/* Filtros y Búsqueda */}
        <div className="bg-[#182535] rounded-lg border border-white/10 p-4">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-3 mb-3">
            <Input
              type="text"
              placeholder="Buscar por ID, cliente..."
              value={busqueda}
              onChange={(e) => setBusqueda(e.target.value)}
              icon={
                <span className="material-symbols-outlined text-lg">search</span>
              }
            />
            <div className="relative">
              <Input
                type="text"
                placeholder="Seleccionar rango (DD/MM/YYYY - DD/MM/YYYY)"
                value={rangoFechas}
                onChange={(e) => setRangoFechas(e.target.value)}
                icon={
                  <span className="material-symbols-outlined text-lg">calendar_today</span>
                }
              />
            </div>
            <div className="relative">
              <select
                className="w-full pl-12 pr-4 py-3.5 bg-slate-800/60 border border-slate-600/50 rounded-lg text-slate-200 text-[15px] transition-all duration-300 outline-none focus:border-primary focus:bg-slate-800/80 focus:shadow-[0_0_0_3px_rgba(81,160,251,0.1)] appearance-none cursor-pointer"
                value={metodoPagoFiltro}
                onChange={(e) => setMetodoPagoFiltro(e.target.value)}
              >
                <option value="Todos">Todos</option>
                {metodosPago.map((metodo) => (
                  <option key={metodo.id} value={metodo.nombre}>
                    {metodo.nombre}
                  </option>
                ))}
              </select>
              <span className="absolute left-4 top-1/2 -translate-y-1/2 text-slate-500 pointer-events-none">
                <span className="material-symbols-outlined text-lg">arrow_drop_down</span>
              </span>
            </div>
          </div>
          <div className="flex gap-3">
            <Button variant="secondary" onClick={limpiarFiltros}>
              Limpiar Filtros
            </Button>
            <Button onClick={aplicarFiltros}>
              Aplicar Filtros
            </Button>
          </div>
        </div>

        {/* Tabla de Ventas */}
        <div className="bg-[#1e2a3a] rounded-lg border border-white/10 overflow-hidden">
          <div className="overflow-x-auto">
            <table className="w-full">
              <thead className="bg-[#2d4a5c]">
                <tr>
                  <th className="px-4 py-2.5 text-left text-xs font-medium text-white/70 uppercase tracking-wider">
                    ID VENTA
                  </th>
                  <th className="px-4 py-2.5 text-left text-xs font-medium text-white/70 uppercase tracking-wider">
                    FECHA Y HORA
                  </th>
                  <th className="px-4 py-2.5 text-left text-xs font-medium text-white/70 uppercase tracking-wider">
                    CLIENTE
                  </th>
                  <th className="px-4 py-2.5 text-left text-xs font-medium text-white/70 uppercase tracking-wider">
                    MÉTODO DE PAGO
                  </th>
                  <th className="px-4 py-2.5 text-left text-xs font-medium text-white/70 uppercase tracking-wider">
                    TOTAL
                  </th>
                  <th className="px-4 py-2.5 text-left text-xs font-medium text-white/70 uppercase tracking-wider">
                    REQUIERE FÓRMULA
                  </th>
                  <th className="px-4 py-2.5 text-right text-xs font-medium text-white/70 uppercase tracking-wider">
                    ACC
                  </th>
                </tr>
              </thead>
              <tbody className="divide-y divide-white/10">
                {ventasPaginadas.length === 0 ? (
                  <tr>
                    <td colSpan="7" className="px-4 py-6 text-center text-white/60">
                      No se encontraron ventas
                    </td>
                  </tr>
                ) : (
                  ventasPaginadas.map((venta) => (
                    <tr key={venta.id} className="hover:bg-white/5 transition-colors">
                      <td className="px-4 py-3 whitespace-nowrap text-sm text-white/90">
                        #VENTA-{String(venta.id).padStart(3, '0')}
                      </td>
                      <td className="px-4 py-3 whitespace-nowrap text-sm text-white/90">
                        {formatearFechaHora(venta.fecha)}
                      </td>
                      <td className="px-4 py-3 whitespace-nowrap text-sm text-white/90">
                        {venta.cliente?.nombre || 'Venta de Mostrador'}
                      </td>
                      <td className="px-4 py-3 whitespace-nowrap">
                        <span
                          className={`inline-flex px-2.5 py-0.5 text-xs font-semibold rounded-full ${getColorMetodoPago(
                            venta.metodoPago
                          )}`}
                        >
                          {venta.metodoPago?.nombre || 'N/A'}
                        </span>
                      </td>
                      <td className="px-4 py-3 whitespace-nowrap text-sm text-white/90 font-semibold">
                        ${parseFloat(venta.totalGeneral || 0).toFixed(2)}
                      </td>
                      <td className="px-4 py-3 whitespace-nowrap">
                        <span
                          className={`inline-flex px-2.5 py-0.5 text-xs font-semibold rounded-full ${
                            venta.requiereFormula
                              ? 'bg-red-500/20 text-red-300'
                              : 'bg-gray-500/20 text-gray-300'
                          }`}
                        >
                          {venta.requiereFormula ? 'Sí' : 'No'}
                        </span>
                      </td>
                      <td className="px-4 py-3 whitespace-nowrap text-right text-sm font-medium">
                        <button
                          onClick={() => alert(`Ver venta #${venta.id} - Funcionalidad próximamente`)}
                          className="text-blue-400 hover:text-blue-300 mr-4 transition-colors"
                          title="Ver"
                        >
                          <span className="material-symbols-outlined">visibility</span>
                        </button>
                        <button
                          onClick={() => alert(`Editar venta #${venta.id} - Funcionalidad próximamente`)}
                          className="text-blue-400 hover:text-blue-300 mr-4 transition-colors"
                          title="Editar"
                        >
                          <span className="material-symbols-outlined">edit</span>
                        </button>
                        <button
                          onClick={() => handleDelete(venta.id)}
                          className="text-red-400 hover:text-red-300 transition-colors"
                          title="Eliminar"
                        >
                          <span className="material-symbols-outlined">delete</span>
                        </button>
                      </td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>
        </div>

        {/* Paginación */}
        {ventasFiltradas.length > 0 && (
          <div className="flex flex-col sm:flex-row justify-between items-center gap-3">
            <div className="text-white/60 text-sm">
              Mostrando {indiceInicio + 1} a {Math.min(indiceFin, ventasFiltradas.length)} de{' '}
              {ventasFiltradas.length} resultados
            </div>
            <div className="flex items-center gap-2">
              <button
                onClick={() => cambiarPagina(paginaActual - 1)}
                disabled={paginaActual === 1}
                className="px-3 py-2 rounded-lg bg-[#182535] border border-white/10 text-white/90 hover:bg-white/10 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
              >
                <span className="material-symbols-outlined text-lg">chevron_left</span>
              </button>
              {generarNumerosPagina().map((numero) => (
                <button
                  key={numero}
                  onClick={() => cambiarPagina(numero)}
                  className={`px-4 py-2 rounded-lg font-semibold transition-colors ${
                    numero === paginaActual
                      ? 'bg-primary text-white'
                      : 'bg-[#182535] border border-white/10 text-white/90 hover:bg-white/10'
                  }`}
                >
                  {numero}
                </button>
              ))}
              {totalPaginas > generarNumerosPagina()[generarNumerosPagina().length - 1] && (
                <>
                  <span className="text-white/60 px-2">...</span>
                  <button
                    onClick={() => cambiarPagina(totalPaginas)}
                    className="px-4 py-2 rounded-lg bg-[#182535] border border-white/10 text-white/90 hover:bg-white/10 transition-colors"
                  >
                    {totalPaginas}
                  </button>
                </>
              )}
              <button
                onClick={() => cambiarPagina(paginaActual + 1)}
                disabled={paginaActual === totalPaginas}
                className="px-3 py-2 rounded-lg bg-[#182535] border border-white/10 text-white/90 hover:bg-white/10 disabled:opacity-50 disabled:cursor-not-allowed transition-colors"
              >
                <span className="material-symbols-outlined text-lg">chevron_right</span>
              </button>
            </div>
          </div>
        )}
      </div>
    </DashboardLayout>
  );
};

export default VentasPage;

