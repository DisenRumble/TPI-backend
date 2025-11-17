package backend.grupo130.ubicaciones.mapper;

import backend.grupo130.ubicaciones.dto.BarrioResponse;
import backend.grupo130.ubicaciones.dto.CiudadResponse;
import backend.grupo130.ubicaciones.entity.Barrio;

public final class BarrioMapper {

    private BarrioMapper() {
    }

    public static BarrioResponse toResponse(Barrio barrio) {
        CiudadResponse ciudadResponse = CiudadMapper.toResponse(barrio.getCiudad());
        return new BarrioResponse(barrio.getId(), barrio.getNombre(), ciudadResponse);
    }
}
