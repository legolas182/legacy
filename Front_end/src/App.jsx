import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './contexts/AuthContext/AuthContext';
import LoginPage from './features/Auth/pages/LoginPage';
import DashboardPage from './features/Dashboard/pages/DashboardPage';
import ContactosPage from './features/Contactos/pages/ContactosPage';
import VentasPage from './features/Ventas/pages/VentasPage';
import PrivateRoute from './components/organisms/PrivateRoute/PrivateRoute';

function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/login" element={<LoginPage />} />
          <Route
            path="/dashboard"
            element={
              <PrivateRoute>
                <DashboardPage />
              </PrivateRoute>
            }
          />
          <Route
            path="/contactos"
            element={
              <PrivateRoute>
                <ContactosPage />
              </PrivateRoute>
            }
          />
          <Route
            path="/ventas"
            element={
              <PrivateRoute>
                <VentasPage />
              </PrivateRoute>
            }
          />
          <Route path="/" element={<Navigate to="/login" replace />} />
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;

