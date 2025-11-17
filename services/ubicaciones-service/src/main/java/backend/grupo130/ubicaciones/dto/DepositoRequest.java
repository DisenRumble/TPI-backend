package backend.grupo130.ubicaciones.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record DepositoRequest(
    @NotBlank String nombre,
    @NotNull UUID ubicacionId,
    Integer capacidadMaxima,
    String telefono,
    Boolean activo
) { }
