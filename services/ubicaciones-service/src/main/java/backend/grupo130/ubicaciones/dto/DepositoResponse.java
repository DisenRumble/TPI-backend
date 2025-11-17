package backend.grupo130.ubicaciones.dto;

import java.util.UUID;

public record DepositoResponse(
    UUID id,
    String nombre,
    UbicacionResponse ubicacion,
    Integer capacidadMaxima,
    String telefono,
    boolean activo
) { }
