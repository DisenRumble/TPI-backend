package backend.grupo130.ubicaciones.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record DistanciaRequest(
    @NotNull @Valid CoordenadaRequest origen,
    @NotNull @Valid CoordenadaRequest destino
) { }
