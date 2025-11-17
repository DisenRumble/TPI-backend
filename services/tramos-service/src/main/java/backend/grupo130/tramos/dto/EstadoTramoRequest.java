package backend.grupo130.tramos.dto;

import jakarta.validation.constraints.NotBlank;

public record EstadoTramoRequest(
    @NotBlank String codigo,
    @NotBlank String descripcion
) { }
