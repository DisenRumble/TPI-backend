package backend.grupo73.solicitudes_svc.domain.dto.response;

import backend.grupo73.solicitudes_svc.domain.model.SolicitudModel;
import backend.grupo73.solicitudes_svc.domain.model.EstadoSolicitud;

import java.math.BigDecimal;
import java.util.UUID;

public record SolicitudRes(
        UUID id,
        UUID clienteId,
        String origen,
        String destino,
        EstadoSolicitud estado,
        BigDecimal costoEstimado,
        Integer tiempoEstimadoMinutos,
        BigDecimal costoReal,
        Integer tiempoRealMinutos
) {
    public static SolicitudRes from(SolicitudModel s) {
        return new SolicitudRes(
                s.getId(),
                s.getClienteId(),
                s.getOrigen(),
                s.getDestino(),
                s.getEstado(),
                s.getCostoEstimado(),
                s.getTiempoEstimadoMinutos(),
                s.getCostoReal(),
                s.getTiempoRealMinutos()
        );
    }
}
