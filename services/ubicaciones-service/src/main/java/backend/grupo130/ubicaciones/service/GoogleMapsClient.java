package backend.grupo130.ubicaciones.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GoogleMapsClient {

    private static final Logger log = LoggerFactory.getLogger(GoogleMapsClient.class);

    private final WebClient googleWebClient;
    private final ObjectMapper objectMapper;

    @Value("${google.maps.api-key:}")
    private String apiKey;

    public Optional<DirectionsSummary> calcularDistancia(double origenLat, double origenLng,
                                                         double destinoLat, double destinoLng) {
        if (!StringUtils.hasText(apiKey)) {
            return Optional.empty();
        }
        var uri = UriComponentsBuilder
            .fromUriString("https://maps.googleapis.com/maps/api/directions/json")
            .queryParam("origin", origenLat + "," + origenLng)
            .queryParam("destination", destinoLat + "," + destinoLng)
            .queryParam("mode", "driving")
            .queryParam("key", apiKey)
            .build(true)
            .toUri();

        try {
            String body = googleWebClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(10))
                .onErrorResume(ex -> {
                    log.warn("Fallo consultando Google Maps: {}", ex.getMessage());
                    return Mono.empty();
                })
                .block();

            if (body == null) {
                return Optional.empty();
            }

            JsonNode root = objectMapper.readTree(body);
            if (!"OK".equalsIgnoreCase(root.path("status").asText())) {
                log.warn("Google Maps Directions devolvió estado {}", root.path("status").asText());
                return Optional.empty();
            }

            JsonNode leg = root.path("routes").get(0).path("legs").get(0);
            double distanceMeters = leg.path("distance").path("value").asDouble();
            long durationSeconds = leg.path("duration").path("value").asLong();

            return Optional.of(new DirectionsSummary(distanceMeters / 1000d, durationSeconds / 60));
        } catch (Exception ex) {
            log.warn("Error parseando respuesta de Google Maps", ex);
            return Optional.empty();
        }
    }

    public record DirectionsSummary(double distanciaKm, long duracionMinutos) {
    }
}
