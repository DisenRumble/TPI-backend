package backend.grupo73.catalogo.domain.service;

import backend.grupo73.catalogo.domain.dto.request.ValidacionCapacidadReq;
import backend.grupo73.catalogo.domain.entity.Camion; // Import the new Camion entity
import backend.grupo73.catalogo.domain.repository.CamionRepositoryI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidacionService implements ValidacionServiceI {

    private final CamionRepositoryI camionRepository;

    @Override
    public boolean validarCapacidad(ValidacionCapacidadReq request) {
        Camion camion = camionRepository.findById(request.getCamionId())
                .orElseThrow(() -> new RuntimeException("Camion no encontrado con ID: " + request.getCamionId()));
        return camion.getCapacidadMaximaPesoKg().compareTo(request.getPesoTotal()) >= 0 &&
               camion.getCapacidadMaximaVolumenM3().compareTo(request.getVolumenTotal()) >= 0;
    }
}
