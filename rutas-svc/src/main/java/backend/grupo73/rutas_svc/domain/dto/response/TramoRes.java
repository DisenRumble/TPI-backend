package backend.grupo73.rutas_svc.domain.dto.response;

import backend.grupo73.rutas_svc.domain.model.TramoModel;

import java.math.BigDecimal;

public record TramoRes(
        PuntoRutaRes origen,
        PuntoRutaRes destino,
        BigDecimal distanciaKm,
        Integer duracionMinutos
) {
    public static TramoRes from(TramoModel m) {
        return new TramoRes(
                PuntoRutaRes.from(m.getOrigen()),
                PuntoRutaRes.from(m.getDestino()),
                BigDecimal.valueOf(m.getDistanciaKm()),
                (int) Math.round(m.getDuracionMinutos())
        );
    }
}
