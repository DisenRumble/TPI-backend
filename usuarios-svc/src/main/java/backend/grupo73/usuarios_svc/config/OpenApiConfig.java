package backend.grupo73.usuarios_svc.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info().title("usuarios-svc").version("v1"))
                .addServersItem(new Server().url("/usuarios")) // respeta tu context-path
                .components(new Components()
                        .addSecuritySchemes(
                                "Keycloak",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.OAUTH2)
                                        .flows(new OAuthFlows()
                                                .authorizationCode(new OAuthFlow()
                                                        .authorizationUrl("http://localhost:8080/realms/tpi/protocol/openid-connect/auth")
                                                        .tokenUrl("http://localhost:8080/realms/tpi/protocol/openid-connect/token")
                                                        .scopes(new io.swagger.v3.oas.models.security.Scopes()
                                                                .addString("openid", "OpenID Connect scope")
                                                                .addString("profile", "User profile information")
                                                                .addString("email", "User email address")
                                                        )
                                                )
                                        )
                        )
                )
                .addSecurityItem(new SecurityRequirement().addList("Keycloak"));
    }
}
