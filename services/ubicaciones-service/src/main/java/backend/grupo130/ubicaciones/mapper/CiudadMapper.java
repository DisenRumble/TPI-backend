package backend.grupo130.ubicaciones.mapper;

import backend.grupo130.ubicaciones.dto.CiudadResponse;
import backend.grupo130.ubicaciones.dto.ProvinciaResponse;
import backend.grupo130.ubicaciones.entity.Ciudad;

public final class CiudadMapper {

    private CiudadMapper() {
    }

    public static CiudadResponse toResponse(Ciudad ciudad) {
        ProvinciaResponse provinciaResponse = ProvinciaMapper.toResponse(ciudad.getProvincia());
        return new CiudadResponse(ciudad.getId(), ciudad.getNombre(), provinciaResponse);
    }
}
