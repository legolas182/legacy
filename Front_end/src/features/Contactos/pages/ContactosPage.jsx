import React, { useState, useEffect } from 'react';
import DashboardLayout from '../../../components/templates/DashboardLayout/DashboardLayout';
import { contactosService } from '../../../services/contactosService/contactosService';
import Spinner from '../../../components/atoms/Spinner/Spinner';
import CreateContactoModal from '../components/CreateContactoModal/CreateContactoModal';

const ContactosPage = () => {
  const [contactos, setContactos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false);

  useEffect(() => {
    loadContactos();
  }, []);

  const loadContactos = async () => {
    try {
      setLoading(true);
      setError(null);
      const response = await contactosService.getAll();
      setContactos(response.data || []);
    } catch (err) {
      console.error('Error al cargar contactos:', err);
      setError(err.response?.data?.message || 'Error al cargar contactos');
    } finally {
      setLoading(false);
    }
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
      <div className="w-full max-w-7xl mx-auto">
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

        {/* Contactos Table */}
        {contactos.length > 0 && (
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
                      Activo
                    </th>
                    <th className="px-6 py-3 text-right text-xs font-medium text-white/70 uppercase tracking-wider">
                      Acciones
                    </th>
                  </tr>
                </thead>
                <tbody className="divide-y divide-white/10">
                  {contactos.map((contacto) => (
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
                          onClick={() => alert(`Editar contacto ${contacto.id} - Funcionalidad próximamente`)}
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
    </DashboardLayout>
  );
};

export default ContactosPage;

