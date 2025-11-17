package backend.grupo130.tramos.service;

import backend.grupo130.tramos.dto.EstadoTramoRequest;
import backend.grupo130.tramos.dto.EstadoTramoResponse;
import backend.grupo130.tramos.dto.TipoTramoRequest;
import backend.grupo130.tramos.dto.TipoTramoResponse;
import backend.grupo130.tramos.entity.EstadoTramo;
import backend.grupo130.tramos.entity.TipoTramo;
import backend.grupo130.tramos.mapper.EstadoTramoMapper;
import backend.grupo130.tramos.mapper.TipoTramoMapper;
import backend.grupo130.tramos.repository.EstadoTramoRepository;
import backend.grupo130.tramos.repository.TipoTramoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogoTramoService {

    private final EstadoTramoRepository estadoTramoRepository;
    private final TipoTramoRepository tipoTramoRepository;

    public EstadoTramoResponse crearEstado(EstadoTramoRequest request) {
        EstadoTramo estado = new EstadoTramo();
        EstadoTramoMapper.updateEntity(estado, request);
        return EstadoTramoMapper.toResponse(estadoTramoRepository.save(estado));
    }

    public List<EstadoTramoResponse> listarEstados() {
        return estadoTramoRepository.findAll().stream()
            .map(EstadoTramoMapper::toResponse)
            .toList();
    }

    public EstadoTramo obtenerEstado(Long id) {
        return estadoTramoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Estado de tramo no encontrado: " + id));
    }

    public TipoTramoResponse crearTipo(TipoTramoRequest request) {
        TipoTramo tipo = new TipoTramo();
        TipoTramoMapper.updateEntity(tipo, request);
        return TipoTramoMapper.toResponse(tipoTramoRepository.save(tipo));
    }

    public List<TipoTramoResponse> listarTipos() {
        return tipoTramoRepository.findAll().stream()
            .map(TipoTramoMapper::toResponse)
            .toList();
    }

    public TipoTramo obtenerTipo(Long id) {
        return tipoTramoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tipo de tramo no encontrado: " + id));
    }
}
