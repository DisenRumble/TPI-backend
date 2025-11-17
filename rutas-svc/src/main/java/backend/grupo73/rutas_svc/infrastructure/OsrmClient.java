package backend.grupo73.rutas_svc.infrastructure;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class OsrmClient {

    private final RestTemplate restTemplate;
    private final String osrmBaseUrl;

    public OsrmClient(
            RestTemplate restTemplate,
            @Value("${app.osrm.base-url:http://localhost:5000}") String osrmBaseUrl
    ) {
        this.restTemplate = restTemplate;
        this.osrmBaseUrl = osrmBaseUrl;
    }

    public OsrmRouteResponse route(double lat1, double lng1, double lat2, double lng2) {
        try {
            String coordinates = lng1 + "," + lat1 + ";" + lng2 + "," + lat2;

            String url = osrmBaseUrl + "/route/v1/driving/" + coordinates +
                    "?overview=false&alternatives=false&steps=false";

            log.debug("Llamando a OSRM (una sola ruta): {}", url);

            return restTemplate.getForObject(url, OsrmRouteResponse.class);

        } catch (Exception e) {
            log.error("Error llamando a OSRM", e);
            throw new RuntimeException("Error llamando a OSRM: " + e.getMessage(), e);
        }
    }

    public OsrmRouteResponse routeWithAlternatives(double lat1, double lng1, double lat2, double lng2) {
        try {
            String coordinates = lng1 + "," + lat1 + ";" + lng2 + "," + lat2;

            String url = osrmBaseUrl + "/route/v1/driving/" + coordinates +
                    "?overview=false&alternatives=true&steps=false";

            log.debug("Llamando a OSRM (con alternativas): {}", url);

            return restTemplate.getForObject(url, OsrmRouteResponse.class);

        } catch (Exception e) {
            log.error("Error llamando a OSRM (alternativas)", e);
            throw new RuntimeException("Error llamando a OSRM (alternativas): " + e.getMessage(), e);
        }
    }

    @Data
    @Getter
    @Setter
    public static class OsrmRouteResponse {
        private Route[] routes;
    }

    @Data
    @Getter
    @Setter
    public static class Route {
        private double distance;
        private double duration;
    }
}
