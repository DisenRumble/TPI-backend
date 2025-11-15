package backend.grupo73.catalogo.domain.service;

import backend.grupo73.catalogo.domain.dto.request.ValidacionCapacidadReq;
import backend.grupo73.catalogo.domain.repository.CamionModel;
import backend.grupo73.catalogo.domain.repository.CamionRepositoryI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidacionService {

    private final CamionRepositoryI camionRepository;

    public boolean validarCapacidad(ValidacionCapacidadReq request) {
        CamionModel camion = camionRepository.findById(request.getCamionId()).orElseThrow();
        return camion.getCapacidadPeso() >= request.getPesoTotal() && camion.getCapacidadVolumen() >= request.getVolumenTotal();
    }
}
