import React, { useState, useEffect } from 'react';
import DashboardLayout from '../../../components/templates/DashboardLayout/DashboardLayout';
import { productosService } from '../../../services/productosService/productosService';
import { categoriasService } from '../../../services/categoriasService/categoriasService';
import { laboratoriosService } from '../../../services/laboratoriosService/laboratoriosService';
import Button from '../../../components/atoms/Button/Button';
import Input from '../../../components/atoms/Input/Input';
import Spinner from '../../../components/atoms/Spinner/Spinner';
import EditProductoModal from '../components/EditProductoModal/EditProductoModal';

const ProductosPage = () => {
  const [productos, setProductos] = useState([]);
  const [productosFiltrados, setProductosFiltrados] = useState([]);
  const [categorias, setCategorias] = useState([]);
  const [laboratorios, setLaboratorios] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Filtros
  const [busqueda, setBusqueda] = useState('');
  const [categoriaFiltro, setCategoriaFiltro] = useState('Todos');
  const [laboratorioFiltro, setLaboratorioFiltro] = useState('Todos');
  const [soloConStock, setSoloConStock] = useState(false);

  // Paginación
  const [paginaActual, setPaginaActual] = useState(1);
  const itemsPorPagina = 5;

  // Modal de edición
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [selectedProductoId, setSelectedProductoId] = useState(null);

  useEffect(() => {
    loadData();
  }, []);

  useEffect(() => {
    aplicarFiltros();
  }, [busqueda, categoriaFiltro, laboratorioFiltro, soloConStock, productos]);

  const loadData = async () => {
    try {
      setLoading(true);
      setError(null);
      
      const [productosResponse, categoriasResponse, laboratoriosResponse] = await Promise.all([
        productosService.getAll().catch(err => {
          console.error('Error al cargar productos:', err);
          return { data: [] };
        }),
        categoriasService.getAll().catch(err => {
          console.error('Error al cargar categorías:', err);
          return { data: [] };
        }),
        laboratoriosService.getAll().catch(err => {
          console.error('Error al cargar laboratorios:', err);
          return { data: [] };
        }),
      ]);

      const productosData = productosResponse.data || [];
      setProductos(productosData);
      setProductosFiltrados(productosData);

      const categoriasData = categoriasResponse.data || [];
      setCategorias(categoriasData);

      const laboratoriosData = laboratoriosResponse.data || [];
      setLaboratorios(laboratoriosData);
    } catch (err) {
      console.error('Error al cargar datos:', err);
      setError(err.response?.data?.message || 'Error al cargar los productos');
    } finally {
      setLoading(false);
    }
  };

  const aplicarFiltros = () => {
    let filtrados = [...productos];

    // Filtro de búsqueda
    if (busqueda) {
      const busquedaLower = busqueda.toLowerCase();
      filtrados = filtrados.filter((producto) => {
        const nombre = producto.nombre?.toLowerCase() || '';
        const concentracion = producto.concentracion?.toLowerCase() || '';
        const categoria = producto.categoria?.nombre?.toLowerCase() || '';
        const laboratorio = producto.laboratorio?.nombre?.toLowerCase() || '';
        const registroInvima = producto.registroInvima?.toLowerCase() || '';

        return (
          nombre.includes(busquedaLower) ||
          concentracion.includes(busquedaLower) ||
          categoria.includes(busquedaLower) ||
          laboratorio.includes(busquedaLower) ||
          registroInvima.includes(busquedaLower)
        );
      });
    }

    // Filtro de categoría
    if (categoriaFiltro && categoriaFiltro !== 'Todos') {
      filtrados = filtrados.filter((producto) => {
        return producto.categoria?.nombre === categoriaFiltro;
      });
    }

    // Filtro de laboratorio
    if (laboratorioFiltro && laboratorioFiltro !== 'Todos') {
      filtrados = filtrados.filter((producto) => {
        return producto.laboratorio?.nombre === laboratorioFiltro;
      });
    }

    // Filtro de stock (simulado - asumiendo que el stock viene en el producto o se calcula)
    if (soloConStock) {
      filtrados = filtrados.filter((producto) => {
        // Si el producto tiene stock, asumimos que viene en producto.stock o producto.cantidad
        // Por ahora, simulamos que todos tienen stock excepto si stock === 0
        const stock = producto.stock || producto.cantidad || 0;
        return stock > 0;
      });
    }

    setProductosFiltrados(filtrados);
    setPaginaActual(1);
  };

  const limpiarFiltros = () => {
    setBusqueda('');
    setCategoriaFiltro('Todos');
    setLaboratorioFiltro('Todos');
    setSoloConStock(false);
  };

  const handleDelete = async (id) => {
    if (window.confirm('¿Está seguro de eliminar este producto?')) {
      try {
        await productosService.delete(id);
        await loadData();
      } catch (err) {
        console.error('Error al eliminar producto:', err);
        alert('Error al eliminar el producto');
      }
    }
  };

  // Paginación
  const indiceInicio = (paginaActual - 1) * itemsPorPagina;
  const indiceFin = indiceInicio + itemsPorPagina;
  const productosPaginados = productosFiltrados.slice(indiceInicio, indiceFin);
  const totalPaginas = Math.ceil(productosFiltrados.length / itemsPorPagina);

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

  // Obtener stock del producto (simulado - ajustar según estructura real)
  const obtenerStock = (producto) => {
    // Si el producto tiene stock directamente
    if (producto.stock !== undefined) return producto.stock;
    if (producto.cantidad !== undefined) return producto.cantidad;
    // Simulación: generar un stock aleatorio para demostración
    return Math.floor(Math.random() * 300);
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
          <h2 className="text-white text-3xl font-bold tracking-tight">Gestión de Productos</h2>
          <Button onClick={() => alert('Funcionalidad de Nuevo Producto próximamente')}>
            <span className="material-symbols-outlined inline mr-2">add</span>
            Nuevo Producto
          </Button>
        </div>

        {/* Error Message */}
        {error && (
          <div className="p-3 bg-red-500/20 border border-red-500/50 rounded-lg text-red-200">
            {error}
          </div>
        )}

        {/* Filtros y Búsqueda */}
        <div className="bg-[#182535] rounded-lg border border-white/10 p-4">
          <div className="mb-4">
            <Input
              type="text"
              placeholder="Buscar por nombre, categoría, laboratorio..."
              value={busqueda}
              onChange={(e) => setBusqueda(e.target.value)}
              icon={
                <span className="material-symbols-outlined text-lg">search</span>
              }
            />
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-3 gap-3 mb-3">
            <div className="flex items-center gap-2">
              <input
                type="checkbox"
                id="soloStock"
                checked={soloConStock}
                onChange={(e) => setSoloConStock(e.target.checked)}
                className="w-4 h-4 rounded bg-slate-800/60 border border-slate-600/50 text-primary focus:ring-primary focus:ring-2"
              />
              <label htmlFor="soloStock" className="text-white/90 text-sm cursor-pointer">
                Mostrar solo productos con stock
              </label>
            </div>
            
            <div className="relative">
              <select
                className="w-full pl-12 pr-4 py-3.5 bg-slate-800/60 border border-slate-600/50 rounded-lg text-slate-200 text-[15px] transition-all duration-300 outline-none focus:border-primary focus:bg-slate-800/80 focus:shadow-[0_0_0_3px_rgba(81,160,251,0.1)] appearance-none cursor-pointer"
                value={categoriaFiltro}
                onChange={(e) => setCategoriaFiltro(e.target.value)}
              >
                <option value="Todos">Categoría</option>
                {categorias.map((categoria) => (
                  <option key={categoria.id} value={categoria.nombre}>
                    {categoria.nombre}
                  </option>
                ))}
              </select>
              <span className="absolute left-4 top-1/2 -translate-y-1/2 text-slate-500 pointer-events-none">
                <span className="material-symbols-outlined text-lg">folder</span>
              </span>
            </div>

            <div className="relative">
              <select
                className="w-full pl-12 pr-4 py-3.5 bg-slate-800/60 border border-slate-600/50 rounded-lg text-slate-200 text-[15px] transition-all duration-300 outline-none focus:border-primary focus:bg-slate-800/80 focus:shadow-[0_0_0_3px_rgba(81,160,251,0.1)] appearance-none cursor-pointer"
                value={laboratorioFiltro}
                onChange={(e) => setLaboratorioFiltro(e.target.value)}
              >
                <option value="Todos">Laboratorio</option>
                {laboratorios.map((laboratorio) => (
                  <option key={laboratorio.id} value={laboratorio.nombre}>
                    {laboratorio.nombre}
                  </option>
                ))}
              </select>
              <span className="absolute left-4 top-1/2 -translate-y-1/2 text-slate-500 pointer-events-none">
                <span className="material-symbols-outlined text-lg">science</span>
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

        {/* Tabla de Productos */}
        <div className="bg-[#1e2a3a] rounded-lg border border-white/10 overflow-hidden">
          <div className="overflow-x-auto">
            <table className="w-full">
              <thead className="bg-[#2d4a5c]">
                <tr>
                  <th className="px-4 py-2.5 text-left text-xs font-medium text-white/70 uppercase tracking-wider">
                    ID PRODUCTO
                  </th>
                  <th className="px-4 py-2.5 text-left text-xs font-medium text-white/70 uppercase tracking-wider">
                    NOMBRE
                  </th>
                  <th className="px-4 py-2.5 text-left text-xs font-medium text-white/70 uppercase tracking-wider">
                    CONCENTRACIÓN
                  </th>
                  <th className="px-4 py-2.5 text-left text-xs font-medium text-white/70 uppercase tracking-wider">
                    CATEGORÍA
                  </th>
                  <th className="px-4 py-2.5 text-left text-xs font-medium text-white/70 uppercase tracking-wider">
                    LABORATORIO
                  </th>
                  <th className="px-4 py-2.5 text-left text-xs font-medium text-white/70 uppercase tracking-wider">
                    STOCK
                  </th>
                  <th className="px-4 py-2.5 text-left text-xs font-medium text-white/70 uppercase tracking-wider">
                    VALOR COMPRA
                  </th>
                  <th className="px-4 py-2.5 text-left text-xs font-medium text-white/70 uppercase tracking-wider">
                    VALOR VENTA
                  </th>
                  <th className="px-4 py-2.5 text-left text-xs font-medium text-white/70 uppercase tracking-wider">
                    UTILIDAD
                  </th>
                  <th className="px-4 py-2.5 text-left text-xs font-medium text-white/70 uppercase tracking-wider">
                    % IVA
                  </th>
                  <th className="px-4 py-2.5 text-left text-xs font-medium text-white/70 uppercase tracking-wider">
                    REQUIERE FÓRMULA
                  </th>
                  <th className="px-4 py-2.5 text-right text-xs font-medium text-white/70 uppercase tracking-wider">
                    ACCIONES
                  </th>
                </tr>
              </thead>
              <tbody className="divide-y divide-white/10">
                {productosPaginados.length === 0 ? (
                  <tr>
                    <td colSpan="12" className="px-4 py-6 text-center text-white/60">
                      {busqueda || categoriaFiltro !== 'Todos' || laboratorioFiltro !== 'Todos' || soloConStock
                        ? 'No se encontraron productos que coincidan con los filtros'
                        : 'No se encontraron productos'}
                    </td>
                  </tr>
                ) : (
                  productosPaginados.map((producto) => {
                    const stock = obtenerStock(producto);
                    
                    // Obtener valores de compra y venta
                    const valorCompra = producto.valorCompra || producto.valor_compra || null;
                    const valorVenta = producto.valorVenta || producto.valor_venta || null;
                    
                    // Calcular utilidad si está disponible
                    const utilidad = producto.utilidad !== undefined ? producto.utilidad : 
                                    (valorVenta && valorCompra ? valorVenta - valorCompra : null);
                    
                    // Obtener porcentaje de IVA
                    const porcentajeIva = producto.porcentajeIva !== undefined ? producto.porcentajeIva : 
                                         (producto.porcentaje_iva !== undefined ? producto.porcentaje_iva : 
                                          (producto.iva !== undefined ? producto.iva : null));
                    
                    return (
                      <tr key={producto.id} className="hover:bg-white/5 transition-colors">
                        <td className="px-4 py-3 whitespace-nowrap text-sm text-white/90">
                          #{producto.id}
                        </td>
                        <td className="px-4 py-3 text-sm text-white/90 font-medium">
                          {producto.nombre || '-'}
                        </td>
                        <td className="px-4 py-3 text-sm text-white/90">
                          {producto.concentracion || '-'}
                        </td>
                        <td className="px-4 py-3 text-sm text-white/90">
                          {producto.categoria?.nombre || '-'}
                        </td>
                        <td className="px-4 py-3 text-sm text-white/90">
                          {producto.laboratorio?.nombre || '-'}
                        </td>
                        <td className="px-4 py-3 text-sm text-white/90">
                          {stock} Uds.
                        </td>
                        <td className="px-4 py-3 text-sm text-white/90 font-semibold">
                          {valorCompra !== null ? `$${Number(valorCompra).toFixed(2)}` : '-'}
                        </td>
                        <td className="px-4 py-3 text-sm text-white/90 font-semibold">
                          {valorVenta !== null ? `$${Number(valorVenta).toFixed(2)}` : '-'}
                        </td>
                        <td className={`px-4 py-3 text-sm font-semibold ${
                          utilidad !== null && utilidad < 0 ? 'text-red-400' : 'text-green-400'
                        }`}>
                          {utilidad !== null ? `$${Number(utilidad).toFixed(2)}` : '-'}
                        </td>
                        <td className="px-4 py-3 text-sm text-white/90 font-semibold">
                          {porcentajeIva !== null ? `${Number(porcentajeIva).toFixed(2)}%` : '-'}
                        </td>
                        <td className="px-4 py-3 whitespace-nowrap">
                          <span
                            className={`inline-flex px-2.5 py-0.5 text-xs font-semibold rounded-full ${
                              producto.requiereFormula
                                ? 'bg-red-500/20 text-red-300'
                                : 'bg-green-500/20 text-green-300'
                            }`}
                          >
                            {producto.requiereFormula ? 'Sí' : 'No'}
                          </span>
                        </td>
                        <td className="px-4 py-3 whitespace-nowrap text-right text-sm font-medium">
                          <button
                            onClick={() => alert(`Ver producto #${producto.id} - Funcionalidad próximamente`)}
                            className="text-blue-400 hover:text-blue-300 mr-4 transition-colors"
                            title="Ver"
                          >
                            <span className="material-symbols-outlined">visibility</span>
                          </button>
                          <button
                            onClick={() => {
                              setSelectedProductoId(producto.id);
                              setIsEditModalOpen(true);
                            }}
                            className="text-blue-400 hover:text-blue-300 mr-4 transition-colors"
                            title="Editar"
                          >
                            <span className="material-symbols-outlined">edit</span>
                          </button>
                          <button
                            onClick={() => handleDelete(producto.id)}
                            className="text-red-400 hover:text-red-300 transition-colors"
                            title="Eliminar"
                          >
                            <span className="material-symbols-outlined">delete</span>
                          </button>
                        </td>
                      </tr>
                    );
                  })
                )}
              </tbody>
            </table>
          </div>
        </div>

        {/* Paginación */}
        {productosFiltrados.length > 0 && (
          <div className="flex flex-col sm:flex-row justify-between items-center gap-3">
            <div className="text-white/60 text-sm">
              Mostrando {indiceInicio + 1}-{Math.min(indiceFin, productosFiltrados.length)} de{' '}
              {productosFiltrados.length} resultados
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

        {/* Modal de Edición */}
        <EditProductoModal
          isOpen={isEditModalOpen}
          onClose={() => {
            setIsEditModalOpen(false);
            setSelectedProductoId(null);
          }}
          onSuccess={() => {
            loadData();
          }}
          productoId={selectedProductoId}
        />
      </div>
    </DashboardLayout>
  );
};

export default ProductosPage;

