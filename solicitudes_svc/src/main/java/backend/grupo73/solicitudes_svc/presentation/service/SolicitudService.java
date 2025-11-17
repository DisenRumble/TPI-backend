package backend.grupo73.solicitudes_svc.presentation.service;

import backend.grupo73.solicitudes_svc.domain.dto.request.SolicitudCreateReq;
import backend.grupo73.solicitudes_svc.domain.dto.request.rutas.CalcularRutaReq;
import backend.grupo73.solicitudes_svc.domain.dto.request.rutas.PuntoReq;
import backend.grupo73.solicitudes_svc.domain.dto.response.ClienteRes;
import backend.grupo73.solicitudes_svc.domain.dto.response.SeguimientoContenedorRes;
import backend.grupo73.solicitudes_svc.domain.dto.response.SolicitudRes;
import backend.grupo73.solicitudes_svc.domain.dto.response.catalogo.CamionRes;
import backend.grupo73.solicitudes_svc.domain.dto.response.catalogo.TarifaRes;
import backend.grupo73.solicitudes_svc.domain.dto.response.catalogo.UbicacionRes;
import backend.grupo73.solicitudes_svc.domain.dto.response.rutas.AlternativaRes;
import backend.grupo73.solicitudes_svc.domain.dto.response.rutas.CalcularRutaRes;
import backend.grupo73.solicitudes_svc.domain.model.*;
import backend.grupo73.solicitudes_svc.domain.repository.ContenedorRepository;
import backend.grupo73.solicitudes_svc.domain.repository.EstadiaDepositoRepository;
import backend.grupo73.solicitudes_svc.domain.repository.SolicitudRepository;
import backend.grupo73.solicitudes_svc.domain.repository.TramoEjecucionRepository;
import backend.grupo73.solicitudes_svc.infrastructure.CatalogoClient;
import backend.grupo73.solicitudes_svc.infrastructure.RutasClient;
import backend.grupo73.solicitudes_svc.infrastructure.UsuariosClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SolicitudService implements SolicitudServiceI {

    private static final Logger log = LoggerFactory.getLogger(SolicitudService.class);

    private final SolicitudRepository solicitudRepository;
    private final ContenedorRepository contenedorRepository;
    private final TramoEjecucionRepository tramoEjecucionRepository;
    private final EstadiaDepositoRepository estadiaDepositoRepository;
    private final UsuariosClient usuariosClient;
    private final RutasClient rutasClient;
    private final CatalogoClient catalogoClient;

    @Override
    public SolicitudRes crearSolicitud(SolicitudCreateReq req) {

        solicitudRepository.findFirstByContenedorIdentificacionUnicaAndEstadoNot(
                req.identificacionUnicaContenedor(), EstadoSolicitud.ENTREGADA
        ).ifPresent(s -> {
            throw new IllegalStateException("El contenedor ya está en una solicitud activa (ID: " + s.getId() + ")");
        });

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

        UbicacionRes origenUbicacion = catalogoClient.getUbicacionById(req.origenUbicacionId());
        validateUbicacion(origenUbicacion, req.origenUbicacionId());

        UbicacionRes destinoUbicacion = catalogoClient.getUbicacionById(req.destinoUbicacionId());
        validateUbicacion(destinoUbicacion, req.destinoUbicacionId());

        List<UbicacionRes> depositosIntermedios = new ArrayList<>();
        if (req.depositosIntermediosUbicacionIds() != null && !req.depositosIntermediosUbicacionIds().isEmpty()) {
            for (UUID depositoId : req.depositosIntermediosUbicacionIds()) {
                UbicacionRes deposito = catalogoClient.getUbicacionById(depositoId);
                validateUbicacion(deposito, depositoId);
                if (!"DEPOSITO".equals(deposito.getTipo())) {
                    throw new IllegalArgumentException("La ubicación con ID " + depositoId + " no es un depósito.");
                }
                depositosIntermedios.add(deposito);
            }
        }

        ContenedorModel cont = contenedorRepository.findByIdentificacionUnica(req.identificacionUnicaContenedor())
                .orElseGet(() -> ContenedorModel.builder()
                        .identificacionUnica(req.identificacionUnicaContenedor())
                        .peso(req.peso())
                        .volumen(req.volumen())
                        .estado("CREADO")
                        .ubicacionActual(origenUbicacion.getNombre())
                        .build());

        SolicitudModel solicitud = SolicitudModel.builder()
                .clienteId(clienteId)
                .contenedor(cont)
                .origenUbicacionId(req.origenUbicacionId())
                .destinoUbicacionId(req.destinoUbicacionId())
                .depositosIntermediosUbicacionIds(req.depositosIntermediosUbicacionIds())
                .origenLat(origenUbicacion.getLat())
                .origenLng(origenUbicacion.getLng())
                .origenDescripcion(origenUbicacion.getNombre())
                .destinoLat(destinoUbicacion.getLat())
                .destinoLng(destinoUbicacion.getLng())
                .destinoDescripcion(destinoUbicacion.getNombre())
                .estado(EstadoSolicitud.BORRADOR)
                .costoEstimado(BigDecimal.ZERO)
                .tiempoEstimadoMinutos(0)
                .build();

        solicitudRepository.save(solicitud);

        List<UbicacionRes> puntosCompletos = new ArrayList<>();
        puntosCompletos.add(origenUbicacion);
        puntosCompletos.addAll(depositosIntermedios);
        puntosCompletos.add(destinoUbicacion);

        CalcularRutaRes rutasCalculadas = getRutasAlternativas(solicitud, puntosCompletos);
        AlternativaRes alternativaPrincipal = rutasCalculadas.getAlternativas().stream()
                .filter(AlternativaRes::isEsPrincipal)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró una alternativa principal para la ruta."));

        TarifaRes tarifas = catalogoClient.getTarifas();

        BigDecimal costoEstimadoTotal = BigDecimal.ZERO;
        Integer tiempoEstimadoTotal = 0;
        List<TramoEjecucionModel> tramosEjecucion = new ArrayList<>();
        AtomicInteger orden = new AtomicInteger(0);

        Map<String, UUID> latLngToUbicacionIdMap = new HashMap<>();
        for (UbicacionRes ub : puntosCompletos) {
            latLngToUbicacionIdMap.put(ub.getLat() + "," + ub.getLng(), ub.getId());
        }

        for (backend.grupo73.solicitudes_svc.domain.dto.response.rutas.TramoRes tramoRes : alternativaPrincipal.getTramos()) {
            TramoEjecucionModel tramoEjecucion = new TramoEjecucionModel();

            tramoEjecucion.setSolicitud(solicitud);
            tramoEjecucion.setOrden(orden.incrementAndGet());

            UUID origenId = latLngToUbicacionIdMap.get(tramoRes.getOrigen().getLat() + "," + tramoRes.getOrigen().getLng());
            UUID destinoId = latLngToUbicacionIdMap.get(tramoRes.getDestino().getLat() + "," + tramoRes.getDestino().getLng());

            if (origenId == null || destinoId == null) {
                throw new RuntimeException("No se pudo mapear lat/lng de la respuesta de rutas-svc a ubicacionId. Verifique que las coordenadas de la respuesta coincidan con las enviadas.");
            }

            tramoEjecucion.setOrigenUbicacionId(origenId);
            tramoEjecucion.setDestinoUbicacionId(destinoId);
            tramoEjecucion.setDistanciaEstimadaKm(tramoRes.getDistanciaKm());
            tramoEjecucion.setTiempoEstimadoMin(tramoRes.getDuracionMinutos());

            BigDecimal costoDistanciaTramo = BigDecimal.valueOf(tramoRes.getDistanciaKm()).multiply(tarifas.getTarifaPorKm());
            costoEstimadoTotal = costoEstimadoTotal.add(costoDistanciaTramo);
            tiempoEstimadoTotal += tramoRes.getDuracionMinutos();

            tramosEjecucion.add(tramoEjecucion);
        }

        solicitud.setCostoEstimado(costoEstimadoTotal);
        solicitud.setTiempoEstimadoMinutos(tiempoEstimadoTotal);
        solicitud.setTramosEjecucion(tramosEjecucion);

        solicitudRepository.save(solicitud);

        return SolicitudRes.from(solicitud);
    }

    @Override
    @Transactional(readOnly = true)
    public SeguimientoContenedorRes obtenerSeguimientoPorContenedor(UUID contenedorId) {
        // 1. Intentar encontrar una solicitud en estado EN_TRANSITO
        Optional<SolicitudModel> solicitudEnTransitoOpt = solicitudRepository.findByContenedorIdAndEstado(contenedorId, EstadoSolicitud.EN_TRANSITO);

        if (solicitudEnTransitoOpt.isPresent()) {
            return SeguimientoContenedorRes.from(solicitudEnTransitoOpt.get());
        }

        // 2. Si no hay EN_TRANSITO, buscar otras solicitudes para dar un mensaje más específico
        List<SolicitudModel> solicitudesDelContenedor = solicitudRepository.findByContenedorId(contenedorId);

        if (solicitudesDelContenedor.isEmpty()) {
            throw new RuntimeException("No existe solicitud para este contenedor.");
        }

        // Verificar si hay alguna solicitud FINALIZADA
        boolean hayFinalizada = solicitudesDelContenedor.stream()
                .anyMatch(s -> s.getEstado() == EstadoSolicitud.ENTREGADA);
        if (hayFinalizada) {
            throw new RuntimeException("La solicitud para este contenedor ya ha sido finalizada.");
        }

        // Verificar si hay alguna solicitud en BORRADOR o PROGRAMADA (o cualquier otra que no sea EN_TRANSITO)
        boolean hayPendiente = solicitudesDelContenedor.stream()
                .anyMatch(s -> s.getEstado() == EstadoSolicitud.BORRADOR || s.getEstado() == EstadoSolicitud.PROGRAMADA);
        if (hayPendiente) {
            throw new RuntimeException("La solicitud para este contenedor aún no está en tránsito.");
        }

        // Caso por si hay algún otro estado no contemplado
        throw new RuntimeException("No se pudo obtener el seguimiento para este contenedor en su estado actual.");
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
    public SolicitudRes asignarCamionATramo(UUID solicitudId, UUID tramoId, UUID camionId) {
        SolicitudModel solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        TramoEjecucionModel tramo = solicitud.getTramosEjecucion().stream()
                .filter(t -> t.getId().equals(tramoId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Tramo de ejecución no encontrado para la solicitud."));

        CamionRes camion = catalogoClient.getCamionById(camionId);
        if (!"ACTIVO".equals(camion.getEstado()) || !camion.getActivo()) {
            throw new IllegalArgumentException("El camión no está activo o disponible.");
        }
        if (camion.getCapacidadMaximaPesoKg().compareTo(solicitud.getContenedor().getPeso()) < 0) {
            throw new IllegalArgumentException("El camión no tiene capacidad de peso suficiente.");
        }
        if (camion.getCapacidadMaximaVolumenM3().compareTo(solicitud.getContenedor().getVolumen()) < 0) {
            throw new IllegalArgumentException("El camión no tiene capacidad de volumen suficiente.");
        }

        tramo.setCamionId(camionId);
        tramoEjecucionRepository.save(tramo);

        return SolicitudRes.from(solicitud);
    }

    @Override
    public SolicitudRes registrarTramoReal(UUID solicitudId, UUID tramoId, Double distanciaRealKm, Integer tiempoRealMin) {
        SolicitudModel solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        TramoEjecucionModel tramo = solicitud.getTramosEjecucion().stream()
                .filter(t -> t.getId().equals(tramoId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Tramo de ejecución no encontrado para la solicitud."));

        Instant fechaHoraInicioReal = Instant.now();
        Instant fechaHoraFinReal = fechaHoraInicioReal.plus(Duration.ofMinutes(tiempoRealMin));

        tramo.setDistanciaRealKm(distanciaRealKm);
        tramo.setTiempoRealMin(tiempoRealMin);
        tramo.setFechaHoraInicioReal(fechaHoraInicioReal);
        tramo.setFechaHoraFinReal(fechaHoraFinReal);
        tramoEjecucionRepository.save(tramo);

        boolean todosTramosFinalizados = solicitud.getTramosEjecucion().stream()
                .allMatch(t -> t.getDistanciaRealKm() != null && t.getTiempoRealMin() != null);

        if (todosTramosFinalizados) {
            solicitud.setEstado(EstadoSolicitud.EN_TRANSITO);
            solicitudRepository.save(solicitud);
        }

        return SolicitudRes.from(solicitud);
    }

    @Override
    public SolicitudRes registrarEstadiaDeposito(UUID solicitudId, UUID depositoUbicacionId, Instant fechaHoraEntrada, Instant fechaHoraSalida) {
        SolicitudModel solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        UbicacionRes deposito = catalogoClient.getUbicacionById(depositoUbicacionId);
        validateUbicacion(deposito, depositoUbicacionId);
        if (!"DEPOSITO".equals(deposito.getTipo())) {
            throw new IllegalArgumentException("La ubicación con ID " + depositoUbicacionId + " no es un depósito.");
        }

        EstadiaDepositoModel estadia = new EstadiaDepositoModel();
        estadia.setSolicitud(solicitud);
        estadia.setDepositoUbicacionId(depositoUbicacionId);
        estadia.setFechaHoraEntrada(fechaHoraEntrada);
        estadia.setFechaHoraSalida(fechaHoraSalida);

        if (fechaHoraEntrada != null && fechaHoraSalida != null) {
            Duration duration = Duration.between(fechaHoraEntrada, fechaHoraSalida);
            estadia.setHorasEstadia(duration.toHours());
        }

        solicitud.getEstadiasDeposito().add(estadia);
        solicitudRepository.save(solicitud);

        return SolicitudRes.from(solicitud);
    }

    @Override
    public SolicitudRes calcularCostoReal(UUID solicitudId) {
        SolicitudModel solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        TarifaRes tarifas = catalogoClient.getTarifas();
        BigDecimal costoRealTotal = BigDecimal.ZERO;
        Integer tiempoRealTotalMinutos = 0;

        for (TramoEjecucionModel tramo : solicitud.getTramosEjecucion()) {
            if (tramo.getDistanciaRealKm() == null || tramo.getTiempoRealMin() == null) {
                throw new IllegalStateException("No todos los tramos tienen datos reales registrados para calcular el costo real.");
            }

            BigDecimal costoDistanciaTramo = BigDecimal.valueOf(tramo.getDistanciaRealKm()).multiply(tarifas.getTarifaPorKm());
            costoRealTotal = costoRealTotal.add(costoDistanciaTramo);
            tiempoRealTotalMinutos += tramo.getTiempoRealMin();
        }

        for (EstadiaDepositoModel estadia : solicitud.getEstadiasDeposito()) {
            if (estadia.getHorasEstadia() == null) {
                throw new IllegalStateException("No todas las estadías tienen el tiempo calculado para el costo real.");
            }
            BigDecimal costoEstadia = BigDecimal.valueOf(estadia.getHorasEstadia()).multiply(tarifas.getTarifaEstadiaPorHora());
            costoRealTotal = costoRealTotal.add(costoEstadia);
        }

        solicitud.setCostoReal(costoRealTotal);
        solicitud.setTiempoRealMinutos(tiempoRealTotalMinutos);
        solicitudRepository.save(solicitud);

        return SolicitudRes.from(solicitud);
    }

    @Override
    public SolicitudRes finalizarSolicitud(UUID solicitudId) {
        SolicitudModel solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        calcularCostoReal(solicitudId);

        solicitud.setEstado(EstadoSolicitud.ENTREGADA);

        ContenedorModel contenedor = solicitud.getContenedor();
        if (contenedor != null) {
            contenedor.setUbicacionActual(solicitud.getDestinoDescripcion());
            contenedorRepository.save(contenedor);
        }

        solicitudRepository.save(solicitud);

        return SolicitudRes.from(solicitud);
    }

    @Override
    @Transactional(readOnly = true)
    public CalcularRutaRes getRutasDisponibles(UUID solicitudId) {
        SolicitudModel solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        List<UbicacionRes> puntosCompletos = new ArrayList<>();
        UbicacionRes origenUbicacion = catalogoClient.getUbicacionById(solicitud.getOrigenUbicacionId());
        validateUbicacion(origenUbicacion, solicitud.getOrigenUbicacionId());
        puntosCompletos.add(origenUbicacion);

        if (solicitud.getDepositosIntermediosUbicacionIds() != null) {
            for (UUID depositoId : solicitud.getDepositosIntermediosUbicacionIds()) {
                UbicacionRes deposito = catalogoClient.getUbicacionById(depositoId);
                validateUbicacion(deposito, depositoId);
                puntosCompletos.add(deposito);
            }
        }
        UbicacionRes destinoUbicacion = catalogoClient.getUbicacionById(solicitud.getDestinoUbicacionId());
        validateUbicacion(destinoUbicacion, solicitud.getDestinoUbicacionId());
        puntosCompletos.add(destinoUbicacion);

        return getRutasAlternativas(solicitud, puntosCompletos);
    }

    private CalcularRutaRes getRutasAlternativas(SolicitudModel solicitud, List<UbicacionRes> puntosCompletos) {
        List<PuntoReq> puntosParaRutasSvc = new ArrayList<>();

        Map<String, UUID> latLngToUbicacionIdMap = new HashMap<>();

        for (UbicacionRes ub : puntosCompletos) {
            log.debug("Construyendo PuntoReq para rutas-svc: UbicacionId={}, Tipo={}, Descripcion={}, Lat={}, Lng={}",
                    ub.getId(), ub.getTipo(), ub.getNombre(), ub.getLat(), ub.getLng());
            puntosParaRutasSvc.add(new PuntoReq(ub.getTipo(), ub.getNombre(), ub.getLat(), ub.getLng()));
            latLngToUbicacionIdMap.put(ub.getLat() + "," + ub.getLng(), ub.getId());
        }

        CalcularRutaReq calcularRutaReq = new CalcularRutaReq(puntosParaRutasSvc);
        log.debug("Enviando CalcularRutaReq a rutas-svc: {}", calcularRutaReq);

        CalcularRutaRes rutasCalculadas = rutasClient.calcularRuta(calcularRutaReq);

        return rutasCalculadas;
    }

    private void validateUbicacion(UbicacionRes ubicacion, UUID ubicacionId) {
        if (ubicacion == null) {
            throw new RuntimeException("Ubicación con ID " + ubicacionId + " no encontrada en el catálogo.");
        }
        if (ubicacion.getLat() == null || ubicacion.getLng() == null) {
            throw new RuntimeException("La ubicación con ID " + ubicacionId + " no tiene coordenadas (lat/lng) definidas en el catálogo. Lat: " + ubicacion.getLat() + ", Lng: " + ubicacion.getLng());
        }
        if (ubicacion.getTipo() == null || ubicacion.getTipo().isEmpty()) {
            throw new RuntimeException("La ubicación con ID " + ubicacionId + " no tiene un tipo definido en el catálogo.");
        }
    }
}