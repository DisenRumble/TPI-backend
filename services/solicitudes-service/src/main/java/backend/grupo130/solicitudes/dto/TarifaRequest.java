package backend.grupo130.solicitudes.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TarifaRequest(
    @NotNull @DecimalMin("0.0") BigDecimal cargoGestionPorTramo,
    @NotNull @DecimalMin("0.0") BigDecimal valorPorKilometro,
    @NotNull @DecimalMin("0.0") BigDecimal valorPorVolumen,
    @NotNull @DecimalMin("0.0") BigDecimal valorCombustibleLitro
) { }
