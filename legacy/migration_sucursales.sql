-- ============================================
-- SCRIPT DE MIGRACIÓN PARA SISTEMA MULTI-SUCURSAL
-- ============================================

-- 1. Crear tabla sucursales
CREATE TABLE IF NOT EXISTS sucursales (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL UNIQUE,
    direccion VARCHAR(500) NOT NULL,
    telefono VARCHAR(50),
    email VARCHAR(255),
    activa BOOLEAN DEFAULT TRUE
);

-- 2. Insertar las 2 sucursales iniciales
INSERT INTO sucursales (nombre, direccion, activa) VALUES 
('Sucursal Principal', 'Dirección Principal', TRUE),
('Sucursal Secundaria', 'Dirección Secundaria', TRUE)
ON DUPLICATE KEY UPDATE nombre = nombre;

-- 3. Agregar columna sucursal_id a las tablas
ALTER TABLE contactos ADD COLUMN IF NOT EXISTS sucursal_id INT;
ALTER TABLE ventas ADD COLUMN IF NOT EXISTS sucursal_id INT;
ALTER TABLE compras ADD COLUMN IF NOT EXISTS sucursal_id INT;
ALTER TABLE lotes ADD COLUMN IF NOT EXISTS sucursal_id INT;
ALTER TABLE inventario_movimientos ADD COLUMN IF NOT EXISTS sucursal_id INT;

-- 4. Asignar sucursal por defecto a registros existentes
-- NOTA: Ajusta estos valores según tus datos reales
-- Asignar Sucursal 1 a usuarios existentes (ejemplo)
UPDATE contactos SET sucursal_id = 1 WHERE sucursal_id IS NULL AND username IS NOT NULL LIMIT 10;

-- Asignar Sucursal 1 a ventas existentes
UPDATE ventas SET sucursal_id = 1 WHERE sucursal_id IS NULL;

-- Asignar Sucursal 1 a compras existentes
UPDATE compras SET sucursal_id = 1 WHERE sucursal_id IS NULL;

-- Asignar Sucursal 1 a lotes existentes
UPDATE lotes SET sucursal_id = 1 WHERE sucursal_id IS NULL;

-- Asignar Sucursal 1 a movimientos de inventario existentes
UPDATE inventario_movimientos SET sucursal_id = 1 WHERE sucursal_id IS NULL;

-- 5. Hacer sucursal_id obligatorio para ventas, compras, lotes y movimientos
-- (Para contactos puede ser NULL si son clientes/proveedores)
ALTER TABLE ventas MODIFY COLUMN sucursal_id INT NOT NULL;
ALTER TABLE compras MODIFY COLUMN sucursal_id INT NOT NULL;
ALTER TABLE lotes MODIFY COLUMN sucursal_id INT NOT NULL;
ALTER TABLE inventario_movimientos MODIFY COLUMN sucursal_id INT NOT NULL;

-- 6. Agregar foreign keys
ALTER TABLE contactos 
ADD CONSTRAINT fk_contactos_sucursal 
FOREIGN KEY (sucursal_id) REFERENCES sucursales(id);

ALTER TABLE ventas 
ADD CONSTRAINT fk_ventas_sucursal 
FOREIGN KEY (sucursal_id) REFERENCES sucursales(id);

ALTER TABLE compras 
ADD CONSTRAINT fk_compras_sucursal 
FOREIGN KEY (sucursal_id) REFERENCES sucursales(id);

ALTER TABLE lotes 
ADD CONSTRAINT fk_lotes_sucursal 
FOREIGN KEY (sucursal_id) REFERENCES sucursales(id);

ALTER TABLE inventario_movimientos 
ADD CONSTRAINT fk_inventario_movimientos_sucursal 
FOREIGN KEY (sucursal_id) REFERENCES sucursales(id);

-- 7. Crear índices para mejorar el rendimiento
CREATE INDEX idx_ventas_sucursal ON ventas(sucursal_id);
CREATE INDEX idx_compras_sucursal ON compras(sucursal_id);
CREATE INDEX idx_lotes_sucursal ON lotes(sucursal_id);
CREATE INDEX idx_inventario_movimientos_sucursal ON inventario_movimientos(sucursal_id);
CREATE INDEX idx_contactos_sucursal ON contactos(sucursal_id);

