import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../../../contexts/AuthContext/AuthContext';
import AuthHeader from '../../../components/molecules/AuthHeader/AuthHeader';
import FormField from '../../../components/molecules/FormField/FormField';
import Button from '../../../components/atoms/Button/Button';
import Link from '../../../components/molecules/Link/Link';
import './LoginPage.css';

const LoginPage = () => {
  const navigate = useNavigate();
  const { login } = useAuth();
  const [formData, setFormData] = useState({
    username: '',
    password: '',
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
    setError('');
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    const result = await login(formData);

    if (result.success) {
      navigate('/dashboard');
    } else {
      setError(result.error);
    }

    setLoading(false);
  };

  return (
    <div className="auth-page">
      <div className="auth-container">
        <AuthHeader
          title="Bienvenido de nuevo"
          subtitle="Ingrese sus credenciales para acceder al panel de control."
        />

        <form className="auth-form" onSubmit={handleSubmit}>
          <FormField
            label="Nombre de Usuario o Correo Electrónico"
            type="text"
            name="username"
            placeholder="ej: juan.perez@example.com"
            value={formData.username}
            onChange={handleChange}
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
                <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2" />
                <circle cx="12" cy="7" r="4" />
              </svg>
            }
          />

          <FormField
            label="Contraseña"
            type="password"
            name="password"
            placeholder="Ingrese su contraseña"
            value={formData.password}
            onChange={handleChange}
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

          <div className="auth-form-forgot">
            <Link to="/forgot-password">¿Olvidaste tu contraseña?</Link>
          </div>

          {error && <div className="auth-form-error">{error}</div>}

          <Button type="submit" fullWidth disabled={loading}>
            {loading ? 'Iniciando sesión...' : 'Iniciar Sesión'}
          </Button>

          <div className="auth-form-footer">
            <span>¿No tienes una cuenta? </span>
            <Link to="/register">Regístrate</Link>
          </div>
        </form>
      </div>
    </div>
  );
};

export default LoginPage;

