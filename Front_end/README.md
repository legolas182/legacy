# Legacy Pharmacy - Frontend

Sistema de gestión para farmacia desarrollado con React siguiendo la arquitectura de Atomic Design.

## 🚀 Tecnologías

- React 18.2.0
- React Router DOM 6.20.0
- Axios 1.6.2
- CSS Modules

## 📁 Estructura del Proyecto

```
src/
├── components/              # Componentes reutilizables
│   ├── atoms/              # Componentes básicos (Button, Input, Logo)
│   ├── molecules/          # Componentes compuestos (FormField, AuthHeader, Link)
│   └── organisms/          # Componentes complejos (PrivateRoute)
├── contexts/               # Contextos de React
│   └── AuthContext/        # Gestión de autenticación
├── features/               # Módulos por funcionalidad
│   ├── Auth/              # Autenticación (Login, Registro)
│   └── Dashboard/         # Panel principal
├── services/               # Servicios de API
│   ├── api/               # Configuración de Axios
│   └── authService/       # Servicio de autenticación
├── App.jsx                # Componente principal
└── index.js               # Punto de entrada
```

## 🔧 Instalación

1. Navega a la carpeta del frontend:
```bash
cd Front_end
```

2. Instala las dependencias:
```bash
npm install
```

## 🏃‍♂️ Ejecutar el Proyecto

### Modo desarrollo
```bash
npm start
```
La aplicación se abrirá en [http://localhost:3000](http://localhost:3000)

### Build para producción
```bash
npm build
```

### Ejecutar tests
```bash
npm test
```

## 🔐 Autenticación

El frontend incluye un sistema completo de autenticación con:
- Login de usuarios
- Registro de nuevos usuarios
- Protección de rutas privadas
- Gestión de tokens JWT

## 🌐 Configuración del Backend

El frontend está configurado para conectarse al backend en `http://localhost:8080/api`. 

Para cambiar esta URL, edita el archivo `src/services/api/apiConfig.js`:

```javascript
const API_BASE_URL = 'http://localhost:8080/api'; // Cambiar según necesidad
```

## 🎨 Características de Diseño

- **Dark Mode por defecto**: Tema oscuro moderno
- **Diseño responsivo**: Adaptable a diferentes tamaños de pantalla
- **Glassmorphism**: Efectos de vidrio esmerilado
- **Animaciones suaves**: Transiciones y efectos hover

## 📄 Páginas Disponibles

- `/login` - Inicio de sesión
- `/register` - Registro de nuevos usuarios
- `/dashboard` - Panel principal (requiere autenticación)

## 🛠️ Scripts Disponibles

- `npm start` - Inicia el servidor de desarrollo
- `npm build` - Crea build de producción
- `npm test` - Ejecuta los tests
- `npm eject` - Expone configuración de webpack (irreversible)

## 📝 Notas

- El token de autenticación se almacena en localStorage
- Las peticiones HTTP incluyen automáticamente el token JWT
- Si el token expira, el usuario es redirigido al login automáticamente

## 🤝 Contribuir

Este proyecto sigue la estructura de Atomic Design para mantener una organización clara y escalable del código.

