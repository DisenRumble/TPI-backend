package backend.grupo73.solicitudes_svc.presentation.service;

import backend.grupo73.solicitudes_svc.domain.dto.request.AsignarRutaReq;
import backend.grupo73.solicitudes_svc.domain.dto.request.SolicitudCreateReq;
import backend.grupo73.solicitudes_svc.domain.dto.response.SeguimientoContenedorRes;
import backend.grupo73.solicitudes_svc.domain.dto.response.SolicitudRes;

import java.util.List;
import java.util.UUID;

public interface SolicitudServiceI {

    SolicitudRes crearSolicitud(SolicitudCreateReq req);

    SeguimientoContenedorRes obtenerSeguimientoPorContenedor(UUID contenedorId);

    List<SolicitudRes> obtenerSolicitudesPendientes();

    SolicitudRes asignarRuta(UUID solicitudId, AsignarRutaReq req);

    SolicitudRes finalizarSolicitud(UUID solicitudId);
}