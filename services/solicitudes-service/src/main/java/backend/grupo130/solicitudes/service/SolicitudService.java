package backend.grupo130.solicitudes.service;

import backend.grupo130.solicitudes.dto.ClienteRequest;
import backend.grupo130.solicitudes.dto.SeguimientoResponse;
import backend.grupo130.solicitudes.dto.SolicitudEstadoUpdateRequest;
import backend.grupo130.solicitudes.dto.SolicitudRequest;
import backend.grupo130.solicitudes.dto.SolicitudResponse;
import backend.grupo130.solicitudes.entity.Cliente;
import backend.grupo130.solicitudes.entity.EstadoSolicitud;
import backend.grupo130.solicitudes.entity.Solicitud;
import backend.grupo130.solicitudes.entity.Tarifa;
import backend.grupo130.solicitudes.mapper.SolicitudMapper;
import backend.grupo130.solicitudes.repository.SolicitudRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SolicitudService {

    private final SolicitudRepository solicitudRepository;
    private final ClienteService clienteService;
    private final TarifaService tarifaService;

    @Transactional
    public SolicitudResponse crearSolicitud(SolicitudRequest request) {
        Cliente cliente = clienteService.resolverCliente(request.clienteId(), request.clienteNuevo());
        Tarifa tarifa = tarifaService.obtenerEntidad(request.tarifaId());

        Solicitud solicitud = Solicitud.builder()
            .cliente(cliente)
            .tarifa(tarifa)
            .contenedorId(request.contenedorId())
            .rutaId(request.rutaId())
            .cantidadTramos(request.cantidadTramos())
            .cantidadDepositos(request.cantidadDepositos())
            .costoEstimado(request.costoEstimado())
            .tiempoEstimadoHoras(request.tiempoEstimadoHoras())
            .estado(EstadoSolicitud.BORRADOR)
            .build();

        return SolicitudMapper.toResponse(solicitudRepository.save(solicitud));
    }

    public List<SolicitudResponse> listar(EstadoSolicitud estado) {
        List<Solicitud> solicitudes = estado == null
            ? solicitudRepository.findAll()
            : solicitudRepository.findByEstado(estado);
        return solicitudes.stream()
            .map(SolicitudMapper::toResponse)
            .toList();
    }

    public SolicitudResponse obtenerPorId(UUID id) {
        return SolicitudMapper.toResponse(buscarPorId(id));
    }

    @Transactional
    public SolicitudResponse actualizarEstado(UUID id, SolicitudEstadoUpdateRequest request) {
        Solicitud solicitud = buscarPorId(id);
        solicitud.setEstado(request.estado());
        if (request.costoFinal() != null) {
            solicitud.setCostoFinal(request.costoFinal());
        }
        if (request.tiempoRealHoras() != null) {
            solicitud.setTiempoRealHoras(request.tiempoRealHoras());
        }
        return SolicitudMapper.toResponse(solicitudRepository.save(solicitud));
    }

    public SeguimientoResponse obtenerSeguimientoPorContenedor(UUID contenedorId) {
        Solicitud solicitud = solicitudRepository.findByContenedorId(contenedorId)
            .orElseThrow(() -> new EntityNotFoundException("No hay solicitudes para el contenedor " + contenedorId));
        return new SeguimientoResponse(
            solicitud.getId(),
            solicitud.getContenedorId(),
            solicitud.getEstado(),
            solicitud.getFechaActualizacion(),
            solicitud.getTiempoEstimadoHoras(),
            solicitud.getTiempoRealHoras(),
            buildDescripcionSeguimiento(solicitud)
        );
    }

    private String buildDescripcionSeguimiento(Solicitud solicitud) {
        return switch (solicitud.getEstado()) {
            case BORRADOR -> "Solicitud registrada y esperando programación de ruta.";
            case PROGRAMADA -> "Ruta asignada. Pendiente de retiro.";
            case EN_TRANSITO -> "Contenedor en traslado activo.";
            case EN_DEPOSITO -> "Contenedor detenido temporalmente en depósito intermedio.";
            case ENTREGADA -> "Traslado registrado como entregado.";
            case CANCELADA -> "Solicitud cancelada por operador.";
        };
    }

    private Solicitud buscarPorId(UUID id) {
        return solicitudRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Solicitud no encontrada: " + id));
    }
}
