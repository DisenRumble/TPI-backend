package backend.grupo130.ubicaciones.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record UbicacionRequest(
    @NotBlank String calle,
    @NotBlank String numero,
    Long barrioId,
    Double latitud,
    Double longitud,
    String referencias
) { }
