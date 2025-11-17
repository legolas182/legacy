# 🔐 Credenciales de Usuarios - Legacy Pharmacy

## Usuarios Creados

### 👨‍💼 Usuario Administrador

```
Username: admin
Password: admin123
Email: admin@legacy.com
Rol: ADMIN
```

### 👤 Usuario Empleado

```
Username: empleado
Password: empleado123
Email: juan.perez@legacy.com
Rol: EMPLEADO
```

---

## 🚀 Cómo Usar las Credenciales

### Paso 1: Iniciar el Backend

Desde la carpeta `legacy`, ejecuta:

```powershell
cd legacy
.\mvnw.cmd clean spring-boot:run
```

### Paso 2: Verificar la Base de Datos

Los usuarios se crearán automáticamente cuando el backend inicie. 

Si ya tienes la tabla `contactos`, los usuarios se insertarán automáticamente gracias al script `data.sql`.

### Paso 3: Iniciar Sesión en el Frontend

1. Abre tu navegador en `http://localhost:3000`
2. Ve a la página de Login
3. Usa cualquiera de las credenciales de arriba:
   - **Usuario**: `admin` 
   - **Contraseña**: `admin123`

### Paso 4: Verificar

Después de iniciar sesión, serás redirigido al Dashboard y deberías ver:
- Tu nombre de usuario en la esquina superior derecha
- Un indicador verde que confirma la conexión con el backend

---

## 📝 Notas Importantes

### Seguridad (Desarrollo)
⚠️ **IMPORTANTE**: Este sistema usa contraseñas en texto plano para desarrollo.
Para producción, debes implementar:
- BCrypt para encriptar contraseñas
- JWT (JSON Web Tokens) para tokens seguros
- HTTPS para todas las comunicaciones

### Estructura de la Base de Datos

Los usuarios están almacenados en la tabla `contactos` con estos campos adicionales:
- `username` - Nombre de usuario único
- `password` - Contraseña (texto plano por ahora)
- `activo` - Estado del usuario (true/false)
- `fecha_creacion` - Fecha de registro
- `ultimo_acceso` - Última vez que inició sesión
- `rol_id` - Referencia al rol del usuario

### Crear Más Usuarios

#### Opción 1: Desde el Frontend
Ve a `http://localhost:3000/register` y completa el formulario.

#### Opción 2: Desde MySQL
Conéctate a MySQL Workbench y ejecuta:

```sql
INSERT INTO contactos (
    nombre, 
    tipo_contacto, 
    email, 
    username, 
    password, 
    activo, 
    rol_id, 
    fecha_creacion
) VALUES (
    'Tu Nombre',
    'CLIENTE',
    'tu@email.com',
    'tu_usuario',
    'tu_password',
    true,
    1,
    NOW()
);
```

---

## 🔧 Endpoints de API Disponibles

### Autenticación

**Login**
```http
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

**Registro**
```http
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{
  "fullName": "Nombre Completo",
  "username": "nuevo_usuario",
  "email": "email@ejemplo.com",
  "password": "password123"
}
```

**Verificar Health**
```http
GET http://localhost:8080/api/health
```

---

## 🐛 Solución de Problemas

### Error: "Usuario no encontrado"
- Verifica que el backend se haya iniciado correctamente
- Revisa en MySQL Workbench si los usuarios existen: `SELECT * FROM contactos WHERE username = 'admin';`
- Si no existen, ejecuta manualmente el script `data.sql`

### Error: "Contraseña incorrecta"
- Verifica que estés usando las credenciales exactas (sensible a mayúsculas/minúsculas)
- Usuario: `admin`
- Contraseña: `admin123`

### Los usuarios no se crean automáticamente
Si el script `data.sql` no se ejecuta automáticamente:

1. Abre MySQL Workbench
2. Conecta a tu base de datos `legacy`
3. Ejecuta manualmente el script en `legacy/src/main/resources/data.sql`

---

## ✅ Checklist de Configuración

- [ ] Backend ejecutándose en puerto 8080
- [ ] Frontend ejecutándose en puerto 3000
- [ ] MySQL ejecutándose
- [ ] Base de datos `legacy` creada
- [ ] Usuarios insertados en tabla `contactos`
- [ ] CORS configurado correctamente

¡Todo listo para empezar a usar el sistema!

