# Prompt para Generación de Mockups de Reportes - Sistema de Farmacia

## Contexto del Sistema
Estás diseñando mockups para un sistema de gestión de farmacia (legacy) que incluye reportes y un dashboard. El sistema maneja productos farmacéuticos, ventas, compras, inventario, lotes con fechas de vencimiento, y contactos (clientes y proveedores).

## Estilo de Diseño General
- **Tema**: Modo oscuro (dark mode) - todos los componentes deben usar esquema de colores oscuro
- **Estilo**: Moderno, limpio y profesional
- **Tipografía**: Legible, jerarquía clara
- **Componentes**: Tablas, tarjetas (cards), gráficos simples, botones de acción
- **Colores**: Paleta oscura con acentos para elementos importantes (botones, alertas, valores destacados)

---

## Mockup 1: Dashboard Principal - Resumen General

### Descripción
Página principal que muestra un resumen ejecutivo del estado de la farmacia.

### Elementos a Incluir:

**Header/Navegación:**
- Logo o nombre del sistema "Sistema de Farmacia"
- Menú de navegación con acceso a reportes
- Indicador de usuario/rol

**Tarjetas de Métricas (Cards) - Fila Superior:**
1. **Total Ventas del Día**
   - Icono de ventas
   - Valor monetario grande y destacado
   - Etiqueta "Ventas Hoy"
   - Color de acento (verde/azul)

2. **Total Ventas del Mes**
   - Icono de calendario mensual
   - Valor monetario grande
   - Etiqueta "Ventas del Mes"
   - Color de acento

3. **Total Ventas del Año**
   - Icono de año
   - Valor monetario grande
   - Etiqueta "Ventas del Año"
   - Color de acento

4. **Total Compras del Día**
   - Icono de compras
   - Valor monetario
   - Etiqueta "Compras Hoy"
   - Color diferente (naranja/amarillo)

5. **Total Compras del Mes**
   - Icono de compras mensuales
   - Valor monetario
   - Etiqueta "Compras del Mes"

6. **Total Compras del Año**
   - Icono de compras anuales
   - Valor monetario
   - Etiqueta "Compras del Año"

**Tarjetas de Métricas - Fila Media:**
7. **Ganancia Neta del Mes**
   - Icono de ganancia
   - Valor monetario muy destacado (más grande)
   - Etiqueta "Ganancia Neta (Mes Actual)"
   - Color verde si es positiva, rojo si es negativa

8. **Productos con Stock Bajo**
   - Icono de alerta/advertencia
   - Número destacado
   - Etiqueta "Productos Stock Bajo"
   - Color de advertencia (amarillo/naranja)

9. **Alertas Activas**
   - Icono de notificación
   - Número destacado
   - Etiqueta "Alertas Activas"
   - Color de alerta (rojo/naranja)

**Gráfico de Ventas:**
- Gráfico de línea o barras simple
- Título: "Ventas por Mes (Últimos 6 Meses)"
- Eje X: Meses (últimos 6 meses)
- Eje Y: Monto en moneda
- Estilo oscuro con línea/barra destacada

**Tabla Top 5 Productos Más Vendidos:**
- Título: "Top 5 Productos Más Vendidos del Mes"
- Columnas: Posición, Nombre del Producto, Cantidad Vendida, Total en Ventas
- Estilo de tabla oscura con filas alternadas
- Máximo 5 filas

### Notas de Diseño:
- Todas las tarjetas deben tener bordes sutiles y sombras suaves
- Los valores monetarios deben tener formato de moneda (ej: $1,234.56)
- El layout debe ser responsive (grid de 3 columnas en desktop, 2 en tablet, 1 en móvil)
- Espaciado generoso entre elementos

---

## Mockup 2: Reporte de Ventas

### Descripción
Página para generar y visualizar reportes de ventas con filtros de fecha.

### Elementos a Incluir:

**Panel de Filtros (Parte Superior):**
- Título: "Reporte de Ventas"
- Campo "Fecha Inicio" (date picker)
- Campo "Fecha Fin" (date picker)
- Botón "Generar Reporte" (prominente, color primario)
- Botón "Limpiar Filtros" (secundario)

**Área de Visualización del Reporte:**
- Título del reporte: "Reporte de Ventas - [Fecha Inicio] a [Fecha Fin]"
- Fecha de generación del reporte

**Resumen General:**
- Tarjeta con "Total General de Ventas: $X,XXX.XX"
- Número de ventas en el período

**Tabla Principal de Ventas:**
- Columnas: Fecha, Cliente, Total
- Filas expandibles o sección de detalle
- Estilo de tabla oscura con hover en filas
- Paginación si hay muchas ventas

**Detalle de Venta (al expandir o en sección separada):**
- Título: "Detalle de Venta #XXX"
- Información de la venta: Fecha, Cliente, Método de Pago, Total
- Subtabla de productos:
  - Columnas: Producto, Cantidad, Precio Unitario, Subtotal
- Total de la venta destacado

