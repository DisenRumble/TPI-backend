package backend.grupo73.camiones.service;

import backend.grupo73.camiones.dto.CamionDTO;
import backend.grupo73.camiones.dto.CapacidadValidationRequest;
import backend.grupo73.camiones.dto.CreateCamionDTO;
import backend.grupo73.camiones.model.Camion;
import backend.grupo73.camiones.repository.CamionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CamionService {

    private final CamionRepository camionRepository;

    @Transactional(readOnly = true)
    public Optional<CamionDTO> getCamionById(Long id) {
        return camionRepository.findById(id).map(this::convertToDto);
    }

    @Transactional
    public CamionDTO createCamion(CreateCamionDTO createCamionDTO) {
        Camion camion = new Camion();
        camion.setDominio(createCamionDTO.getDominio());
        camion.setNombreTransportista(createCamionDTO.getNombreTransportista());
        camion.setTelefono(createCamionDTO.getTelefono());
        camion.setCapPeso(createCamionDTO.getCapPeso());
        camion.setCapVolumen(createCamionDTO.getCapVolumen());
        camion.setCostoKmBase(createCamionDTO.getCostoKmBase());
        camion.setConsumoLK_m(createCamionDTO.getConsumoLK_m());
        camion.setActivo(true);
        
        Camion savedCamion = camionRepository.save(camion);
        return convertToDto(savedCamion);
    }

    @Transactional
    public Optional<CamionDTO> updateCamion(Long id, CreateCamionDTO createCamionDTO) {
        return camionRepository.findById(id).map(camion -> {
            camion.setDominio(createCamionDTO.getDominio());
            camion.setNombreTransportista(createCamionDTO.getNombreTransportista());
            camion.setTelefono(createCamionDTO.getTelefono());
            camion.setCapPeso(createCamionDTO.getCapPeso());
            camion.setCapVolumen(createCamionDTO.getCapVolumen());
            camion.setCostoKmBase(createCamionDTO.getCostoKmBase());
            camion.setConsumoLK_m(createCamionDTO.getConsumoLK_m());
            Camion updatedCamion = camionRepository.save(camion);
            return convertToDto(updatedCamion);
        });
    }

    @Transactional(readOnly = true)
    public boolean validarCapacidad(CapacidadValidationRequest request) {
        return camionRepository.findById(request.getCamionId())
                .map(camion -> camion.getCapPeso() >= request.getPeso() && camion.getCapVolumen() >= request.getVolumen())
                .orElse(false);
    }

    private CamionDTO convertToDto(Camion camion) {
        CamionDTO dto = new CamionDTO();
        dto.setId(camion.getId());
        dto.setDominio(camion.getDominio());
        dto.setNombreTransportista(camion.getNombreTransportista());
        dto.setTelefono(camion.getTelefono());
        dto.setCapPeso(camion.getCapPeso());
        dto.setCapVolumen(camion.getCapVolumen());
        dto.setCostoKmBase(camion.getCostoKmBase());
        dto.setConsumoLK_m(camion.getConsumoLK_m());
        dto.setActivo(camion.isActivo());
        dto.setCreatedAt(camion.getCreatedAt());
        dto.setUpdatedAt(camion.getUpdatedAt());
        return dto;
    }
}