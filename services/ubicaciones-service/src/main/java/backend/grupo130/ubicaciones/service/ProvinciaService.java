package backend.grupo130.ubicaciones.service;

import backend.grupo130.ubicaciones.dto.ProvinciaRequest;
import backend.grupo130.ubicaciones.dto.ProvinciaResponse;
import backend.grupo130.ubicaciones.entity.Provincia;
import backend.grupo130.ubicaciones.mapper.ProvinciaMapper;
import backend.grupo130.ubicaciones.repository.ProvinciaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProvinciaService {

    private final ProvinciaRepository provinciaRepository;

    public ProvinciaResponse crear(ProvinciaRequest request) {
        Provincia provincia = new Provincia();
        ProvinciaMapper.updateEntity(provincia, request);
        return ProvinciaMapper.toResponse(provinciaRepository.save(provincia));
    }

    public List<ProvinciaResponse> listar() {
        return provinciaRepository.findAll().stream()
            .map(ProvinciaMapper::toResponse)
            .toList();
    }

    public Provincia obtenerEntidad(Long id) {
        return provinciaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Provincia no encontrada: " + id));
    }
}
