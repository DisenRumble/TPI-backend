package backend.grupo73.catalogo.domain.service;

import backend.grupo73.catalogo.domain.dto.request.ValidacionCapacidadReq;

public interface ValidacionServiceI {
    boolean validarCapacidad(ValidacionCapacidadReq request);
}
