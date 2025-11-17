package backend.grupo73.catalogo.domain.service;

import backend.grupo73.catalogo.domain.dto.request.UbicacionCreateReq;
import backend.grupo73.catalogo.domain.dto.response.UbicacionRes;
import backend.grupo73.catalogo.domain.entity.Ubicacion;
import backend.grupo73.catalogo.domain.repository.UbicacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UbicacionService implements UbicacionServiceI {

    private final UbicacionRepository ubicacionRepository;

    @Override
    @Transactional
    public UbicacionRes create(UbicacionCreateReq request) {
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setNombre(request.getNombre());
        ubicacion.setDescripcion(request.getDescripcion());
        ubicacion.setLat(request.getLat());
        ubicacion.setLng(request.getLng());
        ubicacion.setTipo(request.getTipo());
        ubicacion = ubicacionRepository.save(ubicacion);
        return toDto(ubicacion);
    }

    @Override
    @Transactional
    public UbicacionRes update(UUID id, UbicacionCreateReq request) {
        Ubicacion ubicacion = ubicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ubicacion no encontrada"));
        ubicacion.setNombre(request.getNombre());
        ubicacion.setDescripcion(request.getDescripcion());
        ubicacion.setLat(request.getLat());
        ubicacion.setLng(request.getLng());
        ubicacion.setTipo(request.getTipo());
        ubicacion = ubicacionRepository.save(ubicacion);
        return toDto(ubicacion);
    }

    @Override
    @Transactional(readOnly = true)
    public UbicacionRes getById(UUID id) {
        Ubicacion ubicacion = ubicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ubicacion no encontrada"));
        return toDto(ubicacion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UbicacionRes> search(String query) {
        List<Ubicacion> ubicaciones = ubicacionRepository.findByNombreContainingIgnoreCase(query);
        return ubicaciones.stream().map(this::toDto).collect(Collectors.toList());
    }

    private UbicacionRes toDto(Ubicacion ubicacion) {
        return new UbicacionRes(
                ubicacion.getId(),
                ubicacion.getNombre(),
                ubicacion.getDescripcion(),
                ubicacion.getLat(),
                ubicacion.getLng(),
                ubicacion.getTipo(),
                ubicacion.isActivo()
        );
    }
}
