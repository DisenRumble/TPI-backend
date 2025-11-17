package backend.grupo73.rutas_svc.service;

import backend.grupo73.rutas_svc.domain.dto.request.CalcularRutaReq;
import backend.grupo73.rutas_svc.domain.dto.request.PuntoRutaReq;
import backend.grupo73.rutas_svc.domain.model.PuntoRutaModel;
import backend.grupo73.rutas_svc.domain.model.RutaCalculadaModel;
import backend.grupo73.rutas_svc.domain.model.TramoModel;
import backend.grupo73.rutas_svc.infrastructure.OsrmClient;
import backend.grupo73.rutas_svc.infrastructure.OsrmClient.OsrmRouteResponse;
import backend.grupo73.rutas_svc.infrastructure.OsrmClient.Route;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class RutasService implements RutasServiceI {

    private final OsrmClient osrmClient;

    public RutasService(OsrmClient osrmClient) {
        this.osrmClient = osrmClient;
    }

    @Override
    public RutaCalculadaModel calcularRuta(CalcularRutaReq request) {
        List<PuntoRutaReq> puntosReq = request.puntos();

        if (puntosReq == null || puntosReq.size() < 2) {
            throw new IllegalArgumentException("Se necesitan al menos 2 puntos para calcular la ruta");
        }

        List<PuntoRutaModel> puntos = puntosReq.stream()
                .map(p -> new PuntoRutaModel(
                        p.lat(),
                        p.lng(),
                        p.tipo(),
                        p.descripcion()
                ))
                .toList();

        List<TramoModel> tramos = new ArrayList<>();
        double totalDistKm = 0.0;
        double totalDurMin = 0.0;

        for (int i = 0; i < puntos.size() - 1; i++) {
            PuntoRutaModel origen = puntos.get(i);
            PuntoRutaModel destino = puntos.get(i + 1);

            OsrmRouteResponse osrmResp = osrmClient.route(
                    origen.getLat(), origen.getLng(),
                    destino.getLat(), destino.getLng()
            );

            if (osrmResp == null || osrmResp.getRoutes() == null || osrmResp.getRoutes().length == 0) {
                throw new RuntimeException("OSRM no devolvió rutas válidas para el tramo " + i);
            }

            Route route = osrmResp.getRoutes()[0];

            double distKm = route.getDistance() / 1000.0;
            double durMin = route.getDuration() / 60.0;

            totalDistKm += distKm;
            totalDurMin += durMin;

            TramoModel tramo = new TramoModel(
                    origen,
                    destino,
                    distKm,
                    durMin
            );

            tramos.add(tramo);
        }

        return new RutaCalculadaModel(tramos, totalDistKm, totalDurMin);
    }

    @Override
    public List<RutaCalculadaModel> calcularRutasConAlternativas(CalcularRutaReq request) {
        List<PuntoRutaReq> puntosReq = request.puntos();

        if (puntosReq == null || puntosReq.size() < 2) {
            throw new IllegalArgumentException("Se necesitan al menos 2 puntos para calcular rutas");
        }

        if (puntosReq.size() > 2) {
            RutaCalculadaModel unica = calcularRuta(request);
            return Collections.singletonList(unica);
        }

        PuntoRutaReq origenReq = puntosReq.get(0);
        PuntoRutaReq destinoReq = puntosReq.get(1);

        PuntoRutaModel origen = new PuntoRutaModel(
                origenReq.lat(),
                origenReq.lng(),
                origenReq.tipo(),
                origenReq.descripcion()
        );

        PuntoRutaModel destino = new PuntoRutaModel(
                destinoReq.lat(),
                destinoReq.lng(),
                destinoReq.tipo(),
                destinoReq.descripcion()
        );

        OsrmRouteResponse osrmResp = osrmClient.routeWithAlternatives(
                origen.getLat(), origen.getLng(),
                destino.getLat(), destino.getLng()
        );

        if (osrmResp == null || osrmResp.getRoutes() == null || osrmResp.getRoutes().length == 0) {
            throw new RuntimeException("OSRM no devolvió rutas válidas para la ruta completa");
        }

        Route[] routes = osrmResp.getRoutes();
        List<RutaCalculadaModel> alternativas = new ArrayList<>();

        for (Route route : routes) {
            double distKm = route.getDistance() / 1000.0;
            double durMin = route.getDuration() / 60.0;

            TramoModel tramo = new TramoModel(
                    origen,
                    destino,
                    distKm,
                    durMin
            );

            List<TramoModel> tramos = List.of(tramo);

            RutaCalculadaModel modelo = new RutaCalculadaModel(
                    tramos,
                    distKm,
                    durMin
            );

            alternativas.add(modelo);
        }

        return alternativas;
    }
}
