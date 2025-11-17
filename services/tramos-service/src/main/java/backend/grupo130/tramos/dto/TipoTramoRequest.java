package backend.grupo130.tramos.dto;

import jakarta.validation.constraints.NotBlank;

public record TipoTramoRequest(
    @NotBlank String codigo,
    String descripcion
) { }
