package backend.grupo130.solicitudes.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record SolicitudRequest(
    UUID clienteId,
    @Valid ClienteRequest clienteNuevo,
    @NotNull UUID contenedorId,
    UUID rutaId,
    @NotNull UUID tarifaId,
    Integer cantidadTramos,
    Integer cantidadDepositos,
    BigDecimal costoEstimado,
    Integer tiempoEstimadoHoras
) { }
