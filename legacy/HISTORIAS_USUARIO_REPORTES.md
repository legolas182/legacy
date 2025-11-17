# Historias de Usuario - Reportes y Dashboard (Versión Simplificada)

## 1. Dashboard Principal - Resumen General

**Como** administrador del sistema  
**Quiero** ver un dashboard con información básica del negocio  
**Para** tener una visión rápida del estado de la farmacia

### Criterios de Aceptación:
- Mostrar total de ventas del día, mes y año actual
- Mostrar total de compras del día, mes y año actual
- Mostrar ganancia neta (ventas - compras) del mes actual
- Mostrar cantidad de productos con stock bajo
- Mostrar cantidad de alertas activas
- Mostrar top 5 productos más vendidos del mes
- Mostrar gráfico simple de ventas por mes (últimos 6 meses)

---

## 2. Reporte de Ventas

**Como** administrador o vendedor  
**Quiero** generar y descargar un reporte de ventas  
**Para** tener un registro de las ventas realizadas

### Criterios de Aceptación:
- Permitir seleccionar rango de fechas (fecha inicio y fecha fin)
- Mostrar lista de ventas: fecha, cliente, total
- Mostrar detalle de cada venta: productos, cantidades, precios
- Mostrar total general de ventas del período
- Permitir ver el reporte en pantalla antes de descargar
- Permitir descargar en formato PDF
- Permitir descargar en formato Excel

---

## 3. Reporte de Compras

**Como** administrador o comprador  
**Quiero** generar y descargar un reporte de compras  
**Para** tener un registro de las compras realizadas

### Criterios de Aceptación:
- Permitir seleccionar rango de fechas (fecha inicio y fecha fin)
- Mostrar lista de compras: fecha, proveedor, número de factura, total
- Mostrar detalle de cada compra: productos, cantidades, precios
- Mostrar total general de compras del período
- Permitir ver el reporte en pantalla antes de descargar
- Permitir descargar en formato PDF
- Permitir descargar en formato Excel

---

## 4. Reporte de Inventario

**Como** administrador o encargado de inventario  
**Quiero** generar y descargar un reporte del inventario actual  
**Para** conocer el estado del stock

### Criterios de Aceptación:
- Mostrar lista de productos: nombre, categoría, stock actual
- Mostrar lista de lotes: número de lote, producto, fecha de vencimiento, stock actual
- Incluir productos con stock bajo o sin stock
- Incluir lotes próximos a vencer (próximos 30 días)
- Mostrar valor total del inventario
- Permitir ver el reporte en pantalla antes de descargar
- Permitir descargar en formato PDF
- Permitir descargar en formato Excel

---

## 5. Reporte de Productos

**Como** administrador  
**Quiero** generar y descargar un reporte de productos  
**Para** tener el catálogo completo de productos

### Criterios de Aceptación:
- Mostrar lista de todos los productos con su información básica: nombre, concentración, forma farmacéutica, categoría, laboratorio, stock actual
- Permitir ver el reporte en pantalla antes de descargar
- Permitir descargar en formato PDF
- Permitir descargar en formato Excel

---

## 6. Reporte de Vencimientos

**Como** administrador o encargado de inventario  
**Quiero** generar y descargar un reporte de productos próximos a vencer  
**Para** gestionar la rotación de inventario

### Criterios de Aceptación:
- Permitir seleccionar días (próximos 30, 60 o 90 días)
- Mostrar lotes ordenados por fecha de vencimiento (más próximos primero)
- Mostrar: número de lote, producto, fecha de vencimiento, días restantes, stock actual
- Incluir lotes ya vencidos
- Mostrar resumen: cantidad de lotes próximos a vencer
- Permitir ver el reporte en pantalla antes de descargar
- Permitir descargar en formato PDF
- Permitir descargar en formato Excel

---

## 7. Reporte de Movimientos de Inventario

**Como** administrador o encargado de inventario  
**Quiero** generar y descargar un reporte de movimientos de inventario  
**Para** tener trazabilidad de los movimientos

### Criterios de Aceptación:
- Permitir seleccionar rango de fechas (fecha inicio y fecha fin)
- Mostrar lista de movimientos: fecha, producto, lote, tipo (entrada/salida), cantidad, descripción
- Mostrar resumen: total de entradas, total de salidas
- Permitir ver el reporte en pantalla antes de descargar
- Permitir descargar en formato PDF
- Permitir descargar en formato Excel

---

## 8. Visualización de Reportes en Pantalla

**Como** usuario del sistema  
**Quiero** poder ver los reportes en pantalla antes de descargarlos  
**Para** verificar que la información es correcta

### Criterios de Aceptación:
- Mostrar el reporte formateado en una página del navegador
- Permitir hacer scroll para ver todo el reporte
- Mostrar todos los datos en formato de tabla
- Permitir imprimir directamente desde el navegador
- Mostrar indicador de carga mientras se genera el reporte
- Permitir cerrar la vista y volver a generar otro reporte

---

## 9. Descarga de Reportes

**Como** usuario del sistema  
**Quiero** poder descargar los reportes en diferentes formatos  
**Para** guardarlos y usarlos cuando los necesite

### Criterios de Aceptación:
- Permitir descargar en formato PDF
- Permitir descargar en formato Excel (.xlsx)
- El PDF debe incluir tablas formateadas y datos básicos
- El Excel debe incluir los datos en formato de tabla
- Mostrar botón de descarga después de generar el reporte
- El archivo debe tener un nombre descriptivo con la fecha

---

## Notas Técnicas para Implementación:

### Tecnologías Sugeridas:
- **Backend (Spring Boot)**: 
  - Apache POI para generación de Excel
  - iText o OpenPDF para generación de PDF
  - Queries simples para obtener los datos
  
- **Frontend (Futuro)**:
  - Tablas simples para mostrar datos
  - Botones de descarga PDF y Excel
  - Selectores de fecha simples

### Consideraciones:
- Mantener los reportes simples y directos
- No incluir gráficos complejos en los reportes descargables
- Los reportes deben ser rápidos de generar
- Considerar permisos por rol para acceder a ciertos reportes

