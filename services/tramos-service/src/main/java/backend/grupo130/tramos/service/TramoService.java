package backend.grupo130.tramos.service;

import backend.grupo130.tramos.dto.ActualizarTramoEstadoRequest;
import backend.grupo130.tramos.dto.TramoResponse;
import backend.grupo130.tramos.entity.Tramo;
import backend.grupo130.tramos.mapper.TramoMapper;
import backend.grupo130.tramos.repository.TramoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TramoService {

    private final TramoRepository tramoRepository;
    private final RutaService rutaService;
    private final CatalogoTramoService catalogoTramoService;

    public List<TramoResponse> listarPorRuta(UUID rutaId) {
        var ruta = rutaService.obtenerEntidad(rutaId);
        return tramoRepository.findByRutaOrderByOrden(ruta).stream()
            .map(TramoMapper::toResponse)
            .toList();
    }

    @Transactional
    public TramoResponse actualizar(UUID tramoId, ActualizarTramoEstadoRequest request) {
        Tramo tramo = buscarPorId(tramoId);
        tramo.setEstadoTramo(catalogoTramoService.obtenerEstado(request.estadoTramoId()));
        tramo.setFechaRealInicio(request.fechaRealInicio());
        tramo.setFechaRealFin(request.fechaRealFin());
        tramo.setTiempoRealHoras(request.tiempoRealHoras());
        tramo.setCostoReal(request.costoReal());
        if (request.patenteCamion() != null) {
            tramo.setPatenteCamion(request.patenteCamion());
        }
        return TramoMapper.toResponse(tramoRepository.save(tramo));
    }

    private Tramo buscarPorId(UUID id) {
        return tramoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Tramo no encontrado: " + id));
    }
}
