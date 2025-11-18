import React, { useState, useEffect } from 'react';
import { useForm } from '../../../../hooks/useForm/useForm';
import { contactosService } from '../../../../services/contactosService/contactosService';
import { rolesService } from '../../../../services/rolesService/rolesService';
import { sucursalesService } from '../../../../services/sucursalesService/sucursalesService';
import FormField from '../../../../components/molecules/FormField/FormField';
import Button from '../../../../components/atoms/Button/Button';
import { validators } from '../../../../utils/validators/validators';
import Spinner from '../../../../components/atoms/Spinner/Spinner';

const EditContactoModal = ({ isOpen, onClose, onSuccess, contactoId }) => {
  const { values, errors, handleChange, handleReset, setFieldError, setErrors, setFieldValue, setValues } = useForm({
    nombre: '',
    rolId: '',
    email: '',
    telefono: '',
    direccion: '',
    empresa: '',
    nit: '',
    sucursalId: '',
    username: '',
    password: '',
    activo: true,
  });

  const [roles, setRoles] = useState([]);
  const [sucursales, setSucursales] = useState([]);
  const [loadingRoles, setLoadingRoles] = useState(false);
  const [loadingSucursales, setLoadingSucursales] = useState(false);
  const [loadingContacto, setLoadingContacto] = useState(false);
  const [submitting, setSubmitting] = useState(false);
  const [submitError, setSubmitError] = useState(null);
  const [contactoOriginal, setContactoOriginal] = useState(null);

  useEffect(() => {
    if (isOpen && contactoId) {
      loadRoles();
      loadSucursales();
      loadContacto();
    }
  }, [isOpen, contactoId]);

  // Limpiar datos cuando se cierra el modal
  useEffect(() => {
    if (!isOpen) {
      handleReset();
      setSubmitError(null);
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

  const loadContacto = async () => {
    if (!contactoId) return;

    try {
      setLoadingContacto(true);
      const response = await contactosService.getById(contactoId);
      const contacto = response.data;

      if (contacto) {
        // Guardar el contacto original para hacer merge después
        setContactoOriginal(contacto);
        
        setValues({
          nombre: contacto.nombre || '',
          email: contacto.email || '',
          telefono: contacto.telefono || '',
          direccion: contacto.direccion || '',
          empresa: contacto.empresa || '',
          nit: contacto.nit || '',
          rolId: contacto.rol?.id?.toString() || '',
          sucursalId: contacto.sucursal?.id?.toString() || '',
          username: contacto.username || '',
          password: '', // No cargar la contraseña por seguridad
          activo: contacto.activo !== undefined ? contacto.activo : true,
        });
      }
    } catch (err) {
      console.error('Error al cargar contacto:', err);
      setSubmitError('Error al cargar los datos del contacto');
    } finally {
      setLoadingContacto(false);
    }
  };

  // Obtener el rol seleccionado
  const rolSeleccionado = roles.find(rol => rol.id.toString() === values.rolId);
  const nombreRol = rolSeleccionado?.nombre?.toUpperCase() || '';

  // Determinar si necesita usuario y contraseña (EMPLEADO o ADMIN)
  const necesitaUsuario = nombreRol === 'EMPLEADO' || nombreRol === 'ADMIN';
  const isEmpleado = nombreRol === 'EMPLEADO';
  const isAdmin = nombreRol === 'ADMIN';

  // Determinar qué campos mostrar según el rol
  const isProveedor = nombreRol === 'PROVEEDOR';

  // Limpiar campos de usuario cuando cambia el rol a uno que no necesita usuario
  useEffect(() => {
    if (!necesitaUsuario && (values.username || values.password || values.sucursalId)) {
      setFieldValue('username', '');
      setFieldValue('password', '');
      setFieldValue('sucursalId', '');
    }
    // Si cambia a un rol que no es PROVEEDOR, limpiar campos de proveedor
    if (!isProveedor && (values.empresa || values.nit)) {
      setFieldValue('empresa', '');
      setFieldValue('nit', '');
    }
  }, [necesitaUsuario, isProveedor, values.rolId]);

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

    // Validar que se seleccione un rol
    if (!validators.required(values.rolId)) {
      newErrors.rolId = 'El rol es requerido';
    }

    // Validaciones para empleado y admin (requieren usuario y contraseña solo si se está creando nuevo usuario)
    if (necesitaUsuario) {
      if (!validators.required(values.username)) {
        newErrors.username = `El username es requerido para ${isAdmin ? 'administradores' : 'empleados'}`;
      }
      // La contraseña solo es requerida si se está cambiando (si hay valor, debe ser válida)
      if (values.password && !validators.password(values.password)) {
        newErrors.password = 'La contraseña debe tener al menos 6 caracteres';
      }
      // La sucursal es requerida solo para empleados, no para administradores
      if (!isAdmin && !validators.required(values.sucursalId)) {
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

      if (!contactoOriginal) {
        setSubmitError('Error: No se pudo cargar el contacto original');
        return;
      }

      // Construir el objeto de actualización basado en el contacto original
      const contactoData = {
        id: contactoId,
        nombre: values.nombre.trim(),
        email: values.email.trim() || null,
        telefono: values.telefono.trim() || null,
        direccion: values.direccion.trim() || null,
        activo: values.activo,
        // Mantener campos del contacto original
        fechaCreacion: contactoOriginal.fechaCreacion || null,
        ultimoAcceso: contactoOriginal.ultimoAcceso || null,
      };

      // Agregar campos de proveedor
      if (isProveedor) {
        contactoData.empresa = values.empresa.trim() || null;
        contactoData.nit = values.nit.trim() || null;
      } else {
        // Si no es proveedor, limpiar estos campos
        contactoData.empresa = null;
        contactoData.nit = null;
      }

      // Agregar rol (siempre requerido)
      if (values.rolId) {
        contactoData.rol = { id: parseInt(values.rolId) };
      }

      // Agregar campos de usuario (actualiza usuario para empleados y administradores)
      if (necesitaUsuario && values.rolId) {
        // Solo asignar sucursal si se proporciona (requerida para empleados, opcional para admin)
        if (values.sucursalId) {
          contactoData.sucursal = { id: parseInt(values.sucursalId) };
        } else {
          // Para admin, la sucursal puede ser null (pertenecen a todas)
          contactoData.sucursal = null;
        }
        
        contactoData.username = values.username.trim();
        
        // IMPORTANTE: Solo enviar la contraseña si se proporcionó una nueva
        if (values.password && values.password.trim()) {
          contactoData.password = values.password.trim();
        }
      } else {
        // Si no necesita usuario, limpiar estos campos
        contactoData.sucursal = null;
        contactoData.username = null;
      }

      // Validar que los datos estén completos antes de enviar
      if (!contactoData.nombre || contactoData.nombre.trim() === '') {
        setSubmitError('Error: El nombre es requerido');
        return;
      }

      console.log('Datos a enviar:', JSON.stringify(contactoData, null, 2));
      
      try {
        const response = await contactosService.update(contactoId, contactoData);
        console.log('Respuesta del servidor:', response);
      } catch (updateError) {
        console.error('Error específico en update:', updateError);
        throw updateError;
      }
      
      handleReset();
      onSuccess?.();
      onClose();
    } catch (err) {
      console.error('Error al actualizar contacto:', err);
      console.error('Detalles del error:', {
        message: err.message,
        response: err.response,
        data: err.response?.data,
        status: err.response?.status,
      });
      
      let errorMessage = 'Error al actualizar el contacto';
      
      if (err.response?.data) {
        if (typeof err.response.data === 'string') {
          errorMessage = err.response.data;
        } else if (err.response.data.message) {
          errorMessage = err.response.data.message;
        } else if (err.response.data.error) {
          errorMessage = err.response.data.error;
        } else {
          errorMessage = JSON.stringify(err.response.data);
        }
      } else if (err.message) {
        errorMessage = err.message;
      }
      
      setSubmitError(errorMessage);
    } finally {
      setSubmitting(false);
    }
  };

  const handleClose = () => {
    if (!submitting && !loadingContacto) {
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
          <h2 className="text-white text-2xl font-bold">Editar Contacto</h2>
          <button
            onClick={handleClose}
            disabled={submitting || loadingContacto}
            className="text-slate-400 hover:text-white transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
          >
            <span className="material-symbols-outlined text-2xl">close</span>
          </button>
        </div>

        {/* Loading State */}
        {loadingContacto ? (
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
                  placeholder="Nombre completo"
                  error={errors.nombre}
                  required
                />

                {/* Rol */}
                <div className="flex flex-col gap-2 w-full">
                  <label className="text-sm font-medium text-slate-300">Rol *</label>
                  {loadingRoles ? (
                    <div className="flex items-center justify-center py-3">
                      <Spinner size="small" />
                    </div>
                  ) : (
                    <select
                      name="rolId"
                      value={values.rolId}
                      onChange={(e) => {
                        handleChange(e);
                        // Si cambia a un rol que no es PROVEEDOR, limpiar campos de proveedor
                        const rolSeleccionado = roles.find(r => r.id.toString() === e.target.value);
                        if (rolSeleccionado?.nombre?.toUpperCase() !== 'PROVEEDOR') {
                          setFieldValue('empresa', '');
                          setFieldValue('nit', '');
                        }
                      }}
                      className={`w-full pl-4 pr-4 py-3.5 bg-slate-800/60 border ${
                        errors.rolId ? 'border-red-500' : 'border-slate-600/50'
                      } rounded-lg text-slate-200 text-[15px] transition-all duration-300 outline-none focus:border-primary focus:bg-slate-800/80 focus:shadow-[0_0_0_3px_rgba(81,160,251,0.1)]`}
                      required
                    >
                      <option value="">Seleccionar rol *</option>
                      {roles.map((rol) => (
                        <option key={rol.id} value={rol.id}>
                          {rol.nombre}
                        </option>
                      ))}
                    </select>
                  )}
                  {errors.rolId && <span className="text-sm text-red-400 mt-1">{errors.rolId}</span>}
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

                {/* Campos de Usuario - Solo se muestran si es EMPLEADO o ADMIN */}
                {necesitaUsuario && (
                  <>
                    {/* Sucursal */}
                    <div className="flex flex-col gap-2 w-full">
                      <label className="text-sm font-medium text-slate-300">
                        Sucursal {isAdmin ? '(Opcional - Admin pertenece a todas)' : '*'}
                      </label>
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
                          required={!isAdmin}
                        >
                          <option value="">{isAdmin ? 'Sin sucursal (pertenece a todas)' : 'Seleccionar sucursal *'}</option>
                          {sucursales.map((sucursal) => (
                            <option key={sucursal.id} value={sucursal.id}>
                              {sucursal.nombre}
                            </option>
                          ))}
                        </select>
                      )}
                      {errors.sucursalId && <span className="text-sm text-red-400 mt-1">{errors.sucursalId}</span>}
                      {isAdmin && (
                        <p className="text-xs text-slate-400 mt-1">
                          Los administradores pertenecen a todas las sucursales. Puede dejar este campo vacío.
                        </p>
                      )}
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
                      label="Contraseña (dejar vacío para no cambiar)"
                      name="password"
                      type="password"
                      value={values.password}
                      onChange={handleChange}
                      placeholder="Nueva contraseña (mínimo 6 caracteres)"
                      error={errors.password}
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
                    Actualizando...
                  </>
                ) : (
                  'Actualizar Contacto'
                )}
              </Button>
            </div>
          </form>
        )}
      </div>
    </div>
  );
};

export default EditContactoModal;


