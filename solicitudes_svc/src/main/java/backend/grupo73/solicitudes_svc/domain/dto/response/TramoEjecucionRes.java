package backend.grupo73.solicitudes_svc.domain.dto.response;

import backend.grupo73.solicitudes_svc.domain.model.TramoEjecucionModel;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TramoEjecucionRes(
        UUID id,
        UUID origenUbicacionId,
        UUID destinoUbicacionId,
        UUID camionId,
        Double distanciaEstimadaKm,
        Integer tiempoEstimadoMin,
        Double distanciaRealKm,
        Integer tiempoRealMin,
        Instant fechaHoraInicioReal,
        Instant fechaHoraFinReal,
        Integer orden
) {
    public static TramoEjecucionRes from(TramoEjecucionModel model) {
        return new TramoEjecucionRes(
                model.getId(),
                model.getOrigenUbicacionId(),
                model.getDestinoUbicacionId(),
                model.getCamionId(),
                model.getDistanciaEstimadaKm(),
                model.getTiempoEstimadoMin(),
                model.getDistanciaRealKm(),
                model.getTiempoRealMin(),
                model.getFechaHoraInicioReal(),
                model.getFechaHoraFinReal(),
                model.getOrden()
        );
    }
}
