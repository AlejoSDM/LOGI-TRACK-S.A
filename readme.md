# LogiTrack S.A. — Sistema de Gestión de Bodegas e Inventarios

Sistema de gestión logística desarrollado con **Java y Spring Boot**, diseñado para administrar bodegas, productos, inventarios y movimientos de mercancía entre diferentes ubicaciones. Incorpora autenticación mediante JWT, control de acceso por roles y registro de auditorías para mantener la trazabilidad de las operaciones.

## Descripción del proyecto

LogiTrack S.A. es una empresa que administra bodegas distribuidas en diferentes ciudades. El sistema permite centralizar la administración de sus recursos, controlar las existencias de productos y registrar los movimientos de inventario realizados por los empleados.

La aplicación cuenta con un backend REST desarrollado en Spring Boot y una interfaz web construida con HTML, CSS y JavaScript.

Su objetivo principal es facilitar el control de las operaciones logísticas, reducir los errores en la administración de inventarios y mantener un historial de las acciones realizadas por los usuarios.

## Objetivo general

Desarrollar un sistema de gestión y auditoría de bodegas que permita administrar productos e inventarios, registrar entradas, salidas y transferencias de mercancía, y mantener la trazabilidad de las operaciones realizadas por cada usuario.

## Tecnologías utilizadas

| Tecnología | Uso |
|---|---|
| Java 17 | Lenguaje de programación |
| Spring Boot | Desarrollo del backend |
| Spring Security | Seguridad y control de acceso |
| JWT | Autenticación mediante tokens |
| Spring Data JPA | Persistencia y acceso a datos |
| Hibernate | Mapeo objeto-relacional |
| MySQL | Base de datos |
| Maven | Gestión de dependencias |
| HTML5 | Estructura del frontend |
| CSS3 | Diseño de la interfaz |
| JavaScript | Lógica e integración con la API |
| Swagger / OpenAPI 3 | Documentación de endpoints |
| Git y GitHub | Control de versiones |

## Funcionalidades principales

### 1. Gestión de usuarios y autenticación

- Inicio de sesión mediante usuario y contraseña.
- Registro de nuevas solicitudes de acceso.
- Contraseñas protegidas mediante BCrypt.
- Autenticación mediante tokens JWT.
- Control de acceso según el rol del usuario.
- Aprobación o rechazo de solicitudes de empleados por parte del administrador.
- Administración de usuarios y sus estados.

**Roles del sistema:**

- `ADMIN`: administra los módulos y las solicitudes de acceso.
- `EMPLEADO`: consulta información y registra operaciones de inventario según sus permisos.

### 2. Gestión de bodegas

Permite administrar las bodegas de la empresa.

- Registrar bodegas.
- Consultar todas las bodegas.
- Buscar bodegas por identificador.
- Actualizar información.
- Eliminar registros.
- Consultar bodegas por ciudad o empleado encargado.

Cada bodega almacena información como nombre, ciudad, capacidad y empleado responsable.

### 3. Gestión de productos

Permite administrar el catálogo de productos.

- Crear productos.
- Consultar productos.
- Actualizar información.
- Eliminar productos.
- Buscar productos por nombre.
- Filtrar por categoría y marca.

Los productos se relacionan con una marca y una categoría.

### 4. Administración de inventarios

El inventario se gestiona individualmente para cada bodega.

- Registrar productos en una bodega.
- Consultar inventarios.
- Consultar inventario por bodega.
- Consultar inventario por producto.
- Actualizar existencias.
- Eliminar registros de inventario.
- Consultar productos con stock bajo.
- Obtener el stock total de una bodega.

El sistema permite identificar productos cuyo stock se encuentra por debajo del límite establecido.

### 5. Movimientos de inventario

El sistema permite registrar tres tipos de operaciones:

