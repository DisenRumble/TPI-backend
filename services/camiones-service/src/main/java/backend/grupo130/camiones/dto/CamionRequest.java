package backend.grupo130.camiones.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record CamionRequest(
    @NotBlank @Size(max = 10) String patente,
    @NotNull UUID transportistaId,
    @NotNull @DecimalMin("0.0") BigDecimal capacidadVolumenM3,
    @NotNull @DecimalMin("0.0") BigDecimal capacidadPesoKg,
    @NotNull @DecimalMin("0.0") BigDecimal consumoLitrosKm,
    @NotNull @DecimalMin("0.0") BigDecimal costoKm,
    Integer anioFabricacion,
    String tipo,
    UUID depositoId
) { }
