package backend.grupo73.catalogo.domain.service;

import backend.grupo73.catalogo.domain.dto.request.CamionCreateReq;
import backend.grupo73.catalogo.domain.dto.response.CamionRes;
import backend.grupo73.catalogo.domain.entity.Camion;
import backend.grupo73.catalogo.domain.repository.CamionRepositoryI;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CamionService implements CamionServiceI {

    private final CamionRepositoryI camionRepository;

    @Override
    @Transactional
    public CamionRes create(CamionCreateReq request) {
        Camion camion = new Camion();
        camion.setPatente(request.getPatente());
        camion.setDescripcion(request.getDescripcion());
        camion.setCapacidadMaximaPesoKg(request.getCapacidadMaximaPesoKg());
        camion.setCapacidadMaximaVolumenM3(request.getCapacidadMaximaVolumenM3());
        camion.setEstado(request.getEstado());
        camion.setActivo(request.isActivo());
        camion = camionRepository.save(camion);
        return toDto(camion);
    }

    @Override
    @Transactional
    public CamionRes update(UUID id, CamionCreateReq request) {
        Camion camion = camionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Camion no encontrado"));
        camion.setPatente(request.getPatente());
        camion.setDescripcion(request.getDescripcion());
        camion.setCapacidadMaximaPesoKg(request.getCapacidadMaximaPesoKg());
        camion.setCapacidadMaximaVolumenM3(request.getCapacidadMaximaVolumenM3());
        camion.setEstado(request.getEstado());
        camion.setActivo(request.isActivo());
        camion = camionRepository.save(camion);
        return toDto(camion);
    }

    @Override
    @Transactional(readOnly = true)
    public CamionRes getById(UUID id) {
        Camion camion = camionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Camion no encontrado con ID: " + id));
        return toDto(camion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CamionRes> getAll() {
        return camionRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private CamionRes toDto(Camion camion) {
        return new CamionRes(
                camion.getId(),
                camion.getPatente(),
                camion.getDescripcion(),
                camion.getCapacidadMaximaPesoKg(),
                camion.getCapacidadMaximaVolumenM3(),
                camion.getEstado(),
                camion.isActivo()
        );
    }
}