- **ENTRADA:** aumenta las existencias de un producto en una bodega.
- **SALIDA:** disminuye las existencias de un producto.
- **TRANSFERENCIA:** traslada productos entre dos bodegas, actualizando las existencias correspondientes.

Cada operación registra información como fecha, usuario responsable, producto, cantidad y bodega o bodegas involucradas.

### 6. Auditoría de operaciones

LogiTrack cuenta con mecanismos de auditoría para mantener un historial de las operaciones realizadas.

Se manejan dos registros especializados:

- **Auditoría de entradas y salidas:** almacena los movimientos de entrada y salida de productos.
- **Auditoría de transferencias:** registra los traslados de mercancía entre bodegas.

Adicionalmente, el módulo de reportes generales registra acciones realizadas sobre diferentes módulos del sistema, incluyendo:

- Creación de registros.
- Modificación de registros.
- Eliminación de registros.
- Entradas.
- Salidas.
- Transferencias.

Los registros contienen información sobre el usuario responsable, fecha, módulo afectado, tipo de acción, identificador del registro y descripción de la operación.

### 7. Consultas y reportes

El sistema dispone de consultas para facilitar el seguimiento de las operaciones:

- Productos con stock bajo.
- Historial de entradas y salidas.
- Historial de transferencias.
- Movimientos por usuario.
- Movimientos por producto.
- Movimientos por bodega.
- Movimientos por rango de fechas.
- Reportes generales filtrados por módulo, acción, usuario y fecha.

### 8. Validaciones y manejo de excepciones

El backend incorpora:

- Validaciones mediante Jakarta Bean Validation.
- Validaciones de reglas de negocio.
- Comprobaciones de existencia de registros.
- Manejo centralizado de excepciones mediante `@ControllerAdvice`.
- Respuestas personalizadas para los errores de la API.

## Arquitectura del proyecto

El backend está organizado por responsabilidades, separando controladores, servicios, repositorios y componentes de soporte.

```text
src/
├── main/
│   ├── java/
│   │   └── com/s2/LogiTrack_S/A/
│   │       ├── auth/
│   │       ├── config/
│   │       ├── controller/
│   │       ├── dto/
│   │       │   ├── request/
│   │       │   └── response/
│   │       ├── exception/
│   │       ├── mapper/
│   │       ├── model/
│   │       ├── repository/
│   │       ├── service/
│   │       │   └── impl/
│   │       └── validation/
│   │
│   └── resources/
│       └── application.properties/
│
├── test/
│
├── app.js
├── index.html
├── style.css
├── script.sql
└── pom.xml
```

### Responsabilidad de los paquetes

| Paquete | Responsabilidad |
|---|---|
| `auth` | Inicio de sesión, registro y solicitudes de acceso |
| `config` | Configuración de seguridad, JWT y OpenAPI |
| `controller` | Exposición de endpoints REST |
| `dto` | Objetos para solicitudes y respuestas |
| `exception` | Manejo centralizado de excepciones |
| `mapper` | Conversión entre entidades y DTO |
| `model` | Entidades del sistema |
| `repository` | Acceso a la base de datos |
| `service` | Contratos de los servicios |
| `service.impl` | Implementación de la lógica de negocio |
| `validation` | Validaciones y reglas de negocio |

## Base de datos

El proyecto utiliza MySQL para almacenar la información.

Entre las principales tablas se encuentran:

- `ciudad`
- `persona`
- `usuario`
- `bodega`
- `marca`
- `categoria_p`
- `productos`
- `inventario_bodega`
- `auditoria_salida_entrada`
- `auditoria_transferencia`
- `reportes_auditorias`

El script de creación de la base de datos se encuentra en:

```text
script.sql
```

## Instalación y ejecución

### Requisitos previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

- Java JDK 17.
- MySQL Server.
- Maven (opcional, el proyecto incluye Maven Wrapper).
- Git.
- Un navegador web.
- Visual Studio Code o un IDE compatible con Spring Boot.

### 1. Clonar el repositorio

