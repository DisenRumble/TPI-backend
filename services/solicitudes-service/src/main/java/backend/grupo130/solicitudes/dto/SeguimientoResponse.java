package backend.grupo130.solicitudes.dto;

import backend.grupo130.solicitudes.entity.EstadoSolicitud;

import java.time.LocalDateTime;
import java.util.UUID;

public record SeguimientoResponse(
    UUID solicitudId,
    UUID contenedorId,
    EstadoSolicitud estado,
    LocalDateTime ultimaActualizacion,
    Integer tiempoEstimadoHoras,
    Integer tiempoRealHoras,
    String descripcion
) { }
