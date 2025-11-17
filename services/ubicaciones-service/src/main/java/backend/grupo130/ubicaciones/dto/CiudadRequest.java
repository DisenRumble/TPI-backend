package backend.grupo130.ubicaciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CiudadRequest(
    @NotBlank String nombre,
    @NotNull Long provinciaId
) { }