```bash
git clone https://github.com/AlejoSDM/LOGI-TRACK-S.A.git
```

Ingresar al directorio:

```bash
cd LOGI-TRACK-S.A
```

### 2. Configurar la base de datos

Abrir MySQL Workbench o un cliente MySQL.

Ejecutar el archivo:

```text
script.sql
```

Este script crea la base de datos `LogiTrackAlejo` y sus tablas.

**Importante:** el script comienza eliminando la base de datos si ya existe. Si tienes información que deseas conservar, realiza una copia de seguridad antes de ejecutarlo.

### 3. Configurar la conexión

Abrir:

```text
src/main/resources/application.properties
```

Configurar los datos de conexión de MySQL según el entorno local:

```properties
spring.application.name=LogiTrack-S.A.

spring.datasource.url=jdbc:mysql://localhost:3306/LogiTrackAlejo
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD

spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
```

No publiques contraseñas reales ni credenciales privadas en el repositorio.

### 4. Ejecutar el backend

En Windows:

```bash
mvnw.cmd spring-boot:run
```

En Linux o macOS:

```bash
./mvnw spring-boot:run
```

El backend se ejecutará en:

```text
http://localhost:8080
```

### 5. Ejecutar el frontend

Abrir el proyecto con Visual Studio Code y ejecutar `../../Desktop/FRONT/index.html` mediante la extensión Live Server.

La configuración CORS del backend contempla los siguientes orígenes:

```text
http://localhost:5500
http://127.0.0.1:5500
```

## Documentación de la API

La API está documentada mediante Swagger UI y OpenAPI 3.

Una vez iniciado el backend, ingresar a:

