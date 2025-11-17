package backend.grupo73.catalogo.domain.service;

import backend.grupo73.catalogo.domain.dto.request.UbicacionCreateReq;
import backend.grupo73.catalogo.domain.dto.response.UbicacionRes;

import java.util.List;
import java.util.UUID;

public interface UbicacionServiceI {
    UbicacionRes create(UbicacionCreateReq request);
    UbicacionRes update(UUID id, UbicacionCreateReq request);
    UbicacionRes getById(UUID id);
    List<UbicacionRes> search(String query);
}
