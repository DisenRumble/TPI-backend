package backend.grupo130.tramos.service;

import backend.grupo130.tramos.dto.RutaRequest;
import backend.grupo130.tramos.dto.RutaResponse;
import backend.grupo130.tramos.dto.TramoRequest;
import backend.grupo130.tramos.entity.Ruta;
import backend.grupo130.tramos.entity.Tramo;
import backend.grupo130.tramos.mapper.RutaMapper;
import backend.grupo130.tramos.repository.RutaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RutaService {

    private final RutaRepository rutaRepository;
    private final CatalogoTramoService catalogoTramoService;

    @Transactional
    public RutaResponse crearRuta(RutaRequest request) {
        Ruta ruta = Ruta.builder()
            .solicitudId(request.solicitudId())
            .cantidadDepositos(request.cantidadDepositos())
            .tiempoEstimadoHoras(request.tiempoEstimadoHoras())
            .costoEstimado(request.costoEstimado())
            .build();

        request.tramos().forEach(tramoRequest -> ruta.getTramos().add(crearTramo(ruta, tramoRequest)));

        ruta.setCantidadTramos(ruta.getTramos().size());
        double distanciaTotal = ruta.getTramos().stream()
            .mapToDouble(tramo -> tramo.getDistanciaKm() != null ? tramo.getDistanciaKm() : 0)
            .sum();
        ruta.setDistanciaTotalKm(distanciaTotal);

        BigDecimal costoTotal = ruta.getTramos().stream()
            .map(tramo -> tramo.getCostoEstimado() != null ? tramo.getCostoEstimado() : BigDecimal.ZERO)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (costoTotal.signum() > 0) {
            ruta.setCostoEstimado(costoTotal);
        }

        return RutaMapper.toResponse(rutaRepository.save(ruta));
    }

    public RutaResponse obtenerRuta(UUID id) {
        return RutaMapper.toResponse(buscarPorId(id));
    }

    public RutaResponse obtenerPorSolicitud(UUID solicitudId) {
        Ruta ruta = rutaRepository.findBySolicitudId(solicitudId)
            .orElseThrow(() -> new EntityNotFoundException("No se encuentra ruta para la solicitud " + solicitudId));
        return RutaMapper.toResponse(ruta);
    }

    public Ruta obtenerEntidad(UUID id) {
        return buscarPorId(id);
    }

    private Ruta buscarPorId(UUID id) {
        return rutaRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Ruta no encontrada: " + id));
    }

    private Tramo crearTramo(Ruta ruta, TramoRequest request) {
        return Tramo.builder()
            .ruta(ruta)
            .orden(request.orden())
            .ubicacionOrigenId(request.ubicacionOrigenId())
            .ubicacionDestinoId(request.ubicacionDestinoId())
            .depositoId(request.depositoId())
            .tipoTramo(catalogoTramoService.obtenerTipo(request.tipoTramoId()))
            .estadoTramo(catalogoTramoService.obtenerEstado(request.estadoTramoId()))
            .patenteCamion(request.patenteCamion())
            .distanciaKm(request.distanciaKm())
            .tiempoEstimadoHoras(request.tiempoEstimadoHoras())
            .fechaEstimadaInicio(request.fechaEstimadaInicio())
            .fechaEstimadaFin(request.fechaEstimadaFin())
            .costoEstimado(request.costoEstimado())
            .build();
    }
}
