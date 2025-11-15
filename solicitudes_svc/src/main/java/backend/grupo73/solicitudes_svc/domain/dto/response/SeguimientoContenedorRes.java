package backend.grupo73.solicitudes_svc.domain.dto.response;

import backend.grupo73.solicitudes_svc.domain.model.SolicitudModel;
import backend.grupo73.solicitudes_svc.domain.model.ContenedorModel;
import backend.grupo73.solicitudes_svc.domain.model.EstadoSolicitud;

import java.math.BigDecimal;
import java.util.UUID;

public record SeguimientoContenedorRes(
        UUID solicitudId,
        UUID contenedorId,
        String identificacionUnica,
        EstadoSolicitud estadoSolicitud,
        String estadoContenedor,
        String ubicacionActual,
        BigDecimal costoEstimado,
        Integer tiempoEstimadoMinutos,
        BigDecimal costoReal,
        Integer tiempoRealMinutos
) {
    public static SeguimientoContenedorRes from(SolicitudModel s) {
        ContenedorModel c = s.getContenedor();
        return new SeguimientoContenedorRes(
                s.getId(),
                c.getId(),
                c.getIdentificacionUnica(),
                s.getEstado(),
                c.getEstado(),
                c.getUbicacionActual(),
                s.getCostoEstimado(),
                s.getTiempoEstimadoMinutos(),
                s.getCostoReal(),
                s.getTiempoRealMinutos()
        );
    }
}
