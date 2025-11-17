package backend.grupo130.solicitudes.dto;

import backend.grupo130.solicitudes.entity.EstadoSolicitud;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record SolicitudResponse(
    UUID id,
    UUID contenedorId,
    UUID rutaId,
    EstadoSolicitud estado,
    Integer cantidadTramos,
    Integer cantidadDepositos,
    BigDecimal costoEstimado,
    BigDecimal costoFinal,
    Integer tiempoEstimadoHoras,
    Integer tiempoRealHoras,
    LocalDateTime fechaCreacion,
    LocalDateTime fechaActualizacion,
    ClienteResponse cliente,
    TarifaResponse tarifa
) { }
