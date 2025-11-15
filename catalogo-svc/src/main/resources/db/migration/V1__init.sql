CREATE TABLE deposito_model (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL
);

CREATE TABLE camion_model (
    id BIGSERIAL PRIMARY KEY,
    patente VARCHAR(255) NOT NULL,
    capacidad_peso DOUBLE PRECISION NOT NULL,
    capacidad_volumen DOUBLE PRECISION NOT NULL
);

CREATE TABLE tarifa_model (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    precio DOUBLE PRECISION NOT NULL,
    vigente BOOLEAN NOT NULL,
    fecha DATE NOT NULL
);
