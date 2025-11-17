package backend.grupo130.tramos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record RutaResponse(
    UUID id,
    UUID solicitudId,
    Integer cantidadTramos,
    Integer cantidadDepositos,
    Double distanciaTotalKm,
    Integer tiempoEstimadoHoras,
    Integer tiempoRealHoras,
    BigDecimal costoEstimado,
    BigDecimal costoReal,
    LocalDateTime fechaCreacion,
    List<TramoResponse> tramos
) { }
