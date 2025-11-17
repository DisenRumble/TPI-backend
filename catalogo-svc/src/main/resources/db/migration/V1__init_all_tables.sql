CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE ubicaciones (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nombre VARCHAR(255) NOT NULL,
    descripcion TEXT,
    lat DOUBLE PRECISION NOT NULL,
    lng DOUBLE PRECISION NOT NULL,
    tipo VARCHAR(50) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE camiones (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    patente VARCHAR(255) NOT NULL UNIQUE,
    descripcion TEXT,
    capacidad_maxima_peso_kg DECIMAL(10, 2) NOT NULL,
    capacidad_maxima_volumen_m3 DECIMAL(10, 2) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE tarifas (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    tarifa_por_km DECIMAL(10, 2) NOT NULL,
    tarifa_por_kg_km DECIMAL(10, 2) NOT NULL,
    tarifa_estadia_por_hora DECIMAL(10, 2) NOT NULL,
    activa BOOLEAN NOT NULL DEFAULT TRUE,
    version BIGINT NOT NULL DEFAULT 0
);
