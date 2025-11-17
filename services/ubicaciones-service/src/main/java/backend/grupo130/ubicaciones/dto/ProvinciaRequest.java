package backend.grupo130.ubicaciones.dto;

import jakarta.validation.constraints.NotBlank;

public record ProvinciaRequest(
    @NotBlank String nombre
) { }
