package backend.grupo130.tramos.mapper;

import backend.grupo130.tramos.dto.EstadoTramoRequest;
import backend.grupo130.tramos.dto.EstadoTramoResponse;
import backend.grupo130.tramos.entity.EstadoTramo;

public final class EstadoTramoMapper {

    private EstadoTramoMapper() {
    }

    public static EstadoTramoResponse toResponse(EstadoTramo estadoTramo) {
        return new EstadoTramoResponse(
            estadoTramo.getId(),
            estadoTramo.getCodigo(),
            estadoTramo.getDescripcion()
        );
    }

    public static void updateEntity(EstadoTramo estadoTramo, EstadoTramoRequest request) {
        estadoTramo.setCodigo(request.codigo());
        estadoTramo.setDescripcion(request.descripcion());
    }
}
