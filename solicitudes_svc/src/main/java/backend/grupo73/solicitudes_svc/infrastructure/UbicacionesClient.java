package backend.grupo73.solicitudes_svc.infrastructure;

import backend.grupo73.solicitudes_svc.domain.dto.response.catalogo.UbicacionRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UbicacionesClient {

    @Value("${app.catalogo.base-url}")
    private String catalogoBaseUrl;

    private final RestClient.Builder restClientBuilder;

    private RestClient restClient() {
        return restClientBuilder
                .baseUrl(catalogoBaseUrl)
                .build();
    }

    private String getCurrentToken() {
        var auth = (JwtAuthenticationToken) SecurityContextHolder
                .getContext()
                .getAuthentication();
        return auth.getToken().getTokenValue();
    }

    public UbicacionRes getUbicacionById(UUID id) {
        return restClient()
                .get()
                .uri("/ubicaciones/{id}", id)
                .header("Authorization", "Bearer " + getCurrentToken())
                .retrieve()
                .body(UbicacionRes.class);
    }
}