**Botones de Acción (Parte Inferior):**
- Botón "Ver en Pantalla" (si no está visible)
- Botón "Descargar PDF" (icono de PDF)
- Botón "Descargar Excel" (icono de Excel)
- Botón "Imprimir" (icono de impresora)

### Notas de Diseño:
- El reporte debe ser legible y bien formateado
- Las tablas deben tener encabezados claros
- Los totales deben estar destacados visualmente
- Indicador de carga mientras se genera el reporte

---

## Mockup 3: Reporte de Compras

### Descripción
Similar al reporte de ventas pero enfocado en compras.

### Elementos a Incluir:

**Panel de Filtros:**
- Título: "Reporte de Compras"
- Campo "Fecha Inicio"
- Campo "Fecha Fin"
- Botón "Generar Reporte"
- Botón "Limpiar Filtros"

**Área de Visualización:**
- Título: "Reporte de Compras - [Fecha Inicio] a [Fecha Fin]"
- Fecha de generación

**Resumen General:**
- Tarjeta con "Total General de Compras: $X,XXX.XX"
- Número de compras en el período

**Tabla Principal de Compras:**
- Columnas: Fecha, Proveedor, Número de Factura, Total
- Filas expandibles o sección de detalle
- Estilo de tabla oscura

**Detalle de Compra:**
- Título: "Detalle de Compra #XXX"
- Información: Fecha, Proveedor, Número de Factura, Total
- Subtabla de productos:
  - Columnas: Producto, Cantidad, Precio Unitario, Subtotal

**Botones de Acción:**
- Botón "Descargar PDF"
- Botón "Descargar Excel"
- Botón "Imprimir"

---

## Mockup 4: Reporte de Inventario

### Descripción
Reporte del estado actual del inventario con productos y lotes.

### Elementos a Incluir:

**Panel de Filtros:**
- Título: "Reporte de Inventario"
- Checkbox "Incluir productos con stock bajo"
- Checkbox "Incluir lotes próximos a vencer (30 días)"
- Botón "Generar Reporte"

**Área de Visualización:**
- Título: "Reporte de Inventario - [Fecha]"
- Fecha de generación

**Resumen:**
- Tarjeta con "Valor Total del Inventario: $X,XXX.XX"
- Contador de productos
- Contador de lotes

**Sección 1: Productos**
- Subtítulo: "Productos en Inventario"
- Tabla con columnas: Nombre, Categoría, Stock Actual, Precio Unitario, Valor Total
- Resaltar en color diferente los productos con stock bajo

**Sección 2: Lotes**
- Subtítulo: "Lotes en Inventario"
- Tabla con columnas: Número de Lote, Producto, Fecha de Vencimiento, Stock Actual
- Resaltar en color diferente los lotes próximos a vencer

**Sección 3: Productos con Stock Bajo (si aplica)**
- Subtítulo: "Productos con Stock Bajo"
- Tabla similar a productos pero solo los de stock bajo

**Sección 4: Lotes Próximos a Vencer (si aplica)**
- Subtítulo: "Lotes Próximos a Vencer (Próximos 30 días)"
- Tabla con lotes ordenados por fecha de vencimiento

**Botones de Acción:**
- Botón "Descargar PDF"
- Botón "Descargar Excel"
- Botón "Imprimir"

---

## Mockup 5: Reporte de Productos

### Descripción
Catálogo completo de productos.

### Elementos a Incluir:

**Panel de Filtros (Opcional):**
- Título: "Reporte de Productos"
- Botón "Generar Reporte Completo"

**Área de Visualización:**
- Título: "Catálogo de Productos - [Fecha]"
- Fecha de generación

**Tabla de Productos:**
- Columnas: Nombre, Concentración, Forma Farmacéutica, Categoría, Laboratorio, Stock Actual
- Todas las filas de productos disponibles
- Paginación si hay muchos productos
- Búsqueda/filtro en tiempo real (opcional para el mockup)

**Resumen:**
- Total de productos en el catálogo

**Botones de Acción:**
- Botón "Descargar PDF"
- Botón "Descargar Excel"
- Botón "Imprimir"

---

## Mockup 6: Reporte de Vencimientos

### Descripción
Reporte de lotes próximos a vencer o vencidos.

### Elementos a Incluir:

**Panel de Filtros:**
- Título: "Reporte de Vencimientos"
- Radio buttons o dropdown para seleccionar días:
  - "Próximos 30 días"
  - "Próximos 60 días"
  - "Próximos 90 días"
  - "Todos los vencidos"
- Botón "Generar Reporte"

**Área de Visualización:**
- Título: "Reporte de Vencimientos - [Período seleccionado]"
- Fecha de generación

**Resumen:**
- Tarjeta con "Cantidad de Lotes Próximos a Vencer: XX"
- Tarjeta con "Cantidad de Lotes Vencidos: XX"

**Tabla de Lotes:**
- Columnas: Número de Lote, Producto, Fecha de Vencimiento, Días Restantes, Stock Actual
- Ordenados por fecha de vencimiento (más próximos primero)
- Resaltar en rojo los lotes vencidos
- Resaltar en amarillo/naranja los lotes próximos a vencer (menos de 30 días)
- Columna "Días Restantes" con valores negativos para vencidos

