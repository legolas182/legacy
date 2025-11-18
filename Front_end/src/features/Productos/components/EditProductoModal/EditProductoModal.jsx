import React, { useState, useEffect } from 'react';
import { useForm } from '../../../../hooks/useForm/useForm';
import { productosService } from '../../../../services/productosService/productosService';
import { categoriasService } from '../../../../services/categoriasService/categoriasService';
import { laboratoriosService } from '../../../../services/laboratoriosService/laboratoriosService';
import FormField from '../../../../components/molecules/FormField/FormField';
import Button from '../../../../components/atoms/Button/Button';
import Spinner from '../../../../components/atoms/Spinner/Spinner';
import { validators } from '../../../../utils/validators/validators';

const EditProductoModal = ({ isOpen, onClose, onSuccess, productoId }) => {
  const { values, errors, handleChange, handleReset, setErrors, setFieldValue, setValues } = useForm({
    nombre: '',
    concentracion: '',
    formaFarmaceutica: '',
    viaAdministracion: '',
    registroInvima: '',
    requiereFormula: false,
    categoriaId: '',
    laboratorioId: '',
    valorCompra: '',
    valorVenta: '',
    porcentajeIva: '19.00',
  });

  const [categorias, setCategorias] = useState([]);
  const [laboratorios, setLaboratorios] = useState([]);
  const [loadingCategorias, setLoadingCategorias] = useState(false);
  const [loadingLaboratorios, setLoadingLaboratorios] = useState(false);
  const [loadingProducto, setLoadingProducto] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  const [submitError, setSubmitError] = useState(null);

  useEffect(() => {
    if (isOpen && productoId) {
      // Solo cargar datos cuando el modal se abre y hay un ID
      loadCategorias();
      loadLaboratorios();
      loadProducto();
    }
  }, [isOpen, productoId]);

  // Limpiar datos cuando se cierra el modal
  useEffect(() => {
    if (!isOpen) {
      handleReset();
      setSubmitError(null);
    }
  }, [isOpen]);

  const loadCategorias = async () => {
    try {
      setLoadingCategorias(true);
      const response = await categoriasService.getAll();
      setCategorias(response.data || []);
    } catch (err) {
      console.error('Error al cargar categorías:', err);
    } finally {
      setLoadingCategorias(false);
    }
  };

  const loadLaboratorios = async () => {
    try {
      setLoadingLaboratorios(true);
      const response = await laboratoriosService.getAll();
      setLaboratorios(response.data || []);
    } catch (err) {
      console.error('Error al cargar laboratorios:', err);
    } finally {
      setLoadingLaboratorios(false);
    }
  };

  const loadProducto = async () => {
    try {
      setLoadingProducto(true);
      setSubmitError(null);
      
      if (!productoId) {
        console.error('No se proporcionó un ID de producto');
        setSubmitError('No se proporcionó un ID de producto');
        return;
      }

      const response = await productosService.getById(productoId);
      console.log('Respuesta completa del API:', response);
      
      // Manejar diferentes formatos de respuesta
      const producto = response?.data || response;
      console.log('Datos del producto:', producto);

      if (producto) {
        // Manejar tanto camelCase como snake_case
        const nombre = producto.nombre || '';
        const concentracion = producto.concentracion || '';
        const formaFarmaceutica = producto.formaFarmaceutica || producto.forma_farmaceutica || '';
        const viaAdministracion = producto.viaAdministracion || producto.via_administracion || '';
        const registroInvima = producto.registroInvima || producto.registro_invima || '';
        const requiereFormula = producto.requiereFormula !== undefined ? producto.requiereFormula : 
                                (producto.requiere_formula !== undefined ? producto.requiere_formula : false);
        
        // Categoría y Laboratorio
        const categoriaId = producto.categoria?.id?.toString() || '';
        const laboratorioId = producto.laboratorio?.id?.toString() || '';
        
        // Valores monetarios - manejar BigDecimal que viene como número o string
        const valorCompra = producto.valorCompra !== null && producto.valorCompra !== undefined 
          ? producto.valorCompra.toString() 
          : (producto.valor_compra !== null && producto.valor_compra !== undefined 
            ? producto.valor_compra.toString() 
            : '');
        const valorVenta = producto.valorVenta !== null && producto.valorVenta !== undefined 
          ? producto.valorVenta.toString() 
          : (producto.valor_venta !== null && producto.valor_venta !== undefined 
            ? producto.valor_venta.toString() 
            : '');
        
        // Porcentaje de IVA
        const porcentajeIva = producto.porcentajeIva !== null && producto.porcentajeIva !== undefined 
          ? producto.porcentajeIva.toString() 
          : (producto.porcentaje_iva !== null && producto.porcentaje_iva !== undefined 
            ? producto.porcentaje_iva.toString() 
            : '19.00');

        console.log('Valores a establecer:', {
          nombre,
          concentracion,
          formaFarmaceutica,
          viaAdministracion,
          registroInvima,
          requiereFormula,
          categoriaId,
          laboratorioId,
          valorCompra,
          valorVenta,
          porcentajeIva
        });

        // Establecer todos los valores usando setValues directamente para evitar problemas de sincronización
        setValues({
          nombre,
          concentracion,
          formaFarmaceutica,
          viaAdministracion,
          registroInvima,
          requiereFormula,
          categoriaId,
          laboratorioId,
          valorCompra,
          valorVenta,
          porcentajeIva,
        });
      } else {
        console.error('No se encontraron datos del producto');
        setSubmitError('No se encontraron datos del producto');
      }
    } catch (err) {
      console.error('Error al cargar producto:', err);
      console.error('Detalles del error:', err.response);
      const errorMessage = err.response?.data?.message || err.message || 'Error al cargar los datos del producto';
      setSubmitError(errorMessage);
    } finally {
      setLoadingProducto(false);
    }
  };

  const validateForm = () => {
    const newErrors = {};

    // Validación básica: nombre es requerido
    if (!validators.required(values.nombre)) {
      newErrors.nombre = 'El nombre es requerido';
    }

    // Validación de valores numéricos
    if (values.valorCompra && isNaN(parseFloat(values.valorCompra))) {
      newErrors.valorCompra = 'El valor de compra debe ser un número válido';
    } else if (values.valorCompra && parseFloat(values.valorCompra) < 0) {
      newErrors.valorCompra = 'El valor de compra no puede ser negativo';
    }

    if (values.valorVenta && isNaN(parseFloat(values.valorVenta))) {
      newErrors.valorVenta = 'El valor de venta debe ser un número válido';
    } else if (values.valorVenta && parseFloat(values.valorVenta) < 0) {
      newErrors.valorVenta = 'El valor de venta no puede ser negativo';
    }

    // Validación de porcentaje IVA
    if (values.porcentajeIva && isNaN(parseFloat(values.porcentajeIva))) {
      newErrors.porcentajeIva = 'El porcentaje de IVA debe ser un número válido';
    } else if (values.porcentajeIva && (parseFloat(values.porcentajeIva) < 0 || parseFloat(values.porcentajeIva) > 100)) {
      newErrors.porcentajeIva = 'El porcentaje de IVA debe estar entre 0 y 100';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSubmitError(null);

    if (!validateForm()) {
      return;
    }

    try {
      setSubmitting(true);

      const productoData = {
        nombre: values.nombre.trim(),
        concentracion: values.concentracion.trim() || null,
        formaFarmaceutica: values.formaFarmaceutica.trim() || null,
        viaAdministracion: values.viaAdministracion.trim() || null,
        registroInvima: values.registroInvima.trim() || null,
        requiereFormula: values.requiereFormula,
        categoria: values.categoriaId ? { id: parseInt(values.categoriaId) } : null,
        laboratorio: values.laboratorioId ? { id: parseInt(values.laboratorioId) } : null,
        valorCompra: values.valorCompra ? parseFloat(values.valorCompra) : null,
        valorVenta: values.valorVenta ? parseFloat(values.valorVenta) : null,
        porcentajeIva: values.porcentajeIva ? parseFloat(values.porcentajeIva) : 19.00,
      };

      await productosService.update(productoId, productoData);
      
      handleReset();
      onSuccess?.();
      onClose();
    } catch (err) {
      console.error('Error al actualizar producto:', err);
      const errorMessage = err.response?.data?.message || err.response?.data || 'Error al actualizar el producto';
      setSubmitError(typeof errorMessage === 'string' ? errorMessage : 'Error al actualizar el producto');
    } finally {
      setSubmitting(false);
    }
  };

  const handleClose = () => {
    if (!submitting) {
      handleReset();
      setSubmitError(null);
      onClose();
    }
  };

  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/60 backdrop-blur-sm">
      <div className="bg-[#1e2a3a] rounded-lg border border-white/10 w-full max-w-5xl max-h-[90vh] overflow-y-auto mx-4 shadow-2xl">
        {/* Header */}
        <div className="sticky top-0 bg-[#1e2a3a] border-b border-white/10 p-6 flex justify-between items-center">
          <h2 className="text-white text-2xl font-bold">Editar Producto</h2>
          <button
            onClick={handleClose}
            disabled={submitting || loadingProducto}
            className="text-slate-400 hover:text-white transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
          >
            <span className="material-symbols-outlined text-2xl">close</span>
          </button>
        </div>

        {/* Loading State */}
        {loadingProducto ? (
          <div className="flex items-center justify-center py-12">
            <Spinner />
          </div>
        ) : (
          /* Form */
          <form onSubmit={handleSubmit} className="p-6">
            {/* Error Message */}
            {submitError && (
              <div className="mb-6 p-4 bg-red-500/20 border border-red-500/50 rounded-lg text-red-200">
                {submitError}
              </div>
            )}

            {/* Grid horizontal de 2 columnas */}
            <div className="grid grid-cols-2 gap-6">
              {/* Columna izquierda */}
              <div className="space-y-6">
                {/* Nombre */}
                <FormField
                  label="Nombre *"
                  name="nombre"
                  value={values.nombre}
                  onChange={handleChange}
                  placeholder="Nombre del producto"
                  error={errors.nombre}
                  required
                />

                {/* Concentración */}
                <FormField
                  label="Concentración"
                  name="concentracion"
                  value={values.concentracion}
                  onChange={handleChange}
                  placeholder="Ej: 500mg, 10ml"
                />

                {/* Forma Farmacéutica */}
                <FormField
                  label="Forma Farmacéutica"
                  name="formaFarmaceutica"
                  value={values.formaFarmaceutica}
                  onChange={handleChange}
                  placeholder="Ej: Tableta, Jarabe, Crema"
                />

                {/* Vía de Administración */}
                <FormField
                  label="Vía de Administración"
                  name="viaAdministracion"
                  value={values.viaAdministracion}
                  onChange={handleChange}
                  placeholder="Ej: Oral, Tópica, Intramuscular"
                />

                {/* Registro INVIMA */}
                <FormField
                  label="Registro INVIMA"
                  name="registroInvima"
                  value={values.registroInvima}
                  onChange={handleChange}
                  placeholder="Número de registro INVIMA"
                />

                {/* Requiere Fórmula */}
                <div className="flex items-center gap-3">
                  <input
                    type="checkbox"
                    name="requiereFormula"
                    id="requiereFormula"
                    checked={values.requiereFormula}
                    onChange={(e) => handleChange({ target: { name: 'requiereFormula', value: e.target.checked } })}
                    className="w-5 h-5 rounded border-slate-600 bg-slate-800 text-primary focus:ring-2 focus:ring-primary/50 cursor-pointer"
                  />
                  <label htmlFor="requiereFormula" className="text-sm font-medium text-slate-300 cursor-pointer">
                    Requiere fórmula médica
                  </label>
                </div>
              </div>

              {/* Columna derecha */}
              <div className="space-y-6">
                {/* Categoría */}
                <div className="flex flex-col gap-2 w-full">
                  <label className="text-sm font-medium text-slate-300">Categoría</label>
                  {loadingCategorias ? (
                    <div className="flex items-center justify-center py-3">
                      <Spinner size="small" />
                    </div>
                  ) : (
                    <select
                      name="categoriaId"
                      value={values.categoriaId}
                      onChange={handleChange}
                      className="w-full pl-4 pr-4 py-3.5 bg-slate-800/60 border border-slate-600/50 rounded-lg text-slate-200 text-[15px] transition-all duration-300 outline-none focus:border-primary focus:bg-slate-800/80 focus:shadow-[0_0_0_3px_rgba(81,160,251,0.1)]"
                    >
                      <option value="">Seleccionar categoría</option>
                      {categorias.map((categoria) => (
                        <option key={categoria.id} value={categoria.id}>
                          {categoria.nombre}
                        </option>
                      ))}
                    </select>
                  )}
                </div>

                {/* Laboratorio */}
                <div className="flex flex-col gap-2 w-full">
                  <label className="text-sm font-medium text-slate-300">Laboratorio</label>
                  {loadingLaboratorios ? (
                    <div className="flex items-center justify-center py-3">
                      <Spinner size="small" />
                    </div>
                  ) : (
                    <select
                      name="laboratorioId"
                      value={values.laboratorioId}
                      onChange={handleChange}
                      className="w-full pl-4 pr-4 py-3.5 bg-slate-800/60 border border-slate-600/50 rounded-lg text-slate-200 text-[15px] transition-all duration-300 outline-none focus:border-primary focus:bg-slate-800/80 focus:shadow-[0_0_0_3px_rgba(81,160,251,0.1)]"
                    >
                      <option value="">Seleccionar laboratorio</option>
                      {laboratorios.map((laboratorio) => (
                        <option key={laboratorio.id} value={laboratorio.id}>
                          {laboratorio.nombre}
                        </option>
                      ))}
                    </select>
                  )}
                </div>

                {/* Valor de Compra */}
                <FormField
                  label="Valor de Compra"
                  name="valorCompra"
                  type="number"
                  step="0.01"
                  min="0"
                  value={values.valorCompra}
                  onChange={handleChange}
                  placeholder="0.00"
                  error={errors.valorCompra}
                />

                {/* Valor de Venta */}
                <FormField
                  label="Valor de Venta"
                  name="valorVenta"
                  type="number"
                  step="0.01"
                  min="0"
                  value={values.valorVenta}
                  onChange={handleChange}
                  placeholder="0.00"
                  error={errors.valorVenta}
                />

                {/* Porcentaje de IVA */}
                <FormField
                  label="Porcentaje de IVA (%)"
                  name="porcentajeIva"
                  type="number"
                  step="0.01"
                  min="0"
                  max="100"
                  value={values.porcentajeIva}
                  onChange={handleChange}
                  placeholder="19.00"
                  error={errors.porcentajeIva}
                />

                {/* Info sobre cálculos automáticos */}
                <div className="p-4 bg-blue-500/10 border border-blue-500/30 rounded-lg">
                  <p className="text-sm text-blue-300">
                    <span className="material-symbols-outlined inline text-base mr-1">info</span>
                    La utilidad se calculará automáticamente. El IVA se aplicará al precio de venta según el porcentaje indicado.
                  </p>
                </div>
              </div>
            </div>

            {/* Actions */}
            <div className="flex gap-4 justify-end pt-4 border-t border-white/10">
              <Button
                type="button"
                variant="secondary"
                onClick={handleClose}
                disabled={submitting}
              >
                Cancelar
              </Button>
              <Button
                type="submit"
                variant="primary"
                disabled={submitting}
              >
                {submitting ? (
                  <>
                    <span className="inline-flex items-center mr-2">
                      <Spinner size="small" />
                    </span>
                    Guardando...
                  </>
                ) : (
                  'Guardar Cambios'
                )}
              </Button>
            </div>
          </form>
        )}
      </div>
    </div>
  );
};

export default EditProductoModal;

