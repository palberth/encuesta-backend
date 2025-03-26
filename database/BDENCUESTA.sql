-- Creación de la base de datos
CREATE DATABASE IF NOT EXISTS BDENCUESTA;
USE BDENCUESTA;

-- Tabla de usuarios
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(32) NOT NULL, -- MD5 siempre produce 32 caracteres
    enabled BOOLEAN NOT NULL DEFAULT TRUE
);

-- Tabla de encuestas
CREATE TABLE encuestas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    pais_residencia VARCHAR(100) NOT NULL,
    nacionalidad VARCHAR(100) NOT NULL,
    sexo VARCHAR(20) NOT NULL,
    edad INT NOT NULL,
    viaja_con VARCHAR(50) NOT NULL,
    cantidad_personas INT,
    motivo_viaje VARCHAR(100) NOT NULL,
    organizacion_viaje VARCHAR(100) NOT NULL,
    servicios_paquete TEXT,
    gastos_paquete JSON,
    gastos_transporte JSON,
    paises_visita JSON,
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

-- Datos iniciales (opcional)
INSERT INTO usuarios (username, password, enabled) VALUES 
('admin', MD5('admin123'), 1),
('user1', MD5('password1'), 1);