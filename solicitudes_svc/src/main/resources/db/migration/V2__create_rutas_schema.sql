CREATE TABLE solicitud_rutas (
    id BIGSERIAL PRIMARY KEY,
    alternativa_id BIGINT,
    es_principal BOOLEAN,
    distancia_total_km DOUBLE PRECISION,
    duracion_total_minutos INT
);

CREATE TABLE solicitud_tramos (
    id BIGSERIAL PRIMARY KEY,
    ruta_id BIGINT,
    orden INT,
    origen_lat DOUBLE PRECISION,
    origen_lng DOUBLE PRECISION,
    origen_tipo VARCHAR(255),
    origen_descripcion VARCHAR(255),
    destino_lat DOUBLE PRECISION,
    destino_lng DOUBLE PRECISION,
    destino_tipo VARCHAR(255),
    destino_descripcion VARCHAR(255),
    distancia_km DOUBLE PRECISION,
    duracion_minutos INT,
    FOREIGN KEY (ruta_id) REFERENCES solicitud_rutas(id)
);

ALTER TABLE solicitudes DROP COLUMN IF EXISTS origen;
ALTER TABLE solicitudes DROP COLUMN IF EXISTS destino;
ALTER TABLE solicitudes ADD COLUMN origen_ubicacion_id UUID;
ALTER TABLE solicitudes ADD COLUMN destino_ubicacion_id UUID;
ALTER TABLE solicitudes ADD COLUMN origen_lat DOUBLE PRECISION;
ALTER TABLE solicitudes ADD COLUMN origen_lng DOUBLE PRECISION;
ALTER TABLE solicitudes ADD COLUMN origen_descripcion VARCHAR(255);
ALTER TABLE solicitudes ADD COLUMN destino_lat DOUBLE PRECISION;
ALTER TABLE solicitudes ADD COLUMN destino_lng DOUBLE PRECISION;
ALTER TABLE solicitudes ADD COLUMN destino_descripcion VARCHAR(255);

ALTER TABLE solicitudes DROP COLUMN IF EXISTS ruta_id;
ALTER TABLE solicitudes ADD COLUMN ruta_id BIGINT;
ALTER TABLE solicitudes ADD CONSTRAINT fk_solicitud_ruta
FOREIGN KEY (ruta_id) REFERENCES solicitud_rutas(id);
