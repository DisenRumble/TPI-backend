package backend.grupo73.solicitudes_svc.infrastructure;

import backend.grupo73.solicitudes_svc.domain.dto.request.rutas.CalcularRutaReq;
import backend.grupo73.solicitudes_svc.domain.dto.response.rutas.CalcularRutaRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class RutasClient {

    @Value("${app.rutas.base-url}")
    private String rutasBaseUrl;

    private final RestClient.Builder restClientBuilder;

    private RestClient restClient() {
        return restClientBuilder
                .baseUrl(rutasBaseUrl)
                .build();
    }

    private String getCurrentToken() {
        var auth = (JwtAuthenticationToken) SecurityContextHolder
                .getContext()
                .getAuthentication();
        return auth.getToken().getTokenValue();
    }

    public CalcularRutaRes calcularRuta(CalcularRutaReq req) {
        return restClient()
                .post()
                .uri("/api/rutas/calcular")
                .header("Authorization", "Bearer " + getCurrentToken())
                .body(req)
                .retrieve()
                .body(CalcularRutaRes.class);
    }
}
