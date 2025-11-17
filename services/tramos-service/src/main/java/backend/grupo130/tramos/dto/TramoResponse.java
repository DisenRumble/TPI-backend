package backend.grupo130.tramos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TramoResponse(
    UUID id,
    Integer orden,
    UUID ubicacionOrigenId,
    UUID ubicacionDestinoId,
    UUID depositoId,
    TipoTramoResponse tipoTramo,
    EstadoTramoResponse estadoTramo,
    String patenteCamion,
    Double distanciaKm,
    Integer tiempoEstimadoHoras,
    Integer tiempoRealHoras,
    BigDecimal costoEstimado,
    BigDecimal costoReal,
    LocalDateTime fechaEstimadaInicio,
    LocalDateTime fechaEstimadaFin,
    LocalDateTime fechaRealInicio,
    LocalDateTime fechaRealFin
) { }
