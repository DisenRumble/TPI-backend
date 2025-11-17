package backend.grupo130.tramos.mapper;

import backend.grupo130.tramos.dto.RutaResponse;
import backend.grupo130.tramos.dto.TramoResponse;
import backend.grupo130.tramos.entity.Ruta;

import java.util.List;

public final class RutaMapper {

    private RutaMapper() {
    }

    public static RutaResponse toResponse(Ruta ruta) {
        List<TramoResponse> tramos = ruta.getTramos().stream()
            .sorted((a, b) -> Integer.compare(a.getOrden(), b.getOrden()))
            .map(TramoMapper::toResponse)
            .toList();

        return new RutaResponse(
            ruta.getId(),
            ruta.getSolicitudId(),
            ruta.getCantidadTramos(),
            ruta.getCantidadDepositos(),
            ruta.getDistanciaTotalKm(),
            ruta.getTiempoEstimadoHoras(),
            ruta.getTiempoRealHoras(),
            ruta.getCostoEstimado(),
            ruta.getCostoReal(),
            ruta.getFechaCreacion(),
            tramos
        );
    }
}
