package backend.grupo130.ubicaciones.mapper;

import backend.grupo130.ubicaciones.dto.BarrioResponse;
import backend.grupo130.ubicaciones.dto.UbicacionResponse;
import backend.grupo130.ubicaciones.entity.Ubicacion;

public final class UbicacionMapper {

    private UbicacionMapper() {
    }

    public static UbicacionResponse toResponse(Ubicacion ubicacion) {
        BarrioResponse barrioResponse = ubicacion.getBarrio() != null
            ? BarrioMapper.toResponse(ubicacion.getBarrio())
            : null;
        return new UbicacionResponse(
            ubicacion.getId(),
            ubicacion.getCalle(),
            ubicacion.getNumero(),
            barrioResponse,
            ubicacion.getLatitud(),
            ubicacion.getLongitud(),
            ubicacion.getReferencias()
        );
    }
}
