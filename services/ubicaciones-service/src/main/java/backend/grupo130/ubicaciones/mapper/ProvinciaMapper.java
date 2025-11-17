package backend.grupo130.ubicaciones.mapper;

import backend.grupo130.ubicaciones.dto.ProvinciaRequest;
import backend.grupo130.ubicaciones.dto.ProvinciaResponse;
import backend.grupo130.ubicaciones.entity.Provincia;

public final class ProvinciaMapper {

    private ProvinciaMapper() {
    }

    public static ProvinciaResponse toResponse(Provincia provincia) {
        return new ProvinciaResponse(provincia.getId(), provincia.getNombre());
    }

    public static void updateEntity(Provincia provincia, ProvinciaRequest request) {
        provincia.setNombre(request.nombre());
    }
}
