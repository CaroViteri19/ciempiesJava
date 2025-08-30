# ANÁLISIS DE NAVEGACIÓN Y CONEXIONES - CIEMPIÉS SGI

## 📍 RUTAS DE NAVEGACIÓN

### **Controlador Principal: ViewController**
**Ubicación:** `src/main/java/com/ciempies/sgi/controller/ViewController.java`

### **Rutas Disponibles:**

| Ruta | Método | Controlador | Vista | Descripción |
|------|--------|-------------|-------|-------------|
| `/` | GET | ViewController.index() | `index.html` | Página principal |
| `/dashboard` | GET | ViewController.dashboard() | `dashboard.html` | Panel de control |
| `/login` | GET | ViewController.login() | `login.html` | Página de login |
| `/registro` | GET | ViewController.registro() | `registro.html` | Página de registro |
| `/usuarios` | GET | ViewController.usuarios() | `usuarios.html` | Gestión de usuarios |
| `/estudiantes` | GET | ViewController.estudiantes() | `estudiantes.html` | Gestión de estudiantes |
| `/rutas` | GET | ViewController.rutas() | `rutas.html` | Gestión de rutas |
| `/asistencias` | GET | ViewController.asistencias() | `asistencias.html` | Gestión de asistencias |
| `/inscripciones` | GET | ViewController.inscripciones() | `inscripciones.html` | Gestión de inscripciones |
| `/zonas` | GET | ViewController.zonas() | `zonas.html` | Gestión de zonas |

### **API REST Endpoints:**
**Controlador:** `AuthController.java`

| Endpoint | Método | Descripción |
|----------|--------|-------------|
| `/api/auth/login` | POST | Autenticación de usuarios |
| `/api/auth/register` | POST | Registro de usuarios |
| `/api/auth/usuarios` | GET | Listar usuarios |
| `/api/auth/usuarios/{id}` | GET | Obtener usuario por ID |
| `/api/auth/usuarios/{id}` | PUT | Actualizar usuario |
| `/api/auth/usuarios/{id}` | DELETE | Eliminar usuario |

## 🔗 CONEXIONES ENTRE TABLAS

### **Diagrama de Relaciones:**

```
┌─────────────┐    ┌─────────────┐    ┌─────────────┐
│   USUARIO   │    │    ZONA     │    │    RUTA     │
│             │    │             │    │             │
│ Id_Usuario  │◄───┤ Id_Zona     │    │ Id_Ruta     │
│ Id_Zona     │    │ Id_Colegio  │    │ Id_Usuario  │
│ Id_ruta     │    │ Cod_Zona    │    │ Id_Estudiante│
└─────────────┘    └─────────────┘    └─────────────┘
       │                   │                   │
       │                   │                   │
       ▼                   │                   ▼
┌─────────────┐            │            ┌─────────────┐
│ ASISTENCIAS │            │            │ ESTUDIANTE  │
│             │            │            │             │
│ Id_asistencia│           │            │ Id_Estudiante│
│ Id_Usuario  │            │            │ Id_Ruta     │
│ Cod_Ruta    │            │            │ Id_Inscripcion│
└─────────────┘            │            └─────────────┘
                           │                   │
                           │                   │
                           │                   ▼
                           │            ┌─────────────┐
                           │            │ INSCRIPCION│
                           │            │             │
                           └────────────┤ Id_Inscripcion│
                                        │ Id_ruta     │
                                        │ Cod_Ruta    │
                                        └─────────────┘
```

### **Relaciones Específicas:**

#### **1. Usuario ↔ Zona**
- **Tabla:** `usuario`
- **Campo:** `Id_Zona` → `zona.Id_Zona`
- **Tipo:** Foreign Key
- **Relación:** Muchos a Uno (Muchos usuarios pueden estar en una zona)

#### **2. Usuario ↔ Ruta**
- **Tabla:** `usuario`
- **Campo:** `Id_ruta` → `ruta.Id_Ruta`
- **Tipo:** Foreign Key
- **Relación:** Muchos a Uno (Muchos usuarios pueden estar en una ruta)

#### **3. Asistencia ↔ Usuario**
- **Tabla:** `asistencias`
- **Campo:** `Id_Usuario` → `usuario.Id_Usuario`
- **Tipo:** Foreign Key
- **Relación:** Muchos a Uno (Muchas asistencias pueden ser registradas por un usuario)

#### **4. Estudiante ↔ Inscripción**
- **Tabla:** `estudiante`
- **Campo:** `Id_Inscripcion` → `inscripcion.Id_Inscripcion`
- **Tipo:** Foreign Key
- **Relación:** Uno a Uno (Un estudiante tiene una inscripción)

