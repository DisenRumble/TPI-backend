package backend.grupo130.ubicaciones.dto;

public record CiudadResponse(
    Long id,
    String nombre,
    ProvinciaResponse provincia
) { }
