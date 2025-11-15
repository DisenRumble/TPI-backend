package backend.grupo73.catalogo.domain.service;

import backend.grupo73.catalogo.domain.dto.request.CamionCreateReq;
import backend.grupo73.catalogo.domain.dto.response.CamionRes;
import backend.grupo73.catalogo.domain.repository.CamionModel;
import backend.grupo73.catalogo.domain.repository.CamionRepositoryI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CamionService {

    private final CamionRepositoryI camionRepository;

    public CamionRes create(CamionCreateReq request) {
        CamionModel model = new CamionModel();
        model.setPatente(request.getPatente());
        model.setCapacidadPeso(request.getCapacidadPeso());
        model.setCapacidadVolumen(request.getCapacidadVolumen());
        camionRepository.save(model);
        return new CamionRes(model.getId(), model.getPatente(), model.getCapacidadPeso(), model.getCapacidadVolumen());
    }

    public CamionRes update(Long id, CamionCreateReq request) {
        CamionModel model = camionRepository.findById(id).orElseThrow();
        model.setPatente(request.getPatente());
        model.setCapacidadPeso(request.getCapacidadPeso());
        model.setCapacidadVolumen(request.getCapacidadVolumen());
        camionRepository.save(model);
        return new CamionRes(model.getId(), model.getPatente(), model.getCapacidadPeso(), model.getCapacidadVolumen());
    }

    public List<CamionRes> getAll() {
        return camionRepository.findAll().stream()
                .map(model -> new CamionRes(model.getId(), model.getPatente(), model.getCapacidadPeso(), model.getCapacidadVolumen()))
                .collect(Collectors.toList());
    }
}
