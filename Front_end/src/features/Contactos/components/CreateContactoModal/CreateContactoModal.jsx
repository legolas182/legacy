import React, { useState, useEffect } from 'react';
import { useForm } from '../../../../hooks/useForm/useForm';
import { contactosService } from '../../../../services/contactosService/contactosService';
import { rolesService } from '../../../../services/rolesService/rolesService';
import { sucursalesService } from '../../../../services/sucursalesService/sucursalesService';
import FormField from '../../../../components/molecules/FormField/FormField';
import Button from '../../../../components/atoms/Button/Button';
import Input from '../../../../components/atoms/Input/Input';
import { validators } from '../../../../utils/validators/validators';
import Spinner from '../../../../components/atoms/Spinner/Spinner';

const CreateContactoModal = ({ isOpen, onClose, onSuccess }) => {
  const { values, errors, handleChange, handleReset, setFieldError, setErrors, setFieldValue } = useForm({
    nombre: '',
    tipoContacto: 'CLIENTE',
    email: '',
    telefono: '',
    direccion: '',
    empresa: '',
    nit: '',
    rolId: '',
    sucursalId: '',
    username: '',
    password: '',
    activo: true,
  });

  const [roles, setRoles] = useState([]);
  const [sucursales, setSucursales] = useState([]);
  const [loadingRoles, setLoadingRoles] = useState(false);
  const [loadingSucursales, setLoadingSucursales] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  const [submitError, setSubmitError] = useState(null);

  useEffect(() => {
    if (isOpen) {
      loadRoles();
      loadSucursales();
    }
  }, [isOpen]);

  const loadRoles = async () => {
    try {
      setLoadingRoles(true);
      const response = await rolesService.getAll();
      setRoles(response.data || []);
    } catch (err) {
      console.error('Error al cargar roles:', err);
    } finally {
      setLoadingRoles(false);
    }
  };

  const loadSucursales = async () => {
    try {
      setLoadingSucursales(true);
      const response = await sucursalesService.getAll();
      setSucursales(response.data || []);
    } catch (err) {
      console.error('Error al cargar sucursales:', err);
    } finally {
      setLoadingSucursales(false);
    }
  };

  // Determinar si es empleado (tipo de contacto es EMPLEADO)
  const isEmpleado = values.tipoContacto === 'EMPLEADO';

  // Determinar qué campos mostrar según el tipo de contacto
  const isProveedor = values.tipoContacto === 'PROVEEDOR';

  // Buscar el rol de empleado automáticamente cuando se selecciona tipo EMPLEADO
  useEffect(() => {
    if (isEmpleado && roles.length > 0) {
      const rolEmpleado = roles.find(rol => 
        rol.nombre.toUpperCase() === 'EMPLEADO' || 
        rol.nombre.toUpperCase() === 'EMPLOYEE'
      );
      if (rolEmpleado && values.rolId !== rolEmpleado.id.toString()) {
        setFieldValue('rolId', rolEmpleado.id.toString());
      }
    }
  }, [isEmpleado, roles]);

  // Limpiar campos de empleado cuando cambia el tipo de contacto
  useEffect(() => {
    if (!isEmpleado && values.rolId) {
      setFieldValue('rolId', '');
      setFieldValue('username', '');
      setFieldValue('password', '');
      setFieldValue('sucursalId', '');
    }
  }, [isEmpleado]);

  const validateForm = () => {
    const newErrors = {};

    // Validación básica: nombre es requerido
    if (!validators.required(values.nombre)) {
      newErrors.nombre = 'El nombre es requerido';
    }

    // Validación de email si se proporciona
    if (values.email && !validators.email(values.email)) {
      newErrors.email = 'El email no es válido';
    }

    // Validaciones para empleado
    if (isEmpleado) {
      if (!validators.required(values.username)) {
        newErrors.username = 'El username es requerido para empleados';
      }
      if (!validators.required(values.password)) {
        newErrors.password = 'La contraseña es requerida para empleados';
      } else if (!validators.password(values.password)) {
        newErrors.password = 'La contraseña debe tener al menos 6 caracteres';
      }
      if (!validators.required(values.sucursalId)) {
        newErrors.sucursalId = 'La sucursal es requerida para empleados';
      }
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

      // Datos básicos del contacto
      const contactoData = {
        nombre: values.nombre.trim(),
        tipoContacto: values.tipoContacto,
        email: values.email.trim() || null,
        telefono: values.telefono.trim() || null,
        direccion: values.direccion.trim() || null,
        activo: values.activo,
      };

      // Agregar campos de proveedor solo si es PROVEEDOR
      if (isProveedor) {
        contactoData.empresa = values.empresa.trim() || null;
        contactoData.nit = values.nit.trim() || null;
      }

      // Agregar campos de empleado (crea usuario)
      if (isEmpleado && values.rolId) {
        contactoData.rol = { id: parseInt(values.rolId) };
        contactoData.sucursal = { id: parseInt(values.sucursalId) };
        contactoData.username = values.username.trim();
        contactoData.password = values.password.trim();
      }

      await contactosService.create(contactoData);
      
      handleReset();
      onSuccess?.();
      onClose();
    } catch (err) {
      console.error('Error al crear contacto:', err);
      const errorMessage = err.response?.data?.message || err.response?.data || 'Error al crear el contacto';
      setSubmitError(typeof errorMessage === 'string' ? errorMessage : 'Error al crear el contacto');
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
          <h2 className="text-white text-2xl font-bold">Nuevo Contacto</h2>
          <button
            onClick={handleClose}
            disabled={submitting}
            className="text-slate-400 hover:text-white transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
          >
            <span className="material-symbols-outlined text-2xl">close</span>
          </button>
        </div>

        {/* Form */}
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
                placeholder="Nombre completo"
                error={errors.nombre}
                required
              />

              {/* Tipo de Contacto */}
              <div className="flex flex-col gap-2 w-full">
                <label className="text-sm font-medium text-slate-300">Tipo de Contacto *</label>
                <select
                  name="tipoContacto"
                  value={values.tipoContacto}
                  onChange={(e) => {
                    handleChange(e);
                    // Si cambia a CLIENTE, limpiar campos de proveedor
                    if (e.target.value === 'CLIENTE') {
                      setFieldValue('empresa', '');
                      setFieldValue('nit', '');
                    }
                  }}
                  className="w-full pl-4 pr-4 py-3.5 bg-slate-800/60 border border-slate-600/50 rounded-lg text-slate-200 text-[15px] transition-all duration-300 outline-none focus:border-primary focus:bg-slate-800/80 focus:shadow-[0_0_0_3px_rgba(81,160,251,0.1)]"
                  required
                >
                  <option value="CLIENTE">Cliente</option>
                  <option value="PROVEEDOR">Proveedor</option>
                  <option value="EMPLEADO">Empleado</option>
                  <option value="ADMIN">Admin</option>
                </select>
              </div>

              {/* Email */}
              <FormField
                label="Email"
                name="email"
                type="email"
                value={values.email}
                onChange={handleChange}
                placeholder="email@ejemplo.com"
                error={errors.email}
              />

              {/* Teléfono */}
              <FormField
                label="Teléfono"
                name="telefono"
                value={values.telefono}
                onChange={handleChange}
                placeholder="Teléfono"
              />

              {/* Dirección */}
              <FormField
                label="Dirección"
                name="direccion"
                value={values.direccion}
                onChange={handleChange}
                placeholder="Dirección"
              />

            </div>

            {/* Columna derecha */}
            <div className="space-y-6">
              {/* Campos de Proveedor - Solo se muestran si es PROVEEDOR */}
              {isProveedor && (
                <>
                  {/* Empresa */}
                  <FormField
                    label="Empresa"
                    name="empresa"
                    value={values.empresa}
                    onChange={handleChange}
                    placeholder="Nombre de la empresa"
                  />

                  {/* NIT */}
                  <FormField
                    label="NIT"
                    name="nit"
                    value={values.nit}
                    onChange={handleChange}
                    placeholder="Número de identificación tributaria"
                  />
                </>
              )}

              {/* Campos de Empleado - Solo se muestran si es EMPLEADO */}
              {isEmpleado && (
                <>
                  {/* Sucursal */}
                  <div className="flex flex-col gap-2 w-full">
                    <label className="text-sm font-medium text-slate-300">Sucursal *</label>
                    {loadingSucursales ? (
                      <div className="flex items-center justify-center py-3">
                        <Spinner size="small" />
                      </div>
                    ) : (
                      <select
                        name="sucursalId"
                        value={values.sucursalId}
                        onChange={handleChange}
                        className={`w-full pl-4 pr-4 py-3.5 bg-slate-800/60 border ${
                          errors.sucursalId ? 'border-red-500' : 'border-slate-600/50'
                        } rounded-lg text-slate-200 text-[15px] transition-all duration-300 outline-none focus:border-primary focus:bg-slate-800/80 focus:shadow-[0_0_0_3px_rgba(81,160,251,0.1)]`}
                        required
                      >
                        <option value="">Seleccionar sucursal *</option>
                        {sucursales.map((sucursal) => (
                          <option key={sucursal.id} value={sucursal.id}>
                            {sucursal.nombre}
                          </option>
                        ))}
                      </select>
                    )}
                    {errors.sucursalId && <span className="text-sm text-red-400 mt-1">{errors.sucursalId}</span>}
                  </div>

                  {/* Username */}
                  <FormField
                    label="Username *"
                    name="username"
                    value={values.username}
                    onChange={handleChange}
                    placeholder="Nombre de usuario"
                    error={errors.username}
                    required
                  />

                  {/* Password */}
                  <FormField
                    label="Contraseña *"
                    name="password"
                    type="password"
                    value={values.password}
                    onChange={handleChange}
                    placeholder="Contraseña (mínimo 6 caracteres)"
                    error={errors.password}
                    required
                  />
                </>
              )}

              {/* Activo */}
              <div className="flex items-center gap-3">
                <input
                  type="checkbox"
                  name="activo"
                  id="activo"
                  checked={values.activo}
                  onChange={(e) => handleChange({ target: { name: 'activo', value: e.target.checked } })}
                  className="w-5 h-5 rounded border-slate-600 bg-slate-800 text-primary focus:ring-2 focus:ring-primary/50 cursor-pointer"
                />
                <label htmlFor="activo" className="text-sm font-medium text-slate-300 cursor-pointer">
                  Contacto activo
                </label>
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
                  Creando...
                </>
              ) : (
                'Crear Contacto'
              )}
            </Button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default CreateContactoModal;

