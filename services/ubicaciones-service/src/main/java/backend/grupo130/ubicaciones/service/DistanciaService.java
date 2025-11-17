package backend.grupo130.ubicaciones.service;

import backend.grupo130.ubicaciones.dto.CoordenadaRequest;
import backend.grupo130.ubicaciones.dto.DistanciaRequest;
import backend.grupo130.ubicaciones.dto.DistanciaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DistanciaService {

    private static final double RADIO_TIERRA_KM = 6371.0;
    private static final double VELOCIDAD_PROMEDIO_KMH = 60.0;

    private final GoogleMapsClient googleMapsClient;

    public DistanciaResponse calcular(DistanciaRequest request) {
        CoordenadaRequest origen = request.origen();
        CoordenadaRequest destino = request.destino();

        return googleMapsClient.calcularDistancia(
                origen.latitud(),
                origen.longitud(),
                destino.latitud(),
                destino.longitud()
            )
            .map(summary -> new DistanciaResponse(
                summary.distanciaKm(),
                summary.duracionMinutos(),
                true,
                "Distancia estimada utilizando Google Maps Directions API"
            ))
            .orElseGet(() -> calcularPorHaversine(origen, destino));
    }

    private DistanciaResponse calcularPorHaversine(CoordenadaRequest origen, CoordenadaRequest destino) {
        double latDistance = Math.toRadians(destino.latitud() - origen.latitud());
        double lonDistance = Math.toRadians(destino.longitud() - origen.longitud());

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
            + Math.cos(Math.toRadians(origen.latitud())) * Math.cos(Math.toRadians(destino.latitud()))
            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distanciaKm = RADIO_TIERRA_KM * c;

        long minutos = Math.round((distanciaKm / VELOCIDAD_PROMEDIO_KMH) * 60);
        return new DistanciaResponse(
            distanciaKm,
            minutos,
            false,
            "Distancia aproximada por fórmula Haversine (sin Google Maps API key)"
        );
    }
}
