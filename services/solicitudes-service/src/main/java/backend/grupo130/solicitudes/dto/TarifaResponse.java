package backend.grupo130.solicitudes.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record TarifaResponse(
    UUID id,
    BigDecimal cargoGestionPorTramo,
    BigDecimal valorPorKilometro,
    BigDecimal valorPorVolumen,
    BigDecimal valorCombustibleLitro,
    boolean vigente
) { }
