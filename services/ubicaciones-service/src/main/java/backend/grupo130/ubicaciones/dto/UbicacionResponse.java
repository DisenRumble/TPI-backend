package backend.grupo130.ubicaciones.dto;

import java.util.UUID;

public record UbicacionResponse(
    UUID id,
    String calle,
    String numero,
    BarrioResponse barrio,
    Double latitud,
    Double longitud,
    String referencias
) { }
