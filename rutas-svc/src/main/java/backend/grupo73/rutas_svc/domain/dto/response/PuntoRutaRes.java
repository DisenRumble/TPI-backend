package backend.grupo73.rutas_svc.domain.dto.response;

import backend.grupo73.rutas_svc.domain.model.PuntoRutaModel;

public record PuntoRutaRes(
        Double lat,
        Double lng,
        String tipo,
        String descripcion
) {
    public static PuntoRutaRes from(PuntoRutaModel m) {
        return new PuntoRutaRes(
                m.getLat(),
                m.getLng(),
                m.getTipo(),
                m.getDescripcion()
        );
    }
}
