
DROP DATABASE IF EXISTS LogiTrackAlejo;

CREATE DATABASE LogiTrackAlejo;
USE LogiTrackAlejo;

-- 1. CIUDAD
CREATE TABLE ciudad (
    id_ciudad INT NOT NULL AUTO_INCREMENT,
    nombre_ciudad VARCHAR(100) NOT NULL,
    PRIMARY KEY (id_ciudad)
);

-- 2. PERSONA
CREATE TABLE persona (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

-- 3. USUARIO
CREATE TABLE usuario (
    id INT NOT NULL AUTO_INCREMENT,
    persona INT NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    contraseña VARCHAR(255) NOT NULL,
    rol ENUM('ADMIN', 'EMPLEADO') NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE,
    PRIMARY KEY (id),
    FOREIGN KEY (persona) REFERENCES persona(id)
);

-- 4. BODEGA
CREATE TABLE bodega (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    ciudad_fk INT NOT NULL,
    capacidad INT NOT NULL,
    empleado_fk INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (ciudad_fk) REFERENCES ciudad(id_ciudad),
    FOREIGN KEY (empleado_fk) REFERENCES usuario(id)
);

-- 5. MARCA
CREATE TABLE marca (
    id INT NOT NULL AUTO_INCREMENT,
    nombre_marca VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

-- 6. CATEGORIA_PRODUCTO
CREATE TABLE categoria_p (
    id INT NOT NULL AUTO_INCREMENT,
    tipo_categoria ENUM(
        'COMIDA',
        'TECNOLOGIA',
        'ROPA',
        'SALUD'
    ) NOT NULL,
    PRIMARY KEY (id)
);

-- 7. PRODUCTOS
CREATE TABLE productos (
    id INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(150) NOT NULL,
    marca_fk INT NOT NULL,
    categoria_fk INT NOT NULL,
    precio_u DECIMAL(12,2) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (marca_fk) REFERENCES marca(id),
    FOREIGN KEY (categoria_fk) REFERENCES categoria_p(id)
);

-- 8. INVENTARIO_BODEGA
CREATE TABLE inventario_bodega (
    id INT NOT NULL AUTO_INCREMENT,
    bodega_fk INT NOT NULL,
    producto_fk INT NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE (bodega_fk, producto_fk),
    FOREIGN KEY (bodega_fk) REFERENCES bodega(id),
    FOREIGN KEY (producto_fk) REFERENCES productos(id)
);

-- 9. AUDITORIA_SALIDA_ENTRADA
CREATE TABLE auditoria_salida_entrada (
    id INT NOT NULL AUTO_INCREMENT,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    tipo_accion ENUM('ENTRADA', 'SALIDA') NOT NULL,
    usuario_responsable INT NOT NULL,
    bodega_fk INT NOT NULL,
    producto_fk INT NOT NULL,
    cantidad INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (usuario_responsable) REFERENCES usuario(id),
    FOREIGN KEY (bodega_fk) REFERENCES bodega(id),
    FOREIGN KEY (producto_fk) REFERENCES productos(id)
);

-- 10. AUDITORIA_TRANSFERENCIA
CREATE TABLE auditoria_transferencia (
    id INT NOT NULL AUTO_INCREMENT,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    usuario_responsable INT NOT NULL,
    bodega_origen INT NOT NULL,
    bodega_destino INT NOT NULL,
    producto_fk INT NOT NULL,
    cantidad INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (usuario_responsable) REFERENCES usuario(id),
    FOREIGN KEY (bodega_origen) REFERENCES bodega(id),
    FOREIGN KEY (bodega_destino) REFERENCES bodega(id),
    FOREIGN KEY (producto_fk) REFERENCES productos(id)
);

CREATE TABLE reportes_auditorias (
    id INT NOT NULL AUTO_INCREMENT,
    fecha DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,

    usuario_id INT NOT NULL,

    modulo VARCHAR(50) NOT NULL,
    tipo_accion VARCHAR(30) NOT NULL,

    registro_id INT NULL,

    descripcion VARCHAR(500) NOT NULL,

    PRIMARY KEY (id),

    FOREIGN KEY (usuario_id)
        REFERENCES usuario(id)
);
