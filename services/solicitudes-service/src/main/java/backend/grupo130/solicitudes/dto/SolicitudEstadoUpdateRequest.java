package backend.grupo130.solicitudes.dto;

import backend.grupo130.solicitudes.entity.EstadoSolicitud;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record SolicitudEstadoUpdateRequest(
    @NotNull EstadoSolicitud estado,
    BigDecimal costoFinal,
    Integer tiempoRealHoras
) { }
