CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS cliente (
  id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
  keycloak_sub VARCHAR(100) NOT NULL UNIQUE,
  email        VARCHAR(200) NOT NULL UNIQUE,
  nombre       VARCHAR(100) NOT NULL,
  apellido     VARCHAR(100) NOT NULL,
  telefono     VARCHAR(50),
  direccion    VARCHAR(255),
  created_at   TIMESTAMP NOT NULL DEFAULT now()
);
