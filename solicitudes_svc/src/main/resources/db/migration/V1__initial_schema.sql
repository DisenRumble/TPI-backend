CREATE TABLE contenedores (
    id UUID PRIMARY KEY,
    identificacion_unica VARCHAR(255) NOT NULL UNIQUE,
    peso NUMERIC(19, 2) NOT NULL,
    volumen NUMERIC(19, 2) NOT NULL,
    estado VARCHAR(255) NOT NULL,
    ubicacion_actual VARCHAR(255)
);

CREATE TABLE solicitudes (
    id UUID PRIMARY KEY,
    cliente_id UUID NOT NULL,
    contenedor_id UUID NOT NULL REFERENCES contenedores(id),
    origen VARCHAR(255) NOT NULL,
    destino VARCHAR(255) NOT NULL,
    estado VARCHAR(255) NOT NULL,
    ruta_id UUID,
    costo_estimado NUMERIC(19, 2),
    tiempo_estimado_minutos INTEGER,
    costo_real NUMERIC(19, 2),
    tiempo_real_minutos INTEGER,
    created_at TIMESTAMP WITH TIME ZONE,
    updated_at TIMESTAMP WITH TIME ZONE
);
