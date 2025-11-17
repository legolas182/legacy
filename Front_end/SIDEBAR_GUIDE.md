# 🎯 Sidebar/Sidenav - Legacy Pharmacy

## ✨ Implementación Completa

He creado un **Sidebar** (menú lateral) profesional igual al diseño que proporcionaste.

## 📁 Componentes Creados

### 1. **Sidebar.jsx** 
`src/components/organisms/Sidebar/Sidebar.jsx`

**Características:**
- ✅ Logo y título "PharmaSys - Gestión de Farmacia"
- ✅ Menú de navegación con 7 opciones
- ✅ Iconos Material Symbols para cada item
- ✅ Estado activo/seleccionado (fondo azul `#2d4a5c`)
- ✅ Botón destacado "Nueva Venta" (azul primary)
- ✅ Footer con "Ayuda" y "Salir"
- ✅ Responsive (se oculta en móvil, se muestra con botón hamburguesa)
- ✅ Overlay para móvil

### 2. **DashboardLayout.jsx**
`src/components/templates/DashboardLayout/DashboardLayout.jsx`

**Layout completo con:**
- Sidebar fijo a la izquierda
- Header arriba
- Contenido principal a la derecha

## 📊 Estructura del Menú

```
PharmaSys
└── Gestión de Farmacia

Navegación:
  📊 Dashboard      ← Activo por defecto
  🛒 Ventas
  🛍️ Compras
  📦 Inventario
  💊 Productos
  📈 Reportes
  ⚙️ Configuración

[Nueva Venta] ← Botón azul destacado

Footer:
  ❓ Ayuda
  🚪 Salir
```

## 🎨 Diseño

### Colores
- **Fondo Sidebar**: `#1e2a3a` (gris azulado oscuro)
- **Item Activo**: `#2d4a5c` (azul oscuro)
- **Item Hover**: `white/5` (overlay blanco 5%)
- **Botón Nueva Venta**: `#51a0fb` (primary azul)

### Estilos
- Ancho fijo: `256px` (w-64)
- Altura completa: `min-h-screen`
- Bordes: `border-white/10`
- Transiciones suaves: `transition-all duration-200`

## 📱 Responsive

### Desktop (lg+)
- Sidebar siempre visible
- Ancho fijo 256px
- Layout con flex

### Mobile (<lg)
- Sidebar oculto por defecto
- Botón hamburguesa en esquina superior izquierda
- Se desliza desde la izquierda
- Overlay oscuro en el fondo
- Click fuera para cerrar

## 🔄 Navegación

El Sidebar usa **React Router** para navegar:

```javascript
const menuItems = [
  { icon: 'dashboard', label: 'Dashboard', path: '/dashboard' },
  { icon: 'shopping_cart', label: 'Ventas', path: '/ventas' },
  { icon: 'shopping_bag', label: 'Compras', path: '/compras' },
  { icon: 'inventory_2', label: 'Inventario', path: '/inventario' },
  { icon: 'medication', label: 'Productos', path: '/productos' },
  { icon: 'bar_chart', label: 'Reportes', path: '/reportes' },
  { icon: 'settings', label: 'Configuración', path: '/configuracion' },
];
```

### Estado Activo
El item activo se determina comparando la ruta actual con `location.pathname`:

```javascript
const isActive = (path) => location.pathname === path;
```

## 🚀 Uso

### En cualquier página del Dashboard:

```jsx
import DashboardLayout from '../../../components/templates/DashboardLayout/DashboardLayout';

const MiPagina = () => {
  return (
    <DashboardLayout>
      <div>
        {/* Tu contenido aquí */}
      </div>
    </DashboardLayout>
  );
};
```

El `DashboardLayout` automáticamente incluye:
- ✅ Sidebar
- ✅ Header
- ✅ Navegación funcional
- ✅ Logout

## 🔧 Personalización

### Cambiar items del menú

Edita el array `menuItems` en `Sidebar.jsx`:

```javascript
const menuItems = [
  { icon: 'dashboard', label: 'Dashboard', path: '/dashboard' },
  // Agrega más items aquí
];
```

### Cambiar colores

En `Sidebar.jsx`, busca las clases de Tailwind:
- `bg-[#1e2a3a]` - Fondo del sidebar
- `bg-[#2d4a5c]` - Item activo
- `bg-primary` - Botón Nueva Venta

## 📝 Iconos Disponibles

Material Symbols usados:
- `dashboard` - Dashboard
- `shopping_cart` - Ventas
- `shopping_bag` - Compras
- `inventory_2` - Inventario
- `medication` - Productos
- `bar_chart` - Reportes
- `settings` - Configuración
- `local_pharmacy` - Logo
- `help` - Ayuda
- `logout` - Salir
- `menu` - Hamburguesa (móvil)

Ver más en: [Material Symbols](https://fonts.google.com/icons)

## ✅ Funcionalidades

### Navegación
- Click en cualquier item → Navega a la ruta
- El item activo se resalta automáticamente

### Nueva Venta
- Click → Navega a `/ventas/nueva`
- Botón destacado para acción principal

### Ayuda
- Click → Navega a `/ayuda`

### Salir
- Click → Cierra sesión
- Elimina token de localStorage
- Redirige a `/login`

### Responsive
- Desktop: Sidebar siempre visible
- Móvil: Botón hamburguesa para abrir/cerrar
- Overlay oscuro al abrir en móvil
- Click fuera del sidebar → Cierra el menú

## 🎯 Estructura Final

```
Dashboard Page
├── DashboardLayout
│   ├── Sidebar (izquierda, fijo)
│   │   ├── Header (Logo + Título)
│   │   ├── Nav Menu (7 items)
│   │   ├── Nueva Venta (botón)
│   │   └── Footer (Ayuda + Salir)
│   ├── Header (arriba)
│   └── Main Content (derecha)
```

## 💡 Próximos Pasos

1. ✅ Sidebar implementado
2. ✅ Layout completo
3. ⏳ Crear páginas para cada ruta (Ventas, Compras, etc.)
4. ⏳ Implementar funcionalidad "Nueva Venta"
5. ⏳ Agregar más interactividad

---

**El Sidebar está listo y funcional** 🎉

