package backend.grupo130.tramos.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ActualizarTramoEstadoRequest(
    @NotNull Long estadoTramoId,
    LocalDateTime fechaRealInicio,
    LocalDateTime fechaRealFin,
    Integer tiempoRealHoras,
    BigDecimal costoReal,
    String patenteCamion
) { }
