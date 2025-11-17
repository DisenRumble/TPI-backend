package backend.grupo130.tramos.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TramoRequest(
    @NotNull Integer orden,
    @NotNull UUID ubicacionOrigenId,
    @NotNull UUID ubicacionDestinoId,
    UUID depositoId,
    @NotNull Long tipoTramoId,
    @NotNull Long estadoTramoId,
    String patenteCamion,
    Double distanciaKm,
    Integer tiempoEstimadoHoras,
    LocalDateTime fechaEstimadaInicio,
    LocalDateTime fechaEstimadaFin,
    BigDecimal costoEstimado
) { }
