package backend.grupo130.camiones.dto;

import backend.grupo130.camiones.entity.EstadoCamion;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record CamionResponse(
    String patente,
    TransportistaResponse transportista,
    BigDecimal capacidadVolumenM3,
    BigDecimal capacidadPesoKg,
    BigDecimal consumoLitrosKm,
    BigDecimal costoKm,
    Integer anioFabricacion,
    String tipo,
    UUID depositoId,
    EstadoCamion estado,
    LocalDateTime fechaAlta,
    LocalDateTime fechaActualizacion
) { }
