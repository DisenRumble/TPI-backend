package backend.grupo130.ubicaciones.dto;

import jakarta.validation.constraints.NotNull;

public record CoordenadaRequest(
    @NotNull Double latitud,
    @NotNull Double longitud
) { }
