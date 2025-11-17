package backend.grupo130.ubicaciones.dto;

public record BarrioResponse(
    Long id,
    String nombre,
    CiudadResponse ciudad
) { }
