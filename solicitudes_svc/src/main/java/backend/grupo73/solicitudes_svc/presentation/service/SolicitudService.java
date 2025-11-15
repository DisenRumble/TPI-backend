package backend.grupo73.solicitudes_svc.presentation.service;

import backend.grupo73.solicitudes_svc.domain.dto.request.AsignarRutaReq;
import backend.grupo73.solicitudes_svc.domain.dto.request.SolicitudCreateReq;
import backend.grupo73.solicitudes_svc.domain.dto.response.ClienteRes;
import backend.grupo73.solicitudes_svc.domain.dto.response.SeguimientoContenedorRes;
import backend.grupo73.solicitudes_svc.domain.dto.response.SolicitudRes;
import backend.grupo73.solicitudes_svc.domain.model.*;
import backend.grupo73.solicitudes_svc.domain.repository.ContenedorRepository;
import backend.grupo73.solicitudes_svc.domain.repository.SolicitudRepository;
import backend.grupo73.solicitudes_svc.infrastructure.UsuariosClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class SolicitudService implements SolicitudServiceI {

    private static final Logger log = LoggerFactory.getLogger(SolicitudService.class);

    private final SolicitudRepository solicitudRepository;
    private final ContenedorRepository contenedorRepository;
    private final UsuariosClient usuariosClient;

    @Override
    public SolicitudRes crearSolicitud(SolicitudCreateReq req) {

        // 1. Validar que el contenedor no esté en una solicitud activa
        solicitudRepository.findFirstByContenedorIdentificacionUnicaAndEstadoNot(
                req.identificacionUnicaContenedor(), EstadoSolicitud.ENTREGADA
        ).ifPresent(s -> {
            throw new IllegalStateException("El contenedor ya está en una solicitud activa (ID: " + s.getId() + ")");
        });

        // 2. Obtener el cliente
        UUID clienteId;
        if (req.clienteId() != null) {
            try {
                log.info("Verificando existencia del cliente con ID: {}", req.clienteId());
                ClienteRes cliente = usuariosClient.getClienteById(req.clienteId());
                clienteId = cliente.id();
                log.info("Cliente con ID {} verificado correctamente.", clienteId);
            } catch (RestClientException ex) {
                log.error("Error al llamar a usuarios-svc para verificar el cliente.", ex);
                throw new RuntimeException("Error de comunicación o el cliente especificado no existe.");
            }
        } else {
            ClienteRes cliente = usuariosClient.ensureCurrentCliente();
            clienteId = cliente.id();
        }

        // 3. Crear o reutilizar contenedor
        ContenedorModel cont = contenedorRepository.findByIdentificacionUnica(req.identificacionUnicaContenedor())
                .orElseGet(() -> ContenedorModel.builder()
                        .identificacionUnica(req.identificacionUnicaContenedor())
                        .peso(req.peso())
                        .volumen(req.volumen())
                        .estado("CREADO")
                        .ubicacionActual(req.origen())
                        .build());

        // 4. Crear solicitud
        SolicitudModel solicitud = SolicitudModel.builder()
                .clienteId(clienteId)
                .contenedor(cont)
                .origen(req.origen())
                .destino(req.destino())
                .estado(EstadoSolicitud.BORRADOR)
                .costoEstimado(BigDecimal.ZERO)
                .tiempoEstimadoMinutos(0)
                .build();

        solicitudRepository.save(solicitud);

        return SolicitudRes.from(solicitud);
    }

    @Override
    @Transactional(readOnly = true)
    public SeguimientoContenedorRes obtenerSeguimientoPorContenedor(UUID contenedorId) {
        SolicitudModel solicitud = solicitudRepository.findByContenedorId(contenedorId)
                .orElseThrow(() -> new RuntimeException("No existe solicitud para este contenedor"));

        return SeguimientoContenedorRes.from(solicitud);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SolicitudRes> obtenerSolicitudesPendientes() {
        return solicitudRepository.findByEstadoNot(EstadoSolicitud.ENTREGADA)
                .stream()
                .map(SolicitudRes::from)
                .toList();
    }

    @Override
    public SolicitudRes asignarRuta(UUID solicitudId, AsignarRutaReq req) {
        SolicitudModel solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        solicitud.setRutaId(req.rutaId());
        solicitud.setEstado(EstadoSolicitud.PROGRAMADA);

        // aquí en el futuro vas a llamar a rutas-svc + catalogo-svc
        solicitud.setCostoEstimado(BigDecimal.ZERO);
        solicitud.setTiempoEstimadoMinutos(0);

        return SolicitudRes.from(solicitud);
    }

    @Override
    public SolicitudRes finalizarSolicitud(UUID solicitudId) {
        SolicitudModel solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        solicitud.setEstado(EstadoSolicitud.ENTREGADA);

        // valores reales se calcularán llamando a otros MS después
        solicitud.setCostoReal(solicitud.getCostoEstimado());
        solicitud.setTiempoRealMinutos(solicitud.getTiempoEstimadoMinutos());

        return SolicitudRes.from(solicitud);
    }
}