[http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Desde Swagger es posible consultar los endpoints, revisar los parámetros y probar las operaciones disponibles.

Para probar endpoints protegidos:

1. Ejecutar `/auth/login`.
2. Copiar el token JWT recibido.
3. Seleccionar el botón **Authorize** en Swagger.
4. Introducir el token.
5. Ejecutar las operaciones autorizadas para el usuario.

## Principales endpoints REST

### Autenticación

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/auth/login` | Iniciar sesión |
| POST | `/auth/register` | Registrar solicitud de acceso |

### Solicitudes de acceso

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/solicitudes` | Consultar solicitudes pendientes |
| PUT | `/api/solicitudes/{id}/aprobar` | Aprobar empleado |
| DELETE | `/api/solicitudes/{id}` | Rechazar solicitud |

### Bodegas

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/bodegas` | Listar bodegas |
| GET | `/api/bodegas/{id}` | Consultar por ID |
| GET | `/api/bodegas/buscar` | Buscar bodegas |
| GET | `/api/bodegas/ciudad/{ciudadId}` | Filtrar por ciudad |
| GET | `/api/bodegas/empleado/{usuarioId}` | Filtrar por encargado |
| POST | `/api/bodegas` | Registrar bodega |
| PUT | `/api/bodegas/{id}` | Actualizar bodega |
| DELETE | `/api/bodegas/{id}` | Eliminar bodega |

### Productos

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/productos` | Listar productos |
| GET | `/api/productos/{id}` | Consultar por ID |
| GET | `/api/productos/buscar` | Buscar productos |
| GET | `/api/productos/categoria/{categoriaId}` | Filtrar por categoría |
| GET | `/api/productos/marca/{marcaId}` | Filtrar por marca |
| POST | `/api/productos` | Registrar producto |
| PUT | `/api/productos/{id}` | Actualizar producto |
| DELETE | `/api/productos/{id}` | Eliminar producto |

### Inventarios

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/inventarios` | Listar inventarios |
| GET | `/api/inventarios/{id}` | Consultar por ID |
| GET | `/api/inventarios/bodega/{bodegaId}` | Consultar por bodega |
| GET | `/api/inventarios/producto/{productoId}` | Consultar por producto |
| GET | `/api/inventarios/stock-bajo` | Consultar stock bajo |
| GET | `/api/inventarios/bodega/{bodegaId}/total-stock` | Consultar stock total |
| POST | `/api/inventarios` | Registrar inventario |
| PUT | `/api/inventarios/{id}` | Actualizar inventario |
| DELETE | `/api/inventarios/{id}` | Eliminar inventario |

### Auditorías de entradas y salidas

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/auditorias-salida-entrada` | Registrar entrada o salida |
| GET | `/api/auditorias-salida-entrada` | Listar movimientos |
| GET | `/api/auditorias-salida-entrada/{id}` | Consultar por ID |
| GET | `/api/auditorias-salida-entrada/fecha` | Filtrar por fechas |
| GET | `/api/auditorias-salida-entrada/usuario/{usuarioId}` | Filtrar por usuario |
| GET | `/api/auditorias-salida-entrada/tipo/{tipoAccion}` | Filtrar por tipo |
| GET | `/api/auditorias-salida-entrada/bodega/{bodegaId}` | Filtrar por bodega |
| GET | `/api/auditorias-salida-entrada/producto/{productoId}` | Filtrar por producto |

### Auditorías de transferencias

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/auditorias-transferencias` | Registrar transferencia |
| GET | `/api/auditorias-transferencias` | Listar transferencias |
| GET | `/api/auditorias-transferencias/{id}` | Consultar por ID |
| GET | `/api/auditorias-transferencias/fecha` | Filtrar por fechas |
| GET | `/api/auditorias-transferencias/usuario/{usuarioId}` | Filtrar por usuario |
| GET | `/api/auditorias-transferencias/producto/{productoId}` | Filtrar por producto |
| GET | `/api/auditorias-transferencias/bodega/{bodegaId}` | Filtrar por bodega |
| GET | `/api/auditorias-transferencias/bodega-origen/{bodegaId}` | Filtrar por bodega de origen |
| GET | `/api/auditorias-transferencias/bodega-destino/{bodegaId}` | Filtrar por bodega destino |

### Reportes generales de auditoría

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/reportes-auditorias` | Listar reportes |
| GET | `/api/reportes-auditorias/{id}` | Consultar por ID |
| GET | `/api/reportes-auditorias/modulo/{modulo}` | Filtrar por módulo |
| GET | `/api/reportes-auditorias/accion/{tipoAccion}` | Filtrar por acción |
| GET | `/api/reportes-auditorias/fecha` | Filtrar por fechas |
| GET | `/api/reportes-auditorias/usuario/{usuarioId}` | Filtrar por usuario |

> Los endpoints pueden requerir autenticación y permisos según el rol del usuario.

## Capturas de pantalla

Las siguientes imágenes permiten mostrar el funcionamiento de la aplicación y su documentación.


### Inicio de sesión

![alt text](../../Desktop/FRONT/image.png)

### Panel principal

![alt text](../../Desktop/FRONT/image-2.png)

### Gestión de bodegas

![alt text](../../Desktop/FRONT/image-1.png)

### Gestión de productos

![alt text](../../Desktop/FRONT/image-3.png)

### Inventario por bodega

![alt text](../../Desktop/FRONT/image-9.png)

### Historial de movimientos y auditorías

![alt text](../../Desktop/FRONT/image-4.png)

### Reportes generales

![alt text](../../Desktop/FRONT/image-5.png)

### Documentación Swagger

![alt text](../../Desktop/FRONT/image-6.png)
![alt text](../../Desktop/FRONT/image-7.png)

### Prueba de autenticación JWT

![alt text](../../Desktop/FRONT/image-8.png)

## Repositorio

Código fuente:

[GitHub — LogiTrack S.A.](https://github.com/AlejoSDM/LOGI-TRACK-S.A)

---

**LogiTrack S.A.**  
Sistema de gestión logística, inventarios y auditoría de operaciones.