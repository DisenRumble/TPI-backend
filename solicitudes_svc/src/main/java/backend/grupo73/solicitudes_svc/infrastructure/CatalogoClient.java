package backend.grupo73.solicitudes_svc.infrastructure;

import backend.grupo73.solicitudes_svc.domain.dto.response.catalogo.CamionRes;
import backend.grupo73.solicitudes_svc.domain.dto.response.catalogo.TarifaRes;
import backend.grupo73.solicitudes_svc.domain.dto.response.catalogo.UbicacionRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CatalogoClient {

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

    public List<UbicacionRes> getUbicacionesByType(String tipo) {
        return restClient()
                .get()
                .uri("/ubicaciones?tipo={tipo}", tipo)
                .header("Authorization", "Bearer " + getCurrentToken())
                .retrieve()
                .body(new ParameterizedTypeReference<List<UbicacionRes>>() {});
    }

    public CamionRes getCamionById(UUID id) {
        return restClient()
                .get()
                .uri("/camiones/{id}", id)
                .header("Authorization", "Bearer " + getCurrentToken())
                .retrieve()
                .body(CamionRes.class);
    }

    public TarifaRes getTarifas() {
        // Asumiendo que hay un endpoint para obtener todas las tarifas o una tarifa por defecto
        return restClient()
                .get()
                .uri("/tarifas")
                .header("Authorization", "Bearer " + getCurrentToken())
                .retrieve()
                .body(TarifaRes.class);
    }
}
