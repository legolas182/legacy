import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../../contexts/AuthContext/AuthContext';
import AuthHeader from '../../../components/molecules/AuthHeader/AuthHeader';
import FormField from '../../../components/molecules/FormField/FormField';
import Button from '../../../components/atoms/Button/Button';
import Link from '../../../components/molecules/Link/Link';
import './LoginPage.css';

const RegisterPage = () => {
  const navigate = useNavigate();
  const { register } = useAuth();
  const [formData, setFormData] = useState({
    fullName: '',
    username: '',
    email: '',
    password: '',
    confirmPassword: '',
  });
  const [errors, setErrors] = useState({});
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
    // Limpiar error del campo cuando el usuario escribe
    if (errors[e.target.name]) {
      setErrors({
        ...errors,
        [e.target.name]: '',
      });
    }
  };

  const validateForm = () => {
    const newErrors = {};

    if (!formData.fullName.trim()) {
      newErrors.fullName = 'El nombre completo es requerido';
    }

    if (!formData.username.trim()) {
      newErrors.username = 'El nombre de usuario es requerido';
    }

    if (!formData.email.trim()) {
      newErrors.email = 'El correo electrónico es requerido';
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = 'El correo electrónico no es válido';
    }

    if (!formData.password) {
      newErrors.password = 'La contraseña es requerida';
    } else if (formData.password.length < 6) {
      newErrors.password = 'La contraseña debe tener al menos 6 caracteres';
    }

    if (formData.password !== formData.confirmPassword) {
      newErrors.confirmPassword = 'Las contraseñas no coinciden';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!validateForm()) {
      return;
    }

    setLoading(true);

    const { confirmPassword, ...userData } = formData;
    const result = await register(userData);

    if (result.success) {
      navigate('/dashboard');
    } else {
      setErrors({ general: result.error });
    }

    setLoading(false);
  };

  return (
    <div className="auth-page">
      <div className="auth-container">
        <AuthHeader title="Crear una cuenta nueva" />

        <form className="auth-form" onSubmit={handleSubmit}>
          <FormField
            label="Nombre Completo"
            type="text"
            name="fullName"
            placeholder="Ingresa tu nombre completo"
            value={formData.fullName}
            onChange={handleChange}
            error={errors.fullName}
            required
          />

          <FormField
            label="Nombre de Usuario"
            type="text"
            name="username"
            placeholder="Elige un nombre de usuario"
            value={formData.username}
            onChange={handleChange}
            error={errors.username}
            required
          />

          <FormField
            label="Correo Electrónico"
            type="email"
            name="email"
            placeholder="tu@correo.com"
            value={formData.email}
            onChange={handleChange}
            error={errors.email}
            required
          />

          <FormField
            label="Contraseña"
            type="password"
            name="password"
            placeholder="Ingresa tu contraseña"
            value={formData.password}
            onChange={handleChange}
            error={errors.password}
            required
            icon={
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="20"
                height="20"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                strokeWidth="2"
                strokeLinecap="round"
                strokeLinejoin="round"
              >
                <rect x="3" y="11" width="18" height="11" rx="2" ry="2" />
                <path d="M7 11V7a5 5 0 0 1 10 0v4" />
              </svg>
            }
          />

          <FormField
            label="Confirmar Contraseña"
            type="password"
            name="confirmPassword"
            placeholder="Confirma tu contraseña"
            value={formData.confirmPassword}
            onChange={handleChange}
            error={errors.confirmPassword}
            required
            icon={
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="20"
                height="20"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                strokeWidth="2"
                strokeLinecap="round"
                strokeLinejoin="round"
              >
                <rect x="3" y="11" width="18" height="11" rx="2" ry="2" />
                <path d="M7 11V7a5 5 0 0 1 10 0v4" />
              </svg>
            }
          />

          {errors.general && (
            <div className="auth-form-error">{errors.general}</div>
          )}

          <Button type="submit" fullWidth disabled={loading}>
            {loading ? 'Registrando...' : 'Registrarse'}
          </Button>

          <div className="auth-form-footer">
            <span>¿Ya tienes una cuenta? </span>
            <Link to="/login">Iniciar Sesión</Link>
          </div>
        </form>
      </div>
    </div>
  );
};

export default RegisterPage;