#### **5. Estudiante ↔ Ruta**
- **Tabla:** `estudiante`
- **Campo:** `Id_Ruta` → `ruta.Id_Ruta`
- **Tipo:** Foreign Key
- **Relación:** Muchos a Uno (Muchos estudiantes pueden estar en una ruta)

#### **6. Inscripción ↔ Ruta**
- **Tabla:** `inscripcion`
- **Campo:** `Id_ruta` → `ruta.Id_Ruta`
- **Tipo:** Foreign Key
- **Relación:** Muchos a Uno (Muchas inscripciones pueden estar en una ruta)

#### **7. Ruta ↔ Usuario**
- **Tabla:** `ruta`
- **Campo:** `Id_Usuario` → `usuario.Id_Usuario`
- **Tipo:** Foreign Key
- **Relación:** Muchos a Uno (Muchas rutas pueden ser manejadas por un usuario)

## 🎨 VISTAS Y TEMPLATES

### **Estructura de Templates:**
```
src/main/resources/templates/
├── index.html              # Página principal
├── login.html              # Login
├── registro.html           # Registro
├── dashboard.html          # Dashboard principal
├── usuarios.html           # Gestión usuarios
├── estudiantes.html        # Gestión estudiantes
├── rutas.html             # Gestión rutas
├── asistencias.html       # Gestión asistencias
├── inscripciones.html     # Gestión inscripciones
├── zonas.html             # Gestión zonas
└── layout.html            # Layout base (si existe)
```

### **Navegación en Dashboard:**
```html
<!-- Sidebar Navigation -->
<ul class="space-y-2">
    <li><a href="/">Inicio</a></li>
    <li><a href="/dashboard">Dashboard</a></li>
    <li><a href="/usuarios">Usuarios</a></li>
    <li><a href="/estudiantes">Estudiantes</a></li>
    <li><a href="/rutas">Rutas</a></li>
    <li><a href="/asistencias">Asistencias</a></li>
    <li><a href="/inscripciones">Inscripciones</a></li>
    <li><a href="/zonas">Zonas</a></li>
</ul>
```

### **Acciones Rápidas:**
```html
<!-- Quick Actions -->
<div class="grid grid-cols-4 gap-4">
    <a href="/usuarios">Nuevo Usuario</a>
    <a href="/estudiantes">Nuevo Estudiante</a>
    <a href="/rutas">Nueva Ruta</a>
    <a href="/asistencias">Registrar Asistencia</a>
</div>
```

## ⚠️ PROBLEMAS IDENTIFICADOS

### **1. Relaciones Faltantes en Entidades:**
- ❌ Las entidades no tienen anotaciones `@OneToMany`, `@ManyToOne`, etc.
- ❌ No hay mapeo de relaciones JPA en las entidades
- ❌ Las relaciones están definidas solo como campos Integer

### **2. Navegación Incompleta:**
- ❌ No hay enlaces de "Volver" en las páginas
- ❌ Falta breadcrumb navigation
- ❌ No hay validación de sesión en las rutas

### **3. API Incompleta:**
- ❌ Solo existe AuthController
- ❌ Faltan controladores para otras entidades
- ❌ No hay endpoints CRUD completos

## 🔧 RECOMENDACIONES DE MEJORA

### **1. Agregar Relaciones JPA:**
```java
// En Usuario.java
@ManyToOne
@JoinColumn(name = "Id_Zona")
private Zona zona;

@ManyToOne
@JoinColumn(name = "Id_ruta")
private Ruta ruta;
```

### **2. Completar API REST:**
- Crear controladores para todas las entidades
- Implementar endpoints CRUD completos
- Agregar validaciones

### **3. Mejorar Navegación:**
- Agregar breadcrumbs
- Implementar validación de sesión
- Agregar enlaces de navegación consistentes

### **4. Optimizar Consultas:**
- Usar `@EntityGraph` para consultas eficientes
- Implementar paginación
- Agregar filtros de búsqueda

## ✅ ESTADO ACTUAL

### **Funcionando:**
- ✅ Rutas básicas de navegación
- ✅ Dashboard con estadísticas
- ✅ Estructura de templates
- ✅ Conexiones de base de datos

### **Necesita Mejora:**
- ⚠️ Relaciones JPA en entidades
- ⚠️ API REST completa
- ⚠️ Validación de sesión
- ⚠️ Navegación avanzada

**El sistema tiene una base sólida pero necesita completar las relaciones JPA y la API REST para funcionar completamente.**
