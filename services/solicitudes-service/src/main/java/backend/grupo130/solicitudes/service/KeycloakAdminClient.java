package backend.grupo130.solicitudes.service;

import backend.grupo130.solicitudes.config.KeycloakAdminProperties;
import backend.grupo130.solicitudes.dto.RegistroClienteRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class KeycloakAdminClient {

    private final RestClient keycloakRestClient;
    private final KeycloakAdminProperties properties;

    public UUID registrarUsuarioCliente(RegistroClienteRequest request) {
        String token = obtenerAdminToken();
        CreateUserPayload payload = CreateUserPayload.from(request, properties.clientRole());

        try {
            ResponseEntity<Void> response = keycloakRestClient.post()
                .uri("/admin/realms/{realm}/users", properties.realm())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(payload)
                .retrieve()
                .toBodilessEntity();

            URI location = response.getHeaders().getLocation();
            if (location == null) {
                throw new IllegalStateException("Keycloak no devolvió la ubicación del usuario creado.");
            }
            return extraerIdDeLocation(location);
        } catch (RestClientResponseException ex) {
            throw new IllegalStateException(
                "Error al crear el usuario en Keycloak (" + ex.getStatusCode().value() + "): " + ex.getResponseBodyAsString(),
                ex
            );
        }
    }

    private UUID extraerIdDeLocation(URI location) {
        String path = location.getPath();
        String id = path.substring(path.lastIndexOf('/') + 1);
        return UUID.fromString(id);
    }

    private String obtenerAdminToken() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "password");
        form.add("client_id", properties.clientId());
        form.add("username", properties.username());
        form.add("password", properties.password());

        TokenResponse response = keycloakRestClient.post()
            .uri("/realms/{realm}/protocol/openid-connect/token", properties.adminRealm())
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(form)
            .retrieve()
            .body(TokenResponse.class);

        if (response == null || response.accessToken == null) {
            throw new IllegalStateException("No se pudo obtener un token de administración de Keycloak.");
        }
        return response.accessToken;
    }

    private record TokenResponse(@JsonProperty("access_token") String accessToken) { }

    private record CreateUserPayload(
        String username,
        String email,
        String firstName,
        String lastName,
        boolean enabled,
        List<CredentialRepresentation> credentials,
        List<String> realmRoles
    ) {
        static CreateUserPayload from(RegistroClienteRequest request, String role) {
            CredentialRepresentation credential = new CredentialRepresentation("password", request.password(), false);
            return new CreateUserPayload(
                request.username(),
                request.email(),
                request.nombre(),
                request.apellido(),
                true,
                List.of(credential),
                List.of(role)
            );
        }
    }

    private record CredentialRepresentation(
        String type,
        String value,
        boolean temporary
    ) { }
}
