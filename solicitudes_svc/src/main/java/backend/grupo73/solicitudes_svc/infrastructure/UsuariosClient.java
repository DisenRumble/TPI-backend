package backend.grupo73.solicitudes_svc.infrastructure;

import backend.grupo73.solicitudes_svc.domain.dto.response.ClienteRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UsuariosClient {

    @Value("${app.usuarios.base-url}")
    private String usuariosBaseUrl;

    private final RestClient.Builder restClientBuilder;

    private RestClient restClient() {
        return restClientBuilder
                .baseUrl(usuariosBaseUrl)
                .build();
    }

    private String getCurrentToken() {
        var auth = (JwtAuthenticationToken) SecurityContextHolder
                .getContext()
                .getAuthentication();
        return auth.getToken().getTokenValue();
    }

    public ClienteRes ensureCurrentCliente() {
        return restClient()
                .post()
                .uri("/api/clientes/auto-register")
                .header("Authorization", "Bearer " + getCurrentToken())
                .retrieve()
                .body(ClienteRes.class);
    }

    public ClienteRes getClienteById(UUID clienteId) {
        return restClient()
                .get()
                .uri("/api/clientes/{id}", clienteId)
                .header("Authorization", "Bearer " + getCurrentToken())
                .retrieve()
                .body(ClienteRes.class);
    }
}
