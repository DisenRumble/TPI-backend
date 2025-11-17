package backend.grupo130.contenedores.dto;

import backend.grupo130.contenedores.entity.EstadoContenedor;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CambioEstadoRequest(
    @NotNull EstadoContenedor estado,
    UUID depositoId,
    UUID solicitudId,
    String descripcion
) { }
