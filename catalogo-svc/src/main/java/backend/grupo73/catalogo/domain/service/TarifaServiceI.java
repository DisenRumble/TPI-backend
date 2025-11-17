package backend.grupo73.catalogo.domain.service;

import backend.grupo73.catalogo.domain.dto.request.TarifaCreateReq;
import backend.grupo73.catalogo.domain.dto.response.TarifaRes;

import java.util.UUID;

public interface TarifaServiceI {
    TarifaRes create(TarifaCreateReq request);
    TarifaRes update(UUID id, TarifaCreateReq request);
    TarifaRes getTarifaVigente();
}
