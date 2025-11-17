
ALTER TABLE solicitudes DROP CONSTRAINT IF EXISTS fk_solicitud_ruta;

ALTER TABLE solicitudes DROP COLUMN IF EXISTS ruta_id;

DROP TABLE IF EXISTS solicitud_tramos;
DROP TABLE IF EXISTS solicitud_rutas;

CREATE TABLE solicitud_tramos_ejecucion (
    id UUID PRIMARY KEY,
    solicitud_id UUID NOT NULL,
    origen_ubicacion_id UUID NOT NULL,
    destino_ubicacion_id UUID NOT NULL,
    camion_id UUID,
    distancia_estimada_km DOUBLE PRECISION,
    tiempo_estimado_min INTEGER,
    distancia_real_km DOUBLE PRECISION,
    tiempo_real_min INTEGER,
    fecha_hora_inicio_real TIMESTAMP WITH TIME ZONE,
    fecha_hora_fin_real TIMESTAMP WITH TIME ZONE,
    orden INTEGER NOT NULL,
    FOREIGN KEY (solicitud_id) REFERENCES solicitudes(id)
);

CREATE TABLE solicitud_estadias_deposito (
    id UUID PRIMARY KEY,
    solicitud_id UUID NOT NULL,
    deposito_ubicacion_id UUID NOT NULL,
    fecha_hora_entrada TIMESTAMP WITH TIME ZONE NOT NULL,
    fecha_hora_salida TIMESTAMP WITH TIME ZONE,
    horas_estadia BIGINT,
    FOREIGN KEY (solicitud_id) REFERENCES solicitudes(id)
);

CREATE TABLE solicitud_depositos_intermedios (
    solicitud_id UUID NOT NULL,
    ubicacion_id UUID NOT NULL,
    PRIMARY KEY (solicitud_id, ubicacion_id),
    FOREIGN KEY (solicitud_id) REFERENCES solicitudes(id)
);
