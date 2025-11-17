package backend.grupo73.solicitudes_svc.presentation.service;

import backend.grupo73.solicitudes_svc.domain.dto.request.SolicitudCreateReq;
import backend.grupo73.solicitudes_svc.domain.dto.response.SeguimientoContenedorRes;
import backend.grupo73.solicitudes_svc.domain.dto.response.SolicitudRes;
import backend.grupo73.solicitudes_svc.domain.dto.response.rutas.CalcularRutaRes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public interface SolicitudServiceI {

    SolicitudRes crearSolicitud(SolicitudCreateReq req);

    SeguimientoContenedorRes obtenerSeguimientoPorContenedor(UUID contenedorId);

    List<SolicitudRes> obtenerSolicitudesPendientes();

    CalcularRutaRes getRutasDisponibles(UUID solicitudId);

    SolicitudRes asignarCamionATramo(UUID solicitudId, UUID tramoId, UUID camionId);

    SolicitudRes registrarTramoReal(UUID solicitudId, UUID tramoId, Double distanciaRealKm, Integer tiempoRealMin);

    SolicitudRes registrarEstadiaDeposito(UUID solicitudId, UUID depositoUbicacionId, Instant fechaHoraEntrada, Instant fechaHoraSalida);

    SolicitudRes calcularCostoReal(UUID solicitudId);

    SolicitudRes finalizarSolicitud(UUID solicitudId);
}
