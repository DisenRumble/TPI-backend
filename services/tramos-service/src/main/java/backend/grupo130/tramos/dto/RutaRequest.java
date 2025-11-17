package backend.grupo130.tramos.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record RutaRequest(
    @NotNull UUID solicitudId,
    Integer cantidadDepositos,
    Integer tiempoEstimadoHoras,
    BigDecimal costoEstimado,
    @NotEmpty @Valid List<TramoRequest> tramos
) { }
