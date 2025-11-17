package backend.grupo130.ubicaciones.service;

import backend.grupo130.ubicaciones.dto.UbicacionRequest;
import backend.grupo130.ubicaciones.dto.UbicacionResponse;
import backend.grupo130.ubicaciones.entity.Barrio;
import backend.grupo130.ubicaciones.entity.Ubicacion;
import backend.grupo130.ubicaciones.mapper.UbicacionMapper;
import backend.grupo130.ubicaciones.repository.UbicacionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UbicacionService {

    private final UbicacionRepository ubicacionRepository;
    private final BarrioService barrioService;

    public UbicacionResponse registrar(UbicacionRequest request) {
        Barrio barrio = request.barrioId() != null ? barrioService.obtenerEntidad(request.barrioId()) : null;
        Ubicacion ubicacion = Ubicacion.builder()
            .calle(request.calle())
            .numero(request.numero())
            .barrio(barrio)
            .latitud(request.latitud())
            .longitud(request.longitud())
            .referencias(request.referencias())
            .build();
        return UbicacionMapper.toResponse(ubicacionRepository.save(ubicacion));
    }

    public List<UbicacionResponse> listar() {
        return ubicacionRepository.findAll().stream()
            .map(UbicacionMapper::toResponse)
            .toList();
    }

    public UbicacionResponse obtener(UUID id) {
        return UbicacionMapper.toResponse(obtenerEntidad(id));
    }

    public Ubicacion obtenerEntidad(UUID id) {
        return ubicacionRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Ubicación no encontrada: " + id));
    }
}
