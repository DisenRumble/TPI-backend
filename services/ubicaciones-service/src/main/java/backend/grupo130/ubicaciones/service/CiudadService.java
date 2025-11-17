package backend.grupo130.ubicaciones.service;

import backend.grupo130.ubicaciones.dto.CiudadRequest;
import backend.grupo130.ubicaciones.dto.CiudadResponse;
import backend.grupo130.ubicaciones.entity.Ciudad;
import backend.grupo130.ubicaciones.entity.Provincia;
import backend.grupo130.ubicaciones.mapper.CiudadMapper;
import backend.grupo130.ubicaciones.repository.CiudadRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CiudadService {

    private final CiudadRepository ciudadRepository;
    private final ProvinciaService provinciaService;

    public CiudadResponse crear(CiudadRequest request) {
        Provincia provincia = provinciaService.obtenerEntidad(request.provinciaId());
        Ciudad ciudad = new Ciudad();
        ciudad.setNombre(request.nombre());
        ciudad.setProvincia(provincia);
        return CiudadMapper.toResponse(ciudadRepository.save(ciudad));
    }

    public List<CiudadResponse> listarPorProvincia(Long provinciaId) {
        Provincia provincia = provinciaService.obtenerEntidad(provinciaId);
        return ciudadRepository.findByProvincia(provincia).stream()
            .map(CiudadMapper::toResponse)
            .toList();
    }

    public Ciudad obtenerEntidad(Long id) {
        return ciudadRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Ciudad no encontrada: " + id));
    }
}
