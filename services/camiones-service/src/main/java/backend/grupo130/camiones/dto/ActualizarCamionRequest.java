package backend.grupo130.camiones.dto;

import backend.grupo130.camiones.entity.EstadoCamion;

import java.math.BigDecimal;
import java.util.UUID;

public record ActualizarCamionRequest(
    UUID transportistaId,
    BigDecimal capacidadVolumenM3,
    BigDecimal capacidadPesoKg,
    BigDecimal consumoLitrosKm,
    BigDecimal costoKm,
    Integer anioFabricacion,
    String tipo,
    UUID depositoId,
    EstadoCamion estado
) { }
