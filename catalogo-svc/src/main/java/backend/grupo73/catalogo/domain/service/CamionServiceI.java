package backend.grupo73.catalogo.domain.service;

import backend.grupo73.catalogo.domain.dto.request.CamionCreateReq;
import backend.grupo73.catalogo.domain.dto.response.CamionRes;

import java.util.List;
import java.util.UUID;

public interface CamionServiceI {
    CamionRes create(CamionCreateReq request);
    CamionRes update(UUID id, CamionCreateReq request);
    CamionRes getById(UUID id);
    List<CamionRes> getAll();
}
