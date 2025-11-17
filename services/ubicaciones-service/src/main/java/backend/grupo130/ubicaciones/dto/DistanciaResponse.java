package backend.grupo130.ubicaciones.dto;

public record DistanciaResponse(
    double distanciaKm,
    long duracionMinutos,
    boolean calculadoConGoogle,
    String detalle
) { }
