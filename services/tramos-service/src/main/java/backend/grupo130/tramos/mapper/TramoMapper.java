package backend.grupo130.tramos.mapper;

import backend.grupo130.tramos.dto.EstadoTramoResponse;
import backend.grupo130.tramos.dto.TipoTramoResponse;
import backend.grupo130.tramos.dto.TramoResponse;
import backend.grupo130.tramos.entity.Tramo;

public final class TramoMapper {

    private TramoMapper() {
    }

    public static TramoResponse toResponse(Tramo tramo) {
        TipoTramoResponse tipoResponse = TipoTramoMapper.toResponse(tramo.getTipoTramo());
        EstadoTramoResponse estadoResponse = EstadoTramoMapper.toResponse(tramo.getEstadoTramo());
        return new TramoResponse(
            tramo.getId(),
            tramo.getOrden(),
            tramo.getUbicacionOrigenId(),
            tramo.getUbicacionDestinoId(),
            tramo.getDepositoId(),
            tipoResponse,
            estadoResponse,
            tramo.getPatenteCamion(),
            tramo.getDistanciaKm(),
            tramo.getTiempoEstimadoHoras(),
            tramo.getTiempoRealHoras(),
            tramo.getCostoEstimado(),
            tramo.getCostoReal(),
            tramo.getFechaEstimadaInicio(),
            tramo.getFechaEstimadaFin(),
            tramo.getFechaRealInicio(),
            tramo.getFechaRealFin()
        );
    }
}
