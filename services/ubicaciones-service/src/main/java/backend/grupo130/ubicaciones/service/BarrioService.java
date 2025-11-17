package backend.grupo130.ubicaciones.service;

import backend.grupo130.ubicaciones.dto.BarrioRequest;
import backend.grupo130.ubicaciones.dto.BarrioResponse;
import backend.grupo130.ubicaciones.entity.Barrio;
import backend.grupo130.ubicaciones.entity.Ciudad;
import backend.grupo130.ubicaciones.mapper.BarrioMapper;
import backend.grupo130.ubicaciones.repository.BarrioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BarrioService {

    private final BarrioRepository barrioRepository;
    private final CiudadService ciudadService;

    public BarrioResponse crear(BarrioRequest request) {
        Ciudad ciudad = ciudadService.obtenerEntidad(request.ciudadId());
        Barrio barrio = new Barrio();
        barrio.setNombre(request.nombre());
        barrio.setCiudad(ciudad);
        return BarrioMapper.toResponse(barrioRepository.save(barrio));
    }

    public List<BarrioResponse> listarPorCiudad(Long ciudadId) {
        Ciudad ciudad = ciudadService.obtenerEntidad(ciudadId);
        return barrioRepository.findByCiudad(ciudad).stream()
            .map(BarrioMapper::toResponse)
            .toList();
    }

    public Barrio obtenerEntidad(Long id) {
        return barrioRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Barrio no encontrado: " + id));
    }
}
