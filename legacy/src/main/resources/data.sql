-- Script para insertar datos de prueba en la base de datos Legacy Pharmacy

-- Insertar roles por defecto si no existen
INSERT IGNORE INTO roles (id, nombre) VALUES 
(1, 'ADMIN'),
(2, 'EMPLEADO'),
(3, 'CLIENTE'),
(4, 'PROVEEDOR');

-- Insertar usuario de prueba
-- Credenciales: admin / admin123
INSERT INTO contactos (
    nombre, 
    email, 
    username, 
    password, 
    activo, 
    rol_id, 
    fecha_creacion,
    telefono
) VALUES (
    'Administrador del Sistema',
    'admin@legacy.com',
    'admin',
    'admin123',
    true,
    1,
    NOW(),
    '555-0001'
) ON DUPLICATE KEY UPDATE 
    nombre = VALUES(nombre),
    email = VALUES(email);

-- Insertar usuario empleado de prueba
-- Credenciales: empleado / empleado123
INSERT INTO contactos (
    nombre, 
    email, 
    username, 
    password, 
    activo, 
    rol_id, 
    fecha_creacion,
    telefono
) VALUES (
    'Juan Pérez',
    'juan.perez@legacy.com',
    'empleado',
    'empleado123',
    true,
    2,
    NOW(),
    '555-0002'
) ON DUPLICATE KEY UPDATE 
    nombre = VALUES(nombre),
    email = VALUES(email);

