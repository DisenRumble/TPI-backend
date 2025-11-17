package backend.grupo73.solicitudes_svc.domain.dto.response;

import backend.grupo73.solicitudes_svc.domain.model.SolicitudModel;
import backend.grupo73.solicitudes_svc.domain.model.EstadoSolicitud;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record SolicitudRes(
        UUID id,
        UUID clienteId,
        String origen,
        String destino,
        EstadoSolicitud estado,
        BigDecimal costoEstimado,
        Integer tiempoEstimadoMinutos,
        BigDecimal costoReal,
        Integer tiempoRealMinutos,
        List<TramoEjecucionRes> tramosEjecucion
) {
    public static SolicitudRes from(SolicitudModel s) {
        List<TramoEjecucionRes> tramos = s.getTramosEjecucion() != null ?
                s.getTramosEjecucion().stream()
                        .map(TramoEjecucionRes::from)
                        .collect(Collectors.toList()) :
                List.of();

        return new SolicitudRes(
                s.getId(),
                s.getClienteId(),
                s.getOrigenDescripcion(),
                s.getDestinoDescripcion(),
                s.getEstado(),
                s.getCostoEstimado(),
                s.getTiempoEstimadoMinutos(),
                s.getCostoReal(),
                s.getTiempoRealMinutos(),
                tramos
        );
    }
}
