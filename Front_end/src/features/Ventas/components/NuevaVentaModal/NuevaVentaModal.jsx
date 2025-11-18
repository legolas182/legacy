import React, { useState, useEffect } from 'react';
import { contactosService } from '../../../../services/contactosService/contactosService';
import { productosService } from '../../../../services/productosService/productosService';
import { metodosPagoService } from '../../../../services/metodosPagoService/metodosPagoService';
import { ventasService } from '../../../../services/ventasService/ventasService';
import { ventasDetalleService } from '../../../../services/ventasDetalleService/ventasDetalleService';
import { rolesService } from '../../../../services/rolesService/rolesService';
import Button from '../../../../components/atoms/Button/Button';
import Input from '../../../../components/atoms/Input/Input';
import Spinner from '../../../../components/atoms/Spinner/Spinner';

const NuevaVentaModal = ({ isOpen, onClose, onSuccess }) => {
  const [clientes, setClientes] = useState([]);
  const [productos, setProductos] = useState([]);
  const [metodosPago, setMetodosPago] = useState([]);
  const [loading, setLoading] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  const [error, setError] = useState(null);

  // Formulario
  const [nombreCliente, setNombreCliente] = useState('');
  const [clienteId, setClienteId] = useState(null);
  const [clientesFiltrados, setClientesFiltrados] = useState([]);
  const [mostrarSugerenciasCliente, setMostrarSugerenciasCliente] = useState(false);
  const [metodoPagoId, setMetodoPagoId] = useState('');
  const [requiereFormula, setRequiereFormula] = useState(false);
  const [numeroFormula, setNumeroFormula] = useState('');
  
  // Productos de la venta
  const [productosVenta, setProductosVenta] = useState([]);
  const [busquedaProducto, setBusquedaProducto] = useState('');
  const [productosFiltrados, setProductosFiltrados] = useState([]);

  useEffect(() => {
    if (isOpen) {
      loadData();
      resetForm();
    }
  }, [isOpen]);

  useEffect(() => {
    if (busquedaProducto) {
      const busquedaLower = busquedaProducto.toLowerCase().trim();
      const filtrados = productos.filter(p => {
        const nombre = p.nombre?.toLowerCase() || '';
        const codigoBarras = p.codigoBarras?.toLowerCase() || '';
        const codigo = p.codigo?.toLowerCase() || '';
        const id = p.id?.toString() || '';
        const concentracion = p.concentracion?.toLowerCase() || '';
        
        return (
          nombre.includes(busquedaLower) ||
          codigoBarras.includes(busquedaLower) ||
          codigo.includes(busquedaLower) ||
          id === busquedaLower ||
          concentracion.includes(busquedaLower)
        );
      });
      setProductosFiltrados(filtrados.slice(0, 15));
    } else {
      setProductosFiltrados([]);
    }
  }, [busquedaProducto, productos]);

  useEffect(() => {
    if (nombreCliente) {
      const filtrados = clientes.filter(c => 
        c.nombre?.toLowerCase().includes(nombreCliente.toLowerCase())
      );
      setClientesFiltrados(filtrados.slice(0, 10));
      setMostrarSugerenciasCliente(true);
    } else {
      setClientesFiltrados([]);
      setMostrarSugerenciasCliente(false);
    }
  }, [nombreCliente, clientes]);

  const loadData = async () => {
    try {
      setLoading(true);
      const [clientesRes, productosRes, metodosRes] = await Promise.all([
        contactosService.getAll(),
        productosService.getAll(),
        metodosPagoService.getAll(),
      ]);

      // Filtrar solo clientes (contactos con rol CLIENTE)
      const clientesData = (clientesRes.data || []).filter(c => 
        c.rol?.nombre?.toUpperCase() === 'CLIENTE' || !c.rol
      );
      setClientes(clientesData);
      setProductos(productosRes.data || []);
      setMetodosPago(metodosRes.data || []);
    } catch (err) {
      console.error('Error al cargar datos:', err);
      setError('Error al cargar los datos necesarios');
    } finally {
      setLoading(false);
    }
  };

  const resetForm = () => {
    setNombreCliente('');
    setClienteId(null);
    setClientesFiltrados([]);
    setMostrarSugerenciasCliente(false);
    setMetodoPagoId('');
    setRequiereFormula(false);
    setNumeroFormula('');
    setProductosVenta([]);
    setBusquedaProducto('');
    setError(null);
  };

  const seleccionarCliente = (cliente) => {
    setNombreCliente(cliente.nombre);
    setClienteId(cliente.id);
    setMostrarSugerenciasCliente(false);
  };


  const agregarProducto = (producto) => {
    // Verificar si el producto ya está en la lista
    const existe = productosVenta.find(p => p.producto.id === producto.id);
    // Usar el precio de venta (valorVenta) del producto
    const precioVenta = parseFloat(producto.valorVenta || producto.valor_venta || producto.precioVenta || producto.precio || 0);
    const porcentajeIva = parseFloat(producto.porcentajeIva || producto.porcentaje_iva || 19.00);
    
    if (existe) {
      // Si existe, aumentar la cantidad
      const nuevaCantidad = existe.cantidad + 1;
      const nuevoSubtotal = nuevaCantidad * precioVenta;
      setProductosVenta(productosVenta.map(p => 
        p.producto.id === producto.id 
          ? { ...p, cantidad: nuevaCantidad, subtotal: nuevoSubtotal }
          : p
      ));
    } else {
      // Si no existe, agregarlo
      const cantidad = 1;
      const subtotal = cantidad * precioVenta;
      const nuevoProducto = {
        producto: producto,
        cantidad: cantidad,
        precioUnitario: precioVenta, // Precio de venta del producto
        subtotal: subtotal,
        porcentajeIva: porcentajeIva,
      };
      setProductosVenta([...productosVenta, nuevoProducto]);
    }
    setBusquedaProducto('');
    setProductosFiltrados([]);
  };

  const eliminarProducto = (index) => {
    setProductosVenta(productosVenta.filter((_, i) => i !== index));
  };

  const actualizarCantidad = (index, nuevaCantidad) => {
    if (nuevaCantidad < 1) return;
    setProductosVenta(productosVenta.map((p, i) => {
      if (i === index) {
        const nuevoSubtotal = nuevaCantidad * p.precioUnitario;
        return { ...p, cantidad: nuevaCantidad, subtotal: nuevoSubtotal };
      }
      return p;
    }));
  };

  const actualizarPrecio = (index, nuevoPrecio) => {
    if (nuevoPrecio < 0) return;
    setProductosVenta(productosVenta.map((p, i) => {
      if (i === index) {
        const nuevoSubtotal = p.cantidad * nuevoPrecio;
        return { ...p, precioUnitario: nuevoPrecio, subtotal: nuevoSubtotal };
      }
      return p;
    }));
  };

  const calcularSubtotal = () => {
    return productosVenta.reduce((sum, p) => sum + p.subtotal, 0);
  };

  const calcularIvaTotal = () => {
    // Calcular el IVA sobre el total de la venta (subtotal total)
    const subtotalTotal = calcularSubtotal();
    
    // Calcular el porcentaje promedio de IVA basado en los productos
    // Ponderado por el subtotal de cada producto
    if (productosVenta.length === 0 || subtotalTotal === 0) {
      return 0;
    }
    
    let ivaPonderado = 0;
    productosVenta.forEach(p => {
      const porcentaje = p.porcentajeIva || 19.00;
      const peso = p.subtotal / subtotalTotal;
      ivaPonderado += porcentaje * peso;
    });
    
    // Calcular IVA sobre el total de la venta
    return (subtotalTotal * ivaPonderado) / 100;
  };

  const calcularTotal = () => {
    return calcularSubtotal() + calcularIvaTotal();
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);

    // Validaciones
    if (!nombreCliente || !nombreCliente.trim()) {
      setError('Debe ingresar el nombre del cliente');
      return;
    }

    if (productosVenta.length === 0) {
      setError('Debe agregar al menos un producto');
      return;
    }

    if (requiereFormula && !numeroFormula.trim()) {
      setError('Debe ingresar el número de fórmula');
      return;
    }

    try {
      setSubmitting(true);

      // Buscar o usar cliente
      let clienteFinal = null;
      if (clienteId) {
        // Si se seleccionó un cliente existente
        clienteFinal = { id: clienteId };
      } else {
        // Si es un cliente nuevo, buscar si existe con ese nombre
        const clienteExistente = clientes.find(c => 
          c.nombre.toLowerCase() === nombreCliente.trim().toLowerCase()
        );
        if (clienteExistente) {
          clienteFinal = { id: clienteExistente.id };
        } else {
          // Si no existe, intentar crear un cliente nuevo
          try {
            // Buscar el rol CLIENTE
            const rolesRes = await rolesService.getAll();
            const rolCliente = rolesRes.data?.find(r => r.nombre?.toUpperCase() === 'CLIENTE');
            
            if (!rolCliente) {
              throw new Error('No se encontró el rol CLIENTE');
            }
            
            const nuevoCliente = {
              nombre: nombreCliente.trim(),
              rol: { id: rolCliente.id },
              activo: true,
            };
            const clienteResponse = await contactosService.create(nuevoCliente);
            clienteFinal = { id: clienteResponse.data.id };
          } catch (err) {
            console.error('Error al crear cliente:', err);
            // Si falla la creación, usar el primer cliente disponible como fallback
            if (clientes.length > 0) {
              clienteFinal = { id: clientes[0].id };
            } else {
              setError('No se pudo crear el cliente. Por favor, seleccione un cliente de la lista.');
              return;
            }
          }
        }
      }

      // Crear la venta
      const ventaData = {
        cliente: clienteFinal,
        fecha: new Date().toISOString(),
        metodoPago: metodoPagoId ? { id: parseInt(metodoPagoId) } : null,
        totalGeneral: calcularTotal(),
        requiereFormula: requiereFormula,
        numeroFormula: requiereFormula ? numeroFormula.trim() : null,
      };

      const ventaResponse = await ventasService.create(ventaData);
      const ventaId = ventaResponse.data.id;

      // Crear los detalles de la venta
      const detallesPromises = productosVenta.map(producto => {
        const detalleData = {
          venta: { id: ventaId },
          producto: { id: producto.producto.id },
          cantidad: producto.cantidad,
          precioUnitario: producto.precioUnitario,
        };
        return ventasDetalleService.create(detalleData);
      });

      await Promise.all(detallesPromises);

      resetForm();
      onSuccess?.();
      onClose();
    } catch (err) {
      console.error('Error al crear venta:', err);
      let errorMessage = 'Error al crear la venta';
      
      if (err.response?.data) {
        if (typeof err.response.data === 'string') {
          errorMessage = err.response.data;
        } else if (err.response.data.message) {
          errorMessage = err.response.data.message;
        } else if (err.response.data.error) {
          errorMessage = err.response.data.error;
        }
      } else if (err.message) {
        errorMessage = err.message;
      }
      
      setError(errorMessage);
    } finally {
      setSubmitting(false);
    }
  };

  const handleClose = () => {
    if (!submitting) {
      resetForm();
      onClose();
    }
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/60 backdrop-blur-sm">
      <div className="bg-[#1e2a3a] rounded-lg border border-white/10 w-full max-w-7xl max-h-[95vh] overflow-hidden mx-4 shadow-2xl flex flex-col">
        {/* Header */}
        <div className="sticky top-0 bg-[#1e2a3a] border-b border-white/10 p-6 flex justify-between items-center z-10">
          <div>
            <h2 className="text-white text-2xl font-bold">Nueva Venta</h2>
            {productosVenta.length > 0 && (
              <p className="text-white/60 text-sm mt-1">
                {productosVenta.length} {productosVenta.length === 1 ? 'producto agregado' : 'productos agregados'}
              </p>
            )}
          </div>
          <button
            onClick={handleClose}
            disabled={submitting}
            className="text-slate-400 hover:text-white transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
          >
            <span className="material-symbols-outlined text-2xl">close</span>
          </button>
        </div>

        {/* Content - Layout de 2 columnas */}
        <div className="flex-1 overflow-hidden flex">
          {loading ? (
            <div className="flex items-center justify-center h-full w-full">
              <Spinner />
            </div>
          ) : (
            <form onSubmit={handleSubmit} className="flex-1 flex flex-col">
              {/* Error Message */}
              {error && (
                <div className="mx-6 mt-6 p-4 bg-red-500/20 border border-red-500/50 rounded-lg text-red-200">
                  {error}
                </div>
              )}

              <div className="flex-1 flex overflow-hidden">
                {/* Columna izquierda - Información de la venta y búsqueda */}
                <div className="w-1/3 border-r border-white/10 p-6 overflow-y-auto flex flex-col gap-6">
                  {/* Información básica */}
                  <div className="space-y-4">
                    <h3 className="text-white text-lg font-semibold">Información de la Venta</h3>
                    
                    {/* Cliente */}
                    <div className="flex flex-col gap-2 relative">
                      <label className="text-sm font-medium text-slate-300">
                        Cliente *
                      </label>
                      <div className="relative">
                        <Input
                          type="text"
                          placeholder="Ingrese el nombre del cliente"
                          value={nombreCliente}
                          onChange={(e) => {
                            setNombreCliente(e.target.value);
                            setClienteId(null);
                          }}
                          onFocus={() => {
                            if (nombreCliente) {
                              setMostrarSugerenciasCliente(true);
                            }
                          }}
                          required
                        />
                        {mostrarSugerenciasCliente && clientesFiltrados.length > 0 && (
                          <div className="absolute z-20 w-full mt-1 bg-[#182535] border border-white/10 rounded-lg shadow-lg max-h-60 overflow-y-auto">
                            {clientesFiltrados.map((cliente) => (
                              <button
                                key={cliente.id}
                                type="button"
                                onClick={() => seleccionarCliente(cliente)}
                                className="w-full px-4 py-3 text-left hover:bg-white/5 transition-colors border-b border-white/5 last:border-b-0"
                              >
                                <div className="text-white font-medium">{cliente.nombre}</div>
                                {cliente.email && (
                                  <div className="text-sm text-slate-400">{cliente.email}</div>
                                )}
                              </button>
                            ))}
                          </div>
                        )}
                      </div>
                    </div>

                    {/* Método de Pago */}
                    <div className="flex flex-col gap-2">
                      <label className="text-sm font-medium text-slate-300">
                        Método de Pago
                      </label>
                      <select
                        value={metodoPagoId}
                        onChange={(e) => setMetodoPagoId(e.target.value)}
                        className="w-full pl-4 pr-4 py-3.5 bg-slate-800/60 border border-slate-600/50 rounded-lg text-slate-200 text-[15px] transition-all duration-300 outline-none focus:border-primary focus:bg-slate-800/80 focus:shadow-[0_0_0_3px_rgba(81,160,251,0.1)]"
                      >
                        <option value="">Seleccionar método de pago</option>
                        {metodosPago.map((metodo) => (
                          <option key={metodo.id} value={metodo.id}>
                            {metodo.nombre}
                          </option>
                        ))}
                      </select>
                    </div>

                    {/* Fórmula médica */}
                    <div className="space-y-2">
                      <div className="flex items-center gap-3">
                        <input
                          type="checkbox"
                          id="requiereFormula"
                          checked={requiereFormula}
                          onChange={(e) => {
                            setRequiereFormula(e.target.checked);
                            if (!e.target.checked) setNumeroFormula('');
                          }}
                          className="w-5 h-5 rounded border-slate-600 bg-slate-800 text-primary focus:ring-2 focus:ring-primary/50 cursor-pointer"
                        />
                        <label htmlFor="requiereFormula" className="text-sm font-medium text-slate-300 cursor-pointer">
                          Requiere fórmula médica
                        </label>
                      </div>
                      {requiereFormula && (
                        <Input
                          type="text"
                          placeholder="Número de fórmula"
                          value={numeroFormula}
                          onChange={(e) => setNumeroFormula(e.target.value)}
                          required={requiereFormula}
                        />
                      )}
                    </div>
                  </div>

                  {/* Búsqueda de productos */}
                  <div className="space-y-3">
                    <h3 className="text-white text-lg font-semibold">Agregar Productos</h3>
                    <div className="relative">
                      <Input
                        type="text"
                        placeholder="Buscar por nombre o código..."
                        value={busquedaProducto}
                        onChange={(e) => setBusquedaProducto(e.target.value)}
                        icon={<span className="material-symbols-outlined text-lg">search</span>}
                        autoFocus
                      />
                      {productosFiltrados.length > 0 && (
                        <div className="absolute z-20 w-full mt-1 bg-[#182535] border border-white/10 rounded-lg shadow-lg max-h-96 overflow-y-auto">
                          {productosFiltrados.map((producto) => (
                            <button
                              key={producto.id}
                              type="button"
                              onClick={() => agregarProducto(producto)}
                              className="w-full px-4 py-3 text-left hover:bg-white/5 transition-colors border-b border-white/5 last:border-b-0"
                            >
                              <div className="flex justify-between items-start">
                                <div className="flex-1">
                                  <div className="text-white font-medium">{producto.nombre}</div>
                                  {producto.concentracion && (
                                    <div className="text-sm text-slate-400">{producto.concentracion}</div>
                                  )}
                                  {(producto.codigoBarras || producto.codigo || producto.id) && (
                                    <div className="text-xs text-slate-500 mt-1">
                                      Código: {producto.codigoBarras || producto.codigo || `#${producto.id}`}
                                    </div>
                                  )}
                                </div>
                                <div className="ml-3 text-primary font-semibold text-sm">
                                  ${parseFloat(producto.valorVenta || producto.valor_venta || producto.precioVenta || producto.precio || 0).toFixed(2)}
                                </div>
                              </div>
                            </button>
                          ))}
                        </div>
                      )}
                    </div>
                    {busquedaProducto && productosFiltrados.length === 0 && (
                      <div className="text-center py-4 text-white/60 text-sm">
                        No se encontraron productos
                      </div>
                    )}
                  </div>
                </div>

                {/* Columna derecha - Lista de productos */}
                <div className="flex-1 p-6 overflow-y-auto flex flex-col">
                  {productosVenta.length === 0 ? (
                    <div className="flex-1 flex items-center justify-center">
                      <div className="text-center">
                        <span className="material-symbols-outlined text-6xl text-white/20 mb-4">shopping_cart</span>
                        <p className="text-white/60 text-lg">No hay productos agregados</p>
                        <p className="text-white/40 text-sm mt-2">Busca y agrega productos para comenzar la venta</p>
                      </div>
                    </div>
                  ) : (
                    <div className="space-y-4">
                      <h3 className="text-white text-lg font-semibold">Productos en la Venta</h3>
                      <div className="bg-[#182535] rounded-lg border border-white/10 overflow-hidden">
                        <div className="overflow-x-auto">
                          <table className="w-full">
                            <thead className="bg-[#2d4a5c]">
                              <tr>
                                <th className="px-4 py-3 text-left text-xs font-medium text-white/70 uppercase">Producto</th>
                                <th className="px-4 py-3 text-center text-xs font-medium text-white/70 uppercase">Cantidad</th>
                                <th className="px-4 py-3 text-left text-xs font-medium text-white/70 uppercase">Precio Unit.</th>
                                <th className="px-4 py-3 text-left text-xs font-medium text-white/70 uppercase">Subtotal</th>
                                <th className="px-4 py-3 text-center text-xs font-medium text-white/70 uppercase">Acción</th>
                              </tr>
                            </thead>
                            <tbody className="divide-y divide-white/10">
                              {productosVenta.map((item, index) => (
                                <tr key={index} className="hover:bg-white/5">
                                  <td className="px-4 py-3">
                                    <div className="text-sm text-white/90 font-medium">{item.producto.nombre}</div>
                                    {item.producto.concentracion && (
                                      <div className="text-xs text-slate-400 mt-1">{item.producto.concentracion}</div>
                                    )}
                                  </td>
                                  <td className="px-4 py-3">
                                    <input
                                      type="number"
                                      min="1"
                                      value={item.cantidad}
                                      onChange={(e) => actualizarCantidad(index, parseInt(e.target.value) || 1)}
                                      className="w-20 px-2 py-1.5 bg-slate-800/60 border border-slate-600/50 rounded text-slate-200 text-sm text-center focus:outline-none focus:border-primary"
                                    />
                                  </td>
                                  <td className="px-4 py-3">
                                    <input
                                      type="number"
                                      min="0"
                                      step="0.01"
                                      value={item.precioUnitario}
                                      onChange={(e) => actualizarPrecio(index, parseFloat(e.target.value) || 0)}
                                      className="w-28 px-2 py-1.5 bg-slate-800/60 border border-slate-600/50 rounded text-slate-200 text-sm focus:outline-none focus:border-primary"
                                    />
                                  </td>
                                  <td className="px-4 py-3 text-sm text-white/90 font-semibold">
                                    ${item.subtotal.toFixed(2)}
                                  </td>
                                  <td className="px-4 py-3 text-center">
                                    <button
                                      type="button"
                                      onClick={() => eliminarProducto(index)}
                                      className="text-red-400 hover:text-red-300 transition-colors p-1 rounded hover:bg-red-500/10"
                                      title="Eliminar producto"
                                    >
                                      <span className="material-symbols-outlined text-lg">delete</span>
                                    </button>
                                  </td>
                                </tr>
                              ))}
                            </tbody>
                          </table>
                        </div>
                      </div>
                    </div>
                  )}
                </div>
              </div>
            </form>
          )}
        </div>

        {/* Footer */}
        <div className="sticky bottom-0 bg-[#1e2a3a] border-t border-white/10 p-6 flex justify-between items-center">
          <div className="flex items-center gap-6">
            <div className="flex flex-col">
              <div className="text-white/60 text-sm">Total de la venta</div>
              <div className="text-white font-bold text-3xl">
                ${calcularTotal().toFixed(2)}
              </div>
            </div>
            {productosVenta.length > 0 && (
              <div className="flex flex-col border-l border-white/10 pl-6">
                <div className="text-white/60 text-sm">Subtotal</div>
                <div className="text-white/90 font-semibold text-lg">
                  ${calcularSubtotal().toFixed(2)}
                </div>
                <div className="text-white/60 text-xs mt-1">IVA: ${calcularIvaTotal().toFixed(2)}</div>
              </div>
            )}
          </div>
          <div className="flex gap-4">
            <Button
              type="button"
              variant="secondary"
              onClick={handleClose}
              disabled={submitting}
            >
              Cancelar
            </Button>
            <Button
              type="button"
              variant="secondary"
              onClick={() => {
                // Función para imprimir factura
                window.print();
              }}
              disabled={submitting || productosVenta.length === 0}
              className="min-w-[160px] flex items-center justify-center gap-2"
            >
              <span className="material-symbols-outlined text-lg text-primary">print</span>
              <span className="text-primary">Imprimir Factura</span>
            </Button>
            <Button
              type="submit"
              variant="secondary"
              onClick={handleSubmit}
              disabled={submitting || productosVenta.length === 0}
              className="min-w-[160px] flex items-center justify-center gap-2"
            >
              {submitting ? (
                <>
                  <Spinner size="small" />
                  <span className="text-primary">Guardando...</span>
                </>
              ) : (
                <>
                  <span className="material-symbols-outlined text-lg text-primary">save</span>
                  <span className="text-primary">Guardar Venta</span>
                </>
              )}
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default NuevaVentaModal;

