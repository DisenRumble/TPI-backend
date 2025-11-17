package backend.grupo130.tramos.mapper;

import backend.grupo130.tramos.dto.TipoTramoRequest;
import backend.grupo130.tramos.dto.TipoTramoResponse;
import backend.grupo130.tramos.entity.TipoTramo;

public final class TipoTramoMapper {

    private TipoTramoMapper() {
    }

    public static TipoTramoResponse toResponse(TipoTramo tipoTramo) {
        return new TipoTramoResponse(
            tipoTramo.getId(),
            tipoTramo.getCodigo(),
            tipoTramo.getDescripcion()
        );
    }

    public static void updateEntity(TipoTramo tipoTramo, TipoTramoRequest request) {
        tipoTramo.setCodigo(request.codigo());
        tipoTramo.setDescripcion(request.descripcion());
    }
}
