package backend.grupo130.ubicaciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BarrioRequest(
    @NotBlank String nombre,
    @NotNull Long ciudadId
) { }
