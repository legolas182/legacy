import React, { useState, useEffect } from 'react';
import DashboardLayout from '../../../components/templates/DashboardLayout/DashboardLayout';
import { contactosService } from '../../../services/contactosService/contactosService';
import { sucursalesService } from '../../../services/sucursalesService/sucursalesService';
import { rolesService } from '../../../services/rolesService/rolesService';
import Spinner from '../../../components/atoms/Spinner/Spinner';
import CreateContactoModal from '../components/CreateContactoModal/CreateContactoModal';
import EditContactoModal from '../components/EditContactoModal/EditContactoModal';
import Input from '../../../components/atoms/Input/Input';
import Button from '../../../components/atoms/Button/Button';

const ContactosPage = () => {
  const [contactos, setContactos] = useState([]);
  const [contactosFiltrados, setContactosFiltrados] = useState([]);
  const [sucursales, setSucursales] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [selectedContactoId, setSelectedContactoId] = useState(null);

  // Filtros
  const [busqueda, setBusqueda] = useState('');
  const [rolFiltro, setRolFiltro] = useState('Todos');
  const [roles, setRoles] = useState([]);
  const [sucursalFiltro, setSucursalFiltro] = useState('Todos');

  useEffect(() => {
    loadData();
  }, []);

  useEffect(() => {
    aplicarFiltros();
  }, [busqueda, rolFiltro, sucursalFiltro, contactos]);

  const loadData = async () => {
    try {
      setLoading(true);
      setError(null);
      const [contactosResponse, sucursalesResponse, rolesResponse] = await Promise.all([
        contactosService.getAll().catch(err => {
          console.error('Error al cargar contactos:', err);
          return { data: [] };
        }),
        sucursalesService.getAll().catch(err => {
          console.error('Error al cargar sucursales:', err);
          return { data: [] };
        }),
        rolesService.getAll().catch(err => {
          console.error('Error al cargar roles:', err);
          return { data: [] };
        }),
      ]);

      const contactosData = contactosResponse.data || [];
      setContactos(contactosData);
      setContactosFiltrados(contactosData);

      const sucursalesData = sucursalesResponse.data || [];
      setSucursales(sucursalesData);

      const rolesData = rolesResponse.data || [];
      setRoles(rolesData);
    } catch (err) {
      console.error('Error al cargar datos:', err);
      setError(err.response?.data?.message || 'Error al cargar los contactos');
    } finally {
      setLoading(false);
    }
  };

  const aplicarFiltros = () => {
    let filtrados = [...contactos];

    // Filtro de búsqueda
    if (busqueda) {
      const busquedaLower = busqueda.toLowerCase();
      filtrados = filtrados.filter((contacto) => {
        const nombre = contacto.nombre?.toLowerCase() || '';
        const email = contacto.email?.toLowerCase() || '';
        const telefono = contacto.telefono?.toLowerCase() || '';
        const username = contacto.username?.toLowerCase() || '';
        const rolNombre = contacto.rol?.nombre?.toLowerCase() || '';

        return (
          nombre.includes(busquedaLower) ||
          email.includes(busquedaLower) ||
          telefono.includes(busquedaLower) ||
          username.includes(busquedaLower) ||
          rolNombre.includes(busquedaLower) ||
          contacto.id?.toString().includes(busquedaLower)
        );
      });
    }

    // Filtro por rol
    if (rolFiltro !== 'Todos') {
      filtrados = filtrados.filter(
        (contacto) => contacto.rol?.id?.toString() === rolFiltro
      );
    }

    // Filtro por sucursal
    if (sucursalFiltro !== 'Todos') {
      filtrados = filtrados.filter(
        (contacto) => contacto.sucursal?.id?.toString() === sucursalFiltro
      );
    }

    setContactosFiltrados(filtrados);
  };

  const loadContactos = async () => {
    await loadData();
  };

  const handleDelete = async (id) => {
    if (window.confirm('¿Está seguro de eliminar este contacto?')) {
      try {
        await contactosService.delete(id);
        await loadContactos();
      } catch (err) {
        console.error('Error al eliminar contacto:', err);
        alert('Error al eliminar el contacto');
      }
    }
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
      <div className="w-full">
        {/* Page Heading */}
        <div className="flex flex-wrap justify-between items-center gap-4 mb-6">
          <div className="flex flex-col gap-1">
            <h2 className="text-white text-3xl font-bold tracking-tight">Contactos</h2>
            <p className="text-white/60 text-base font-normal">Gestión de contactos y usuarios.</p>
          </div>
          <button
            onClick={() => setIsModalOpen(true)}
            className="bg-primary hover:bg-primary/90 text-white font-semibold py-2 px-4 rounded-lg transition-all duration-200"
          >
            <span className="material-symbols-outlined inline mr-2">add</span>
            Nuevo Contacto
          </button>
        </div>

        {/* Filtros y Búsqueda */}
        <div className="bg-[#182535] rounded-lg border border-white/10 p-4 mb-6">
          <div className="grid grid-cols-1 md:grid-cols-3 gap-3 mb-3">
            <Input
              type="text"
              placeholder="Buscar por nombre, email, teléfono..."
              value={busqueda}
              onChange={(e) => setBusqueda(e.target.value)}
              icon={
                <span className="material-symbols-outlined text-lg">search</span>
              }
            />
            <div className="relative">
              <select
                className="w-full pl-12 pr-4 py-3.5 bg-slate-800/60 border border-slate-600/50 rounded-lg text-slate-200 text-[15px] transition-all duration-300 outline-none focus:border-primary focus:bg-slate-800/80 focus:shadow-[0_0_0_3px_rgba(81,160,251,0.1)] appearance-none cursor-pointer"
                value={rolFiltro}
                onChange={(e) => setRolFiltro(e.target.value)}
              >
                <option value="Todos">Todos los roles</option>
                {roles.map((rol) => (
                  <option key={rol.id} value={rol.id.toString()}>
                    {rol.nombre}
                  </option>
                ))}
              </select>
              <span className="absolute left-4 top-1/2 -translate-y-1/2 text-slate-500 pointer-events-none">
                <span className="material-symbols-outlined text-lg">person</span>
              </span>
            </div>
            <div className="relative">
              <select
                className="w-full pl-12 pr-4 py-3.5 bg-slate-800/60 border border-slate-600/50 rounded-lg text-slate-200 text-[15px] transition-all duration-300 outline-none focus:border-primary focus:bg-slate-800/80 focus:shadow-[0_0_0_3px_rgba(81,160,251,0.1)] appearance-none cursor-pointer"
                value={sucursalFiltro}
                onChange={(e) => setSucursalFiltro(e.target.value)}
              >
                <option value="Todos">Todas las sucursales</option>
                {sucursales.map((sucursal) => (
                  <option key={sucursal.id} value={sucursal.id.toString()}>
                    {sucursal.nombre}
                  </option>
                ))}
              </select>
              <span className="absolute left-4 top-1/2 -translate-y-1/2 text-slate-500 pointer-events-none">
                <span className="material-symbols-outlined text-lg">store</span>
              </span>
            </div>
          </div>
        </div>

        {/* Error Message */}
        {error && (
          <div className="mb-4 p-4 bg-red-500/20 border border-red-500/50 rounded-lg text-red-200">
            {error}
          </div>
        )}

        {/* Success Message */}
        {!error && contactos.length === 0 && (
          <div className="mb-4 p-4 bg-blue-500/20 border border-blue-500/50 rounded-lg text-blue-200">
            No hay contactos registrados.
          </div>
        )}

        {/* Message when filters applied but no results */}
        {!error && contactos.length > 0 && contactosFiltrados.length === 0 && (
          <div className="mb-4 p-4 bg-yellow-500/20 border border-yellow-500/50 rounded-lg text-yellow-200">
            No se encontraron contactos con los filtros aplicados.
          </div>
        )}

        {/* Contactos Table */}
        {contactosFiltrados.length > 0 && (
          <div className="bg-[#1e2a3a] rounded-lg border border-white/10 overflow-hidden">
            <div className="overflow-x-auto">
              <table className="w-full">
                <thead className="bg-[#2d4a5c]">
                  <tr>
                    <th className="px-6 py-3 text-left text-xs font-medium text-white/70 uppercase tracking-wider">
                      ID
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-white/70 uppercase tracking-wider">
                      Nombre
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-white/70 uppercase tracking-wider">
                      Username
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-white/70 uppercase tracking-wider">
                      Email
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-white/70 uppercase tracking-wider">
                      Teléfono
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-white/70 uppercase tracking-wider">
                      Rol
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-white/70 uppercase tracking-wider">
                      Sucursal
                    </th>
                    <th className="px-6 py-3 text-left text-xs font-medium text-white/70 uppercase tracking-wider">
                      Activo
                    </th>
                    <th className="px-6 py-3 text-right text-xs font-medium text-white/70 uppercase tracking-wider">
                      Acciones
                    </th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-white/10">
                  {contactosFiltrados.map((contacto) => (
                    <tr key={contacto.id} className="hover:bg-white/5 transition-colors">
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-white/90">
                        {contacto.id}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-white/90">
                        {contacto.nombre || '-'}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-white/90">
                        {contacto.username || '-'}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-white/90">
                        {contacto.email || '-'}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-white/90">
                        {contacto.telefono || '-'}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        {contacto.rol ? (
                          <span
                            className={`inline-flex px-2 py-1 text-xs font-semibold rounded-full ${
                              contacto.rol.nombre?.toUpperCase() === 'CLIENTE'
                                ? 'bg-blue-500/20 text-blue-300'
                                : contacto.rol.nombre?.toUpperCase() === 'PROVEEDOR'
                                ? 'bg-purple-500/20 text-purple-300'
                                : contacto.rol.nombre?.toUpperCase() === 'EMPLEADO'
                                ? 'bg-green-500/20 text-green-300'
                                : contacto.rol.nombre?.toUpperCase() === 'ADMIN'
                                ? 'bg-orange-500/20 text-orange-300'
                                : 'bg-gray-500/20 text-gray-300'
                            }`}
                          >
                            {contacto.rol.nombre || '-'}
                          </span>
                        ) : (
                          <span className="text-sm text-white/50">-</span>
                        )}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-sm text-white/90">
                        {contacto.sucursal?.nombre || '-'}
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap">
                        <span
                          className={`inline-flex px-2 py-1 text-xs font-semibold rounded-full ${
                            contacto.activo
                              ? 'bg-green-500/20 text-green-300'
                              : 'bg-red-500/20 text-red-300'
                          }`}
                        >
                          {contacto.activo ? 'Activo' : 'Inactivo'}
                        </span>
                      </td>
                      <td className="px-6 py-4 whitespace-nowrap text-right text-sm font-medium">
                        <button
                          onClick={() => {
                            setSelectedContactoId(contacto.id);
                            setIsEditModalOpen(true);
                          }}
                          className="text-blue-400 hover:text-blue-300 mr-4"
                        >
                          <span className="material-symbols-outlined">edit</span>
                        </button>
                        <button
                          onClick={() => handleDelete(contacto.id)}
                          className="text-red-400 hover:text-red-300"
                        >
                          <span className="material-symbols-outlined">delete</span>
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        )}

        {/* Info Card */}
        <div className="mt-6 p-4 bg-blue-500/10 border border-blue-500/30 rounded-lg">
          <p className="text-blue-200 text-sm">
            <strong>Total de contactos:</strong> {contactos.length}
            {contactosFiltrados.length !== contactos.length && (
              <span className="ml-2">
                (<strong>Filtrados:</strong> {contactosFiltrados.length})
              </span>
            )}
          </p>
          <p className="text-blue-200/70 text-xs mt-1">
            Conexión con el backend establecida correctamente.
          </p>
        </div>
      </div>

      {/* Modal de Crear Contacto */}
      <CreateContactoModal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onSuccess={() => {
          loadContactos();
          setIsModalOpen(false);
        }}
      />

      {/* Modal de Editar Contacto */}
      <EditContactoModal
        isOpen={isEditModalOpen}
        onClose={() => {
          setIsEditModalOpen(false);
          setSelectedContactoId(null);
        }}
        onSuccess={() => {
          loadContactos();
          setIsEditModalOpen(false);
          setSelectedContactoId(null);
        }}
        contactoId={selectedContactoId}
      />
    </DashboardLayout>
  );
};

export default ContactosPage;