**Botones de Acción:**
- Botón "Descargar PDF"
- Botón "Descargar Excel"
- Botón "Imprimir"

---

## Mockup 7: Reporte de Movimientos de Inventario

### Descripción
Trazabilidad de movimientos de inventario (entradas y salidas).

### Elementos a Incluir:

**Panel de Filtros:**
- Título: "Reporte de Movimientos de Inventario"
- Campo "Fecha Inicio"
- Campo "Fecha Fin"
- Botón "Generar Reporte"
- Botón "Limpiar Filtros"

**Área de Visualización:**
- Título: "Reporte de Movimientos - [Fecha Inicio] a [Fecha Fin]"
- Fecha de generación

**Resumen:**
- Tarjeta "Total de Entradas: XXX unidades"
- Tarjeta "Total de Salidas: XXX unidades"
- Tarjeta "Balance Neto: XXX unidades" (entradas - salidas)

**Tabla de Movimientos:**
- Columnas: Fecha, Producto, Lote, Tipo (Entrada/Salida), Cantidad, Descripción
- Iconos o badges de color para distinguir entradas (verde) y salidas (rojo)
- Ordenados por fecha (más recientes primero)
- Paginación si hay muchos movimientos

**Botones de Acción:**
- Botón "Descargar PDF"
- Botón "Descargar Excel"
- Botón "Imprimir"

---

## Mockup 8: Vista de Reporte en Pantalla (Vista Previa)

### Descripción
Vista previa del reporte antes de descargar.

### Elementos a Incluir:

**Header del Reporte:**
- Logo o nombre del sistema
- Título del reporte
- Fecha de generación
- Rango de fechas (si aplica)

**Contenido del Reporte:**
- Tablas formateadas con todos los datos
- Resúmenes y totales destacados
- Formato limpio y profesional
- Scroll vertical para reportes largos

**Barra de Acciones Flotante o Fija:**
- Botón "Descargar PDF"
- Botón "Descargar Excel"
- Botón "Imprimir"
- Botón "Cerrar" o "Volver"

**Footer del Reporte:**
- Información del sistema
- Página X de Y (si tiene paginación)

---

## Especificaciones Técnicas de Diseño

### Paleta de Colores (Modo Oscuro):
- **Fondo principal**: #1a1a1a o #121212
- **Fondo secundario**: #2d2d2d o #1e1e1e
- **Fondo de tarjetas**: #252525
- **Texto principal**: #ffffff o #e0e0e0
- **Texto secundario**: #b0b0b0
- **Bordes**: #404040
- **Acento primario**: #4a9eff (azul)
- **Acento éxito**: #4caf50 (verde)
- **Acento advertencia**: #ff9800 (naranja)
- **Acento peligro**: #f44336 (rojo)
- **Hover**: #353535

### Componentes Reutilizables:
- Tarjetas (Cards) con sombra sutil
- Tablas con filas alternadas y hover
- Botones con estados (normal, hover, active, disabled)
- Inputs de fecha (date pickers) con estilo oscuro
- Badges/etiquetas para estados (entrada/salida, vencido, etc.)
- Iconos para acciones y métricas

### Tipografía:
- Títulos principales: 24-28px, bold
- Subtítulos: 18-20px, semibold
- Texto normal: 14-16px, regular
- Texto pequeño: 12px, regular
- Valores destacados: 32-40px, bold

### Espaciado:
- Padding de tarjetas: 20-24px
- Espaciado entre elementos: 16-24px
- Padding de tablas: 12-16px
- Márgenes de secciones: 32-40px

---

## Instrucciones para el Diseñador

1. **Crear todos los mockups en modo oscuro** siguiendo la paleta de colores especificada
2. **Usar datos de ejemplo realistas** pero genéricos (nombres de productos farmacéuticos, montos en formato de moneda, fechas recientes)
3. **Incluir todos los elementos mencionados** en cada mockup
4. **Mantener consistencia visual** entre todos los mockups
5. **Mostrar estados interactivos** cuando sea relevante (hover, active, etc.)
6. **Incluir iconos apropiados** para cada tipo de acción y métrica
7. **Asegurar legibilidad** de todos los textos y valores
8. **Mostrar el layout responsive** o al menos indicar cómo se vería en diferentes tamaños
9. **Incluir indicadores de carga** donde sea necesario
10. **Mostrar mensajes de estado** (sin datos, error, etc.) cuando aplique

---

## Notas Adicionales

- Los mockups deben ser lo suficientemente detallados para que un desarrollador pueda implementarlos
- Considerar la experiencia de usuario: flujos claros, acciones obvias, feedback visual
- Los reportes deben verse profesionales ya que pueden ser impresos o compartidos
- Mantener el diseño simple y funcional, evitando elementos decorativos innecesarios
- Todos los botones de descarga deben tener iconos claros y etiquetas descriptivas